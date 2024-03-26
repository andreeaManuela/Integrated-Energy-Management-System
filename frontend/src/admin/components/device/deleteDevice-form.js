import React, {useState, useEffect} from "react";
import './deleteDevice-form.css';

function DeleteDeviceForm({visible, onClose, deleteDevice}){
    const [id, setIds] = useState([]);
    const [selectedId, setSelectedId] = useState('');

    useEffect(() => {
        const fetchIds = async () => {
            try {
                const token= localStorage.getItem('authToken')
                const headers = {};

                if (token) {
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
                //console.log(data);
            } catch (error) {
                console.error("Eroare la extragerea ID-urilor:", error);
            }
        };
        fetchIds();
    }, []);

    const handleSubmit = async () => {
        const user = {
            id: selectedId,
        }

        console.log(selectedId);
        deleteDevice(selectedId);
        onClose();
    }

    return (
        <div>
            { visible && (
                <div className="formClass">
                    <label className="titleDelete">Choose the device id you want to delete.</label>
                    <form onSubmit={handleSubmit}>
                        <div className="input-container-delete">
                            <label>Id</label>
                            <select value={selectedId} onChange={(e) => setSelectedId(e.target.value)}>
                                <option value="" disabled>Selectează un ID</option>
                                {id.map(id => (
                                    <option key={id} value={id}>{id}</option>
                                ))}
                            </select>
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

export default DeleteDeviceForm;


