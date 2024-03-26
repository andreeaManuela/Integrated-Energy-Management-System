import React from "react";
import {useEffect, useState} from "react";
import './updateDevice-form.css';

function UpdateDeviceForm({visible, onClose, updateDevice}){
    const [id, setIds] = useState([]);
    const [selectedId, setSelectedId]= useState('');
    const [description, setDescription]= useState('');
    const [address, setAddress] = useState('');
    const [max_consump, setMaxConsum] = useState('');

    useEffect(() => {
        const fetchIds = async () => {
            try {
                const token = localStorage.getItem('authToken')
                const headers={};

                if(token){
                    headers['Authorization'] = `Bearer ${token}`;
                }

                const response = await fetch('http://localhost:8081/device_id', {
                    method: 'GET',
                    headers: headers,
                });

                if (!response.ok) {
                    console.log('Fail to fetch ids');
                    return;
                }

                const data = await response.json();
                setIds(data);
            } catch (error) {
                console.error('Eroare la obținerea id-urilor:', error);
            }
        };
        fetchIds();
    }, []);

    const handleSubmit = async () => {
        const device = {
            id: selectedId,
            description: description,
            address: address,
            max_consump: max_consump,
        }

        updateDevice(device);
        console.log(device)
        setAddress('');
        setDescription('');
        setMaxConsum('');
        onClose();
    }

    return (
        <div>
            { visible && (
                <div className="formClass">
                    <form onSubmit={handleSubmit}>
                        <div className="input-container">
                            <label>Id</label>
                            <select className="input"
                                    value={selectedId}
                                    onChange={(e) => setSelectedId(e.target.value)}>
                                <option value="" disabled>Selectează un ID</option>
                                {id.map((id) => (
                                    <option key={id} value={id}>
                                        {id}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="input-container">
                            <label>Address</label>
                            <input className="input"
                                   type="text"
                                   value={address}
                                   placeholder={"Str Rozelor nr 20 .."}
                                   onChange={(e) => setAddress(e.target.value)} />
                        </div>
                        <div className="input-container">
                            <label>Description</label>
                            <input className="input"
                                   type="text"
                                   value={description}
                                   placeholder={"..."}
                                   onChange={(e) => setDescription(e.target.value)} />
                        </div>
                        <div className="input-container">
                            <label>Max Energy Consumption</label>
                            <input className="input"
                                   type="text"
                                   value={max_consump}
                                   placeholder={"ex 5 kWh"}
                                   onChange={(e) => setMaxConsum(e.target.value)} />
                        </div>
                        <div className="button-container">
                            <button type="button" className="button" onClick={handleSubmit}>Submit
                            </button>
                        </div>
                    </form>
                </div>
            )}
        </div>
    );

}

export default UpdateDeviceForm;