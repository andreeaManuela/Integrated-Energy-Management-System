import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

export const useWebSocket = (url) => {
    const [data, setData] = useState(null);

    useEffect(() => {
        const socket = new SockJS(url);
        const stompClient = new Client({
            webSocketFactory: () => socket
        });

        stompClient.onConnect = (frame) => {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/notifications', (message) => {
                setData(JSON.parse(message.body));
            });
        };

        stompClient.onStompError = (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        };

        stompClient.activate();

        return () => {
            stompClient.deactivate();
        };
    }, [url]);

    return data;
};
