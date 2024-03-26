// NotificationBanner.js
import React from 'react';
import { useWebSocket } from './useWebSocket';

const NotificationBanner = ({ url }) => {
    const messageData = useWebSocket(url);

    if (!messageData || typeof messageData !== 'object') return null;

    const messageText = messageData.message;
    return <div className="notification-banner">{messageText}</div>;

};

export default NotificationBanner;
