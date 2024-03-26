// WebSocketService.js
import SockJS from 'sockjs-client';
import { Stomp } from 'stompjs';

class WebSocketService {
    constructor() {
        this.socket = new SockJS('http://localhost:8082/websocket');
        this.stompClient = Stomp.over(this.socket);
        this.isConnected = false;
    }

    connect(callback) {
        this.stompClient.connect({}, frame => {
            this.isConnected = true;
            console.log('Connected: ' + frame);
            this.stompClient.subscribe('/topic/notifications', message => {
                if (callback) {
                    callback(JSON.parse(message.body));
                }
            });
        }, error => {
            this.isConnected = false;
            console.error('Could not connect to WebSocket: ' + error);
            setTimeout(() => {
                this.connect(callback);
            }, 5000); // Încercă să te reconectezi după 5 secunde
        });
    }

    disconnect() {
        if (this.stompClient) {
            this.stompClient.disconnect(() => {
                this.isConnected = false;
                console.log('Disconnected from WebSocket');
            });
        }
    }

}

export default new WebSocketService();
