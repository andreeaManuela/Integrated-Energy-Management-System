import React, {useState} from "react";
import './addUser-form.css';

function AddUserForm({visible, onClose, insertUser}){
    const [name, setName]= useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, getRole] = useState('ADMIN');

   const handleRoleChange = (e) =>{
       getRole(e.target.value);
   }

   const handleSubmit = async () => {
       if (name.trim() === '' || username.trim() === '' || password.trim() === '') {
           alert('Please fill in all the fields!');
           return;
       }

       const user = {
           name: name,
           username: username,
           password: password,
           role: role
       }

       insertUser(user);


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
                    <label>Name</label>
                    <input className="input"
                        type="text"
                        value={name}
                        placeholder={"What's your name?"}
                        onChange={(e) => setName(e.target.value)} />
                </div>
                <div className="input-container">
                    <label>Username</label>
                    <input className="input"
                           type="text"
                           value={username}
                           placeholder={"What's your username?"}
                           onChange={(e) => setUsername(e.target.value)} />
                </div>
                <div className="input-container">
                    <label>Password</label>
                    <input className="input"
                           type="text"
                           value={password}
                           placeholder={"What's your password?"}
                           onChange={(e) => setPassword(e.target.value)} />
                </div>
                <div className="input-container">
                    <label>Role</label>
                    <select className="input"
                           value={role}
                           onChange={handleRoleChange}>
                        <option value="ADMIN">ADMIN</option>
                        <option value="CLIENT">CLIENT</option>
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

export default AddUserForm;