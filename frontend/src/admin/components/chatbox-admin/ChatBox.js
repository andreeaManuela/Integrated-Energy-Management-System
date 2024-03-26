import React, {useState, useEffect, useRef, useCallback, useContext} from 'react';
import "./ChatBox.css"
import {WebSocketContext} from "../../../websocketService/WebSocketProvider";

const ChatBox = ({ admin, user, messages, onClose, isVisible, onSendMessage, updateMessages  }) => {
    const [message, setMessage] = useState('');
    const endOfMessagesRef = useRef(null);
    const stompClient= useContext(WebSocketContext);


    //SEEN from admin to client
    //trimiterea de notificari seen
    useEffect( () => {
        //daca chatul este deschis, trimitem seen spre backend de la admin
        if(isVisible){
            const seenMessage= {userId: user.id, seen: true}
            stompClient.send("/app/seen-from-admin", {}, JSON.stringify(seenMessage));
            console.log("Trimitem seen la backend!")
        }
    }, [isVisible, stompClient, user.id]);

    //adminul primeste "seen" de la client
    useEffect(() => {
        if (stompClient) {
            const seenMessage = `/topic/seen-as-client/${user.id}`;
            const subscription = stompClient.subscribe(seenMessage, (message) => {
                const mesajSeen = JSON.parse(message.body);
                //daca seen este true
                if(mesajSeen){
                    mesajVazutDeAdmin();
                }
            });

            return () => {
                subscription.unsubscribe();
            }
        }

    }, [stompClient, messages, user.id]);

    const mesajVazutDeAdmin = () => {
            const lastIndex = messages.length - 1;
            const updatedMessages = messages.map((msg, index) => {
                if(index === lastIndex && msg.hasOwnProperty('sendByMe')){
                    return { ...msg, seen: true };
                } else {
                    return msg;
                }
            }

            );

        updateMessages(updatedMessages);
    }

    const sendMessageToClient = () => {
        onSendMessage(user.id, message);
        setMessage('');
    }

    //move chatbox
    useEffect(() => {
        endOfMessagesRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    const messageChange = (e) => {
        setMessage(e.target.value);
    }

    const [isDragging, setIsDragging] = useState(false);
    const [position, setPosition] = useState({ x: 100, y: 100 });

    const startDragging = useCallback((e) => {
        setIsDragging(true);
        e.target.style.cursor = 'grabbing';
    }, []);

    const stopDragging = useCallback(() => {
        setIsDragging(false);
        window.document.body.style.cursor = 'default';
    }, []);

    const handleDragging = useCallback((e) => {
        if (isDragging) {
            setPosition((prevPosition) => ({
                x: prevPosition.x + e.movementX,
                y: prevPosition.y + e.movementY,
            }));
        }
    }, [isDragging]);

    useEffect(() => {
        if (isDragging) {
            window.addEventListener('mousemove', handleDragging);
            window.addEventListener('mouseup', stopDragging);
            return () => {
                window.removeEventListener('mousemove', handleDragging);
                window.removeEventListener('mouseup', stopDragging);
            };
        }
    }, [isDragging, handleDragging, stopDragging]);


    return (
        <div  className="client-chat-box"
              style={{
                  left: `${position.x}px`,
                  top: `${position.y}px`,
                  position: 'absolute'
              }}
              onMouseDown={startDragging}>
            <div className="chat-header">
                <span className="chat-title">{`Chat (ID: ${user.id})`}</span>
                <button onClick={() => onClose(user.id)} className="close-chat">✖</button>
            </div>
            <div className="chat-messages">
                {messages.map((msg, index) => (
                    <div key={index} className={msg.sendByMe ? 'sent' : 'received'}>
                        <div className="message-content"> {msg.message}</div>
                        <div className="message-time">{new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</div>
                        {msg.seen && <div className="seen-indicator">Seen</div>}
                    </div>
                ))}
                <div ref={endOfMessagesRef} />
            </div>
            <div className="chat-input">
                <input
                    type="text"
                    value={message}
                    onChange={messageChange}
                    placeholder="Type your message here..."
                />
                <button className="send-message" onClick={sendMessageToClient}>➤</button>
            </div>
        </div>

    );
};

export default ChatBox;
