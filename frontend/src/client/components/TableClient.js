import React from "react";
import './TableClient.css';

function TabelDeviceClient({devices}){
    return(
        <table className="table-devices">
            <thead>
            <tr>
                <th>ID</th>
                <th>Description</th>
                <th>Address</th>
                <th>Maximum energy consumption</th>
            </tr>
            </thead>
            <tbody className="body">
            {devices.map((device, index) => (
                <tr key={index}>
                    <td>{device.id}</td>
                    <td>{device.description}</td>
                    <td>{device.address}</td>
                    <td>{device.max_consump}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}
export default TabelDeviceClient;