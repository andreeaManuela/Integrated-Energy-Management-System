import React, {useState, useEffect} from "react";
import './deleteUser-form.css'

function DeleteUserForm({visible, onClose, deleteUser}){
    const [id, setIds] = useState([]);
    const [selectedId, setSelectedId]= useState('');

    useEffect(() => {
        const fetchIds = async () => {
            try {
                const token= localStorage.getItem('authToken')
                const headers = {};

                if (token) {
                    headers['Authorization'] = `Bearer ${token}`;
                }

                const response = await fetch('http://localhost:8080/id', {
                    method: 'GET',
                    headers: headers,
                });

                const data = await response.json();
                setIds(data);
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
    deleteUser(selectedId);
    onClose();
    }

    return (
        <div>
            { visible && (
                <div className="formClass">
                    <label className="titleDelete">Choose the user id you want to delete.</label>
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

export default DeleteUserForm;
