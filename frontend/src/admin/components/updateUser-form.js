import './updateUser-form.css';
import React, {useEffect, useState} from "react";

function UpdateUserForm({visible, onClose, updateUser}){
    const [id, setIds] = useState([]);
    const [selectedId, setSelectedId]= useState('');
    const [name, setName]= useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    useEffect(() => {
        async function fetchIds () {
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

              //  console.log(token);

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
        const user = {
            id: selectedId,
            name: name,
            username: username,
            password: password,
        }

      console.log("Id ul este: ", id);
      console.log("Numele este", name);
      updateUser(user);
      console.log(user)
      setName('');
      setUsername('');
      setPassword('');
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
                                {id.map((id) => (
                                    <option key={id} value={id}>
                                        {id}
                                    </option>
                                ))}
                        </select>
                    </div>
                    <div className="input-container">
                        <label>Name</label>
                        <input className="input"
                               type="text"
                               value={name}
                               placeholder={"Name?"}
                               onChange={(e) => setName(e.target.value)} />
                    </div>
                    <div className="input-container">
                        <label>Username</label>
                        <input className="input"
                               type="text"
                               value={username}
                               placeholder={"Username?"}
                               onChange={(e) => setUsername(e.target.value)} />
                    </div>
                    <div className="input-container">
                        <label>Password</label>
                        <input className="input"
                               type="text"
                               value={password}
                               placeholder={"Password?"}
                               onChange={(e) => setPassword(e.target.value)} />
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

export default UpdateUserForm;

