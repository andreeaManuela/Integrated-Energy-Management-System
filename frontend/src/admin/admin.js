import React, {useContext, useEffect, useRef, useState} from "react";
import img from './admin.jpg';
import {CardHeader} from "reactstrap";
import AddUserForm from './components/addUser-form';
import UpdateUserForm from './components/updateUser-form';
import DeleteUserForm from "./components/deleteUser-form";
import TabelUser from "./components/tabelUser";

import AddDeviceForm from "./components/device/addDevice-form";
import UpdateDeviceForm from "./components/device/updateDevice-form";
import DeleteDeviceForm from "./components/device/deleteDevice-form";
import TabelDevice from "./components/device/tabelDevice";
import '../admin/admin.css';
import {jwtDecode} from "jwt-decode";
import {useLocation} from "react-router-dom";
import ChatBox from "./components/chatbox-admin/ChatBox";
import {WebSocketContext} from "../websocketService/WebSocketProvider";

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "100vh",
    backgroundImage: `url(${img})`
};

function Admin(){
    const location= useLocation();
    const params = new URLSearchParams(location.search);
    const adminID= params.get("adminId");

    const [insertFormVisible, setInsertFormVisible] = useState(false);
    const [updateFormVisible, setUpdateFormVisible] = useState(false);
    const [deleteFormVisible, setDeleteFormVisible] = useState(false);
    const [users, setUsers] = useState([]);

    const [insertDeviceVisible, setInsertDeviceVisible] = useState(false);
    const [updateDeviceVisible, setUpdateDeviceVisible] = useState(false);
    const [deleteDeviceVisible, setDeleteDeviceVisible] = useState(false);
    const [devices, setDevices] = useState([]);


    useEffect(() => {
        async function getUsers ()  {
            try {
                const token = localStorage.getItem('authToken');
                const headers = {};

                if (token) {
                    headers['Authorization'] = `Bearer ${token}`;
                }

                const response = await fetch('http://localhost:8080/security/user',{
                    method: 'GET',
                    headers: headers,
                } );

              //  console.log(token);

                if (!response.ok) {
                    console.log('Fail to fetch users');
                    return;
                }
                const data = await response.json();
                setUsers(data);
            } catch (error) {
                console.error('Eroare la obținerea user-ilor:', error);
            }
        };
        getUsers();
    }, []);

    useEffect(() => {
        async function getDevices ()  {
            try {
                const token = localStorage.getItem('authToken')
                const headers={};

                if(token){
                    headers['Authorization'] = `Bearer ${token}`;
                }

                const response = await fetch('http://localhost:8081/device', {
                    method: 'GET',
                    headers: headers,
                } );

               // console.log('Device', response);
                if (!response.ok) {
                    console.log('Fail to fetch devices for users');
                    return;
                }
                const data = await response.json();
                setDevices(data);
            } catch (error) {
                console.error('Eroare la obținerea device-urilor pentru useri:', error);
            }
        };
        getDevices();
    }, []);

    //WebSocket CHAT

    const [openChats, setOpenChats] = useState({});
    const stompClient= useContext(WebSocketContext);


    //primirea de mesaje de la client
    useEffect(() => {
        if(stompClient){
            const subscribeMessagesFromClient = stompClient.subscribe( '/topic/adminMessages', (message) => {
                const mesajDeLaClient = JSON.parse(message.body);

                 setOpenChats(prevChats => {
                     const isChatBoxOpen = prevChats[mesajDeLaClient.userId]?.isVisible;
                     if (isChatBoxOpen) {
                         //chatbox deschis => admin a vazut mesajul => clientul primeste seen
                         //seen=true se trimite spre backend
                        const readNotification = {userId: mesajDeLaClient.userId, seen: true};
                        stompClient.send("/app/seen-from-admin", {}, JSON.stringify(readNotification));
                        console.log("S-a trimis seen-ul")
                    } else{
                         alert(`Clientul cu ID-ul ${mesajDeLaClient.userId} ți-a trimis un mesaj.`);
                     }
                    if (prevChats[mesajDeLaClient.userId]) {
                        return {
                            ...prevChats,
                            [mesajDeLaClient.userId]: {
                                ...prevChats[mesajDeLaClient.userId],
                                messages: [...prevChats[mesajDeLaClient.userId].messages, {message: mesajDeLaClient.message, timeStamp: mesajDeLaClient.timeStamp, sendByMe: false}]
                            }
                        };

                    } else {
                        return prevChats;
                    }
                  });
            });
            return () => {
                subscribeMessagesFromClient.unsubscribe();
            };
        }
    }, [stompClient, openChats]);

    const openChat = (user) => {
        //verific daca este deschis deja
        if(!openChats[user.id]){
            //il deschid
           // console.log("Open chats "+user.id)
            setOpenChats( prevChats => ({
                ...prevChats,
                [user.id]: {
                    user: {
                        id: user.id,
                        name: user.name
                    },
                    messages: [],
                    isVisible: true
                }
            }));
        } else {
            setOpenChats( prevChats => ({
                ...prevChats,
                [user.id]: {
                    ...prevChats[user.id],
                    isVisible: !prevChats[user.id].isVisible
                }
            }));
        }
    }

    const closeChat = (userId) => {
        setOpenChats(prevChats => ({
            ...prevChats,
            [userId]: {
                ...prevChats[userId],
                isVisible: false
            }
        }));
    };

    //trimite mesaje la client
    const sendMessageToClient = async (userId, messageText) => {
        const chatMessage = {
            userId: userId,
            message: messageText,
            timeStamp: new Date().toISOString(),
            sendMessageA: true
        };

        if (stompClient) {
        // Trimiteți mesajul la server
        stompClient.send("/app/chat.adminToClient", {}, JSON.stringify(chatMessage))
    }

        // Actualizați starea mesajelor
        setOpenChats(prevChats =>  ({
            ...prevChats,
            [userId]: {
                ...prevChats[userId],
                messages: [...prevChats[userId].messages, {message: messageText, sendByMe: true }]
            }
        }));
    };

    const updateChatMessages = (userId, updatedMessage) => {
        setOpenChats(prevChats => ({
            ...prevChats,
            [userId]: {
                ...prevChats[userId],
                messages: updatedMessage
            }
        }))
    }

    async function insertUserFunction(userForInsert) {
        let responseDataId;
        let userId;
        const token = localStorage.getItem('authToken');
        //insert in baza de date user
        try {

            const response = await fetch('http://localhost:8080/security/user', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(userForInsert),
            });

            console.log("Trimite user", userForInsert);
            if(response.ok){
                responseDataId= await response.json(); //astept id ul userului inserat
                const decodeToken = jwtDecode(responseDataId.token)
                userId= decodeToken.userId
              //  console.log("RESPONSE DATA ID", responseDataId)
             //   console.log("USER ID din token", userId)
            }
        } catch (error) {
            console.error('Eroare la inserare');
        }
        //insert in baza de date device
        try {
            const response = await fetch(`http://localhost:8081/client_id/${userId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({id: userId}),
            });

            if(!response.ok){
                console.log('Fail to insert device');}
        } catch (error) {
            console.error('Eroare la inserare Device');
        }

    }

    async function updateUserFunction(userForUpdate){
        try {
            const token= localStorage.getItem('authToken');
            const response = await fetch(`http://localhost:8080/security/user/${userForUpdate.id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(userForUpdate),
            });
            console.log(token)
            console.log("Userul pentru update in admin",userForUpdate)
            if(!response.ok){
                console.log('Fail to update');}
        } catch (error) {
            console.error('Update error');
        }
    }

    async function deleteUserFunction(userId){
        const token=localStorage.getItem('authToken');

        //delete from users
        try {
            const response = await fetch(`http://localhost:8080/security/user/${userId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            });
            if(!response.ok){
                console.log('Fail to delete the user');}
        } catch (error) {
            console.error('Delete error', error);
        }

        //delete device with id_client
        try {
            const headers = {};

            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }

            const response = await fetch(`http://localhost:8081/client_id/${userId}`, {
                method: 'DELETE',
                headers: headers,
            });
         //   console.log(response);
         //   console.log("id trimis este", userId);
            if(!response.ok){
                console.log('Fail to delete the id from id_client');}
        } catch (error) {
            console.error('Delete error', error);
        }
    }

    async function insertDeviceFunction(deviceInsert){
        const data = {
            description: deviceInsert.description,
            address: deviceInsert.address,
            max_consump: deviceInsert.max_consump
        };

        try{
            const token = localStorage.getItem('authToken');
            const response = await fetch(`http://localhost:8081/device/${deviceInsert.id_client}`, {
                method: 'POST',
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data),
            });
            console.log(deviceInsert.id_client)
            console.log(data)

            if(!response.ok){
                const response2= await response.json();
                console.log('Fail to insert device', response2);
            }
        }catch( error){
            console.log('Eroare la inserare DEVICE');
        }
    }

    async function updateDeviceFunction(deviceUpdate){
        try {
            const token = localStorage.getItem('authToken');
            const response = await fetch(`http://localhost:8081/device/${deviceUpdate.id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(deviceUpdate),
            });
            if(!response.ok){
                console.log('Fail to update device');}
        } catch (error) {
            console.error('Update Device error');
        }
    }

    async function deleteDeviceFunction(deviceDelete){
        try {
            const token = localStorage.getItem('authToken');
            const headers = {};

            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }

            const response = await fetch(`http://localhost:8081/device/${deviceDelete}`, {
                method: 'DELETE',
                headers: headers,

            });
            if(!response.ok){
                console.log('Fail to delete', response.status, response.statusText);}

        } catch (error) {
            console.error('Delete error', error);
        }
    }

   return (
       <div style={backgroundStyle}  className="admin-page">

           <CardHeader className="title">
               <strong>Admin Page</strong>
           </CardHeader>

           <div className="butoane">
           <div className="insert-user-button">
               <button className="insert" type="button" className="insert" onClick={() => setInsertFormVisible(true)}>
                   ADD USER
               </button>
               <AddUserForm
                   visible={insertFormVisible}
                   onClose={() => setInsertFormVisible(false)}
                   insertUser={insertUserFunction} />

           </div>
           <div className="update-user-button">
               <button type="button" className="update" onClick={() => setUpdateFormVisible(true)} >
                   UPDATE USER
               </button>
               <UpdateUserForm
                   visible={updateFormVisible}
                   onClose={() => setUpdateFormVisible(false)}
                   updateUser={updateUserFunction} />
           </div>
           <div className="delete-user-button">
               <button type="button" className="delete" onClick={() => setDeleteFormVisible(true)}>
                   DELETE USER
               </button>
               <DeleteUserForm
                   visible={deleteFormVisible}
                   onClose={() => setDeleteFormVisible(false)}   deleteUser={deleteUserFunction}
               />
           </div>
           </div>

           <TabelUser users={users}  onChatClick={openChat}/>

           {Object.entries(openChats).map(([userId, chat]) => (
                   <ChatBox
                       key={userId}
                       admin={adminID}
                       user={chat.user}
                       messages={chat.messages}
                       onClose={() => closeChat(userId)}
                       isVisible={chat.isVisible}
                       onSendMessage={(userId, text) => sendMessageToClient(userId, text)}
                       updateMessages={(updateMessages) => updateChatMessages(userId, updateMessages)}
                   />
           ))}

           <CardHeader className="title-device">
               <strong>Device Operations</strong>
           </CardHeader>

           <div className="butoane">
               <div className="insert-device-button">
                   <button className="insert" type="button" onClick={() => setInsertDeviceVisible(true)}>
                       ADD DEVICE
                   </button>
                   <AddDeviceForm
                       visible={insertDeviceVisible}
                       onClose={() => setInsertDeviceVisible(false)}
                       insertDevice={insertDeviceFunction} />

               </div>
               <div className="update-device-button">
                   <button className="update" type="button" onClick={() => setUpdateDeviceVisible(true)}>
                       UPDATE DEVICE
                   </button>
                   <UpdateDeviceForm
                       visible={updateDeviceVisible}
                       onClose={() => setUpdateDeviceVisible(false)}
                       updateDevice={updateDeviceFunction} />
               </div>
               <div className="delete-device-button">
                 <button  className="delete" type="button" onClick={() => setDeleteDeviceVisible(true)}>
                   DELETE DEVICE
               </button>
                   <DeleteDeviceForm
                       visible={deleteDeviceVisible}
                       onClose={() => setDeleteDeviceVisible(false)}
                       deleteDevice={deleteDeviceFunction}
                   />
           </div>
           </div>

           <TabelDevice devices={devices} />
       </div>

   );
}

export default Admin;