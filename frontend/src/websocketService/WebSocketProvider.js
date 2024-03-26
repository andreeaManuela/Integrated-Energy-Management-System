import React, {createContext, useContext, useRef, useEffect, useState} from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

// Crearea contextului
export const WebSocketContext = createContext(null);

export const WebSocketProvider = ({ children }) => {
    const [stompClient, setClient] = useState(null);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8083/websocket/chat');
        const client = window.Stomp.over(socket);

        client.connect({}, () => {
            setClient(client);
            console.log('Conectat la STOMP prin SockJS');
        })


        return () => {
            if (client && client.connected) {
                client.disconnect();
                console.log('Deconectat la STOMP prin SockJS')
            }
        };
    }, []);

    return (
        <WebSocketContext.Provider value={stompClient}>
            {children}
        </WebSocketContext.Provider>
    );
};
