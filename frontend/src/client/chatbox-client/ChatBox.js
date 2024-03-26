import React, {useState, useEffect, useRef, useContext} from 'react';
import "./ChatBox.css";
import {WebSocketContext} from "../../websocketService/WebSocketProvider";

const ChatBox = ({ userId}) => {
    const [messages, setMessages] = useState([]); //lista de mesaje
    const [message, setMessage] = useState(""); //textbox pt mesaje in chat
    const endOfMessagesRef = useRef(null);
    const stompClient= useContext(WebSocketContext);

    //mesaje primite de la ADMIN
    useEffect(() => {
        if(stompClient){
            const subscriptionMsjAdmin= stompClient.subscribe(`/topic/client/${userId}`, (message) => {
                const mesajDeLaAdmin = JSON.parse(message.body);
                //Adaug msj in lista de mesaje
                setMessages(prevMessages => [...prevMessages, {...mesajDeLaAdmin, sendMessageA: false}]);

                //mesajul primit il setez pe seen si trimit in backend => seen de la client
                const seenMesaj= {userId: userId, seen: true};
                stompClient.send("/app/seen-from-client", {}, JSON.stringify(seenMesaj));
                console.log("SEEN mesaj de la client spre admin: "+ seenMesaj)

            });
            return () => {
                subscriptionMsjAdmin.unsubscribe();
            };
        }
    }, [stompClient, userId]);

    //trimiterea de mesaje la ADMIN
    const sendMessageToAdmin = () => {
      if(stompClient && message.trim() !== ""){
          const messageToSend = {
              message: message,
              userId: userId,
              timeStamp: new Date().toISOString(),//toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })//.toISOString()
              formattedTime: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
              sendMessageA: true

          };
          stompClient.send("/app/chat.clientToAdmin", {}, JSON.stringify(messageToSend));

          //adaug mesajul la lista de mesaje
          setMessages([...messages, messageToSend]);
          setMessage("");
      }
    };

    //ultimul mesaj primeste seen de la admin
    useEffect(() => {
        if(stompClient){
            const seenSubscription = stompClient.subscribe(`/topic/seen-as-admin/${userId}`, (message) => {
                const isSeen= JSON.parse(message.body);
                console.log(isSeen)
                if(isSeen){
                    console.log("Seen de la admin in chatBox")
                    //se afiseazan in chatbox "seen" pentru ultimul mesaj
                    setLastMessageSeen();

                    // setMessages(prevMessages => {
                    //     let updatedMessages = [...prevMessages];
                    //     const lastIndex = updatedMessages.length - 1;
                    //     if (lastIndex >= 0 && updatedMessages[lastIndex].sendMessageA) {
                    //         updatedMessages[lastIndex] = { ...updatedMessages[lastIndex], seen: true };
                    //     }
                    //     return updatedMessages;
                    // });

                }
            });
            return () => {
                seenSubscription.unsubscribe();
            };
        }
    }, [stompClient, userId]);

    const setLastMessageSeen = () => {
        setMessages(prevMessages => {
            const updatedMessages = [...prevMessages];

            // Găsește ultimul mesaj trimis de utilizator
            const lastMessageIndex = updatedMessages.length - 1;

            // Verifică dacă există mesaje
            if (lastMessageIndex > 0) {
                // Setează proprietatea 'seen' ca 'true' pentru ultimul mesaj
                updatedMessages[lastMessageIndex] = {
                    ...updatedMessages[lastMessageIndex],
                    seen: true
                };
            }

            return updatedMessages;
        });
    };

    useEffect( () => {
        if(stompClient){
            const subscribeSeen= stompClient.subscribe(`/topic/seen-as-client/${userId}`, (message) => {
                const seenMess = JSON.parse(message.body);
                if(seenMess.seen === "true"){
                    setMessages(prevMessages => {
                        let updatedMessages = [...prevMessages];
                        const lastIndex = updatedMessages.length - 1;
                        if (lastIndex >= 0 && updatedMessages[lastIndex].sendMessageA) {
                            updatedMessages[lastIndex] = { ...updatedMessages[lastIndex], seen: true };
                        }
                        return updatedMessages;
                    });

                    return () => {
                        subscribeSeen.unsubscribe();
                    }

                }
            })
        }
    })


   useEffect(() => {
        endOfMessagesRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);


    return (
        <div  className="client-chat-box">
            <div className="chat-header">
                <span className="chat-title">Chat Admin</span>
            </div>
            <div className="chat-messages">
                {messages.map((msg, index) => (
                    <div key={index} className={`message ${msg.sendMessageA ? 'sent' : 'received'}`}>
                        <div className="message-content">{msg.message}</div>
                        <div className="message-time">{new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</div>
                        {msg.seen && index && msg.sendMessageA && <div className="seen-indicator">Seen</div>}
                    </div>
                ))}
                <div ref={endOfMessagesRef} />
            </div>
            <div className="chat-input">
                <input
                    type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Type your message here..."
                />
                <button onClick={sendMessageToAdmin} className="send-message">➤</button>
            </div>
        </div>

    );
};

export default ChatBox;
