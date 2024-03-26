import React, {useContext, useEffect, useState} from 'react';
import {CardHeader} from "reactstrap";
import TableClient from "./components/TableClient";
import {useLocation} from "react-router-dom";
import './client.css';
import ChatBox from "./chatbox-client/ChatBox"

function Client(){
    const [devices, setDevices]= useState([]);
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const id_client = params.get("userId");

    useEffect( () => {
    async function getDevicesById ()  {
        try {
            const token = localStorage.getItem('authToken')
            const headers = {};

            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }

            const response = await fetch(`http://localhost:8081/client_id/${id_client}`,{
                method: 'GET',
                headers: headers,
            } );
            if (!response.ok) {
                console.log('Fail to fetch devices for a specific user ');
                console.log(response);
                return;
            }
            const data = await response.json();
            setDevices(data);
        } catch (error) {
            console.error('Eroare la obținerea device-urilor:', error);
        }
    };
    getDevicesById();
}, [] );

    return (
        <div
            className="admin-page"
            style={{ left: '100px', top: '100px' }}
        >
        <div>
            <CardHeader className="title">
                <strong>You have the following devices:</strong>
            </CardHeader>
            <TableClient devices={devices} />
            <div className="chatbox-container">
                <ChatBox userId={id_client} />
            </div>
        </div>
        </div>
    );
}

export default Client;