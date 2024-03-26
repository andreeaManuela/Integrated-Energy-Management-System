import React from "react";
import './addDevice-form.css';
import {useState} from "react";
import {useEffect} from "react";

function AddDeviceForm({visible, onClose, insertDevice}){
    const [id_client, setIdClient]= useState([]);
    const [selectedId, setSelectedId] = useState('');
    const [description, setDescription] = useState('');
    const [address, setAdress] = useState('');
    const [max_consump, setMaxConsum] = useState('');

    useEffect(() => {
        const fetchIds = async () => {
            try {
                const token= localStorage.getItem('authToken')
                const headers={};

                if (token) {
                    headers['Authorization'] = `Bearer ${token}`;
                }

                const response = await fetch('http://localhost:8080/id', {
                    method: 'GET',
                    headers: headers,
                });

                if (!response.ok) {
                    console.log('Fail to fetch ids');
                    return;
                }

                const data = await response.json();
                setIdClient(data);
            } catch (error) {
                console.error('Eroare la obținerea id-urilor:', error);
            }
        };
        fetchIds();
    }, []);

    const handleSubmit = async () => {
        if (address.trim() === '' || max_consump.trim() === '') {
            alert('Please fill in all the fields!');
            return;
        }

        const device = {
            description: description,
            address: address,
            max_consump: max_consump,
            id_client: selectedId,
        }

        console.log("Add Device Form", device);
        insertDevice(device);

        setDescription('');
        setAdress('');
        setMaxConsum('');
        onClose();
    }

    return(
        <div>
            { visible && (
                <div className="formClass">
                    <form onSubmit={handleSubmit}>
                        <div className="input-container">
                            <select value={selectedId} onChange={(e) => setSelectedId(e.target.value)}>
                                <option value="" disabled>Selectează un ID</option>
                                {id_client.map(id => (
                                    <option key={id} value={id}>{id}</option>
                                ))}
                            </select>
                        </div>
                        <div className="input-container">
                            <input className="input"
                                   type="text"
                                   value={description}
                                   placeholder={"Description"}
                                   onChange={(e) => setDescription(e.target.value)} />
                        </div>
                        <div className="input-container">
                            <input className="input"
                                   type="text"
                                   value={address}
                                   placeholder={"What's the address?"}
                                   onChange={(e) => setAdress(e.target.value)} />
                        </div>
                        <div className="input-container">
                            <input className="input"
                                   type="text"
                                   value={max_consump}
                                   placeholder={"Maximum hourly energy consumption: ex 5 kWh"}
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

export default AddDeviceForm;