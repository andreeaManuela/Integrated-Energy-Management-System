import React, {useState} from 'react';
import img from './login3.jpg'
import {FaUser} from 'react-icons/fa';
import './login.css';
import {jwtDecode} from 'jwt-decode';

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "100vh",
    backgroundImage: `url(${img})`
};

const formStyle = {
    position: 'absolute',
    top: '30%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    padding: '50px 50px',
    borderRadius: '20px',
    textAlign: 'center',
    background: 'white',
    marginTop: '55px',
}

const containerStyle = {
    position: 'relative',
    width: '100%',
    height: '100vh',
    overflow: 'hidden',
    alignItems: 'center',
};

const inputStyle = {
    padding: '8px',
    margin: '8px',
    display: 'block',
    alignItems: 'center',
    marginTop: '20px',
};

const labelStyle = {
    display: 'inline-block',
};

const buttonStyle = {
    background: 'linear-gradient(to right, #ff7e5f, #feb47b)',
    padding: '10px 40px',
    marginTop: '40px' ,
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer',
}

const iconStyle = {
    marginRight: '8px',
}

const title = {
    fontFamily: 'Ibrir, sans-serif',
    fontSize: '2rem',
    color: '#333',
    textShadow: '2px 2px 4px rgba(0, 0, 0, 0.2)',
    marginBottom: '30px',
}

const lineStyle ={
    width: '170px',
    height: '2px',
    backgroundColor: '#FFA062',
    margin: '0 auto',
    marginBottom: '30px',
}

function Login(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMess, setErrorMess] = useState('');

    const handleLogin = async () => {
        try {
            const data = { username: username, password: password };

            const response = await fetch('http://localhost:8080/security/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (response.ok) {
                const { token } = await response.json();
                localStorage.setItem('authToken', token);
                console.log(token);

                const decodedToken= jwtDecode(token);
                localStorage.setItem('userRole', decodedToken.user_role);
                localStorage.setItem('userId', decodedToken.userId);
                localStorage.setItem('username', decodedToken.username);
                console.log(decodedToken.user_role);
                console.log(decodedToken.userId);

                if(decodedToken.user_role === 'ADMIN'){
                    window.location.href= `admin?userId=${decodedToken.userId}`;
                }else if(decodedToken.user_role === 'CLIENT'){
                    window.location.href= `client?userId=${decodedToken.userId}`;
                }else{
                    console.error('Utilizator necunoscut!');
                }

            }else {
                throw new Error('Eroare de la server');

            }


        } catch (error) {
            setErrorMess('Username or password incorrect! Try again!');
          //  alert('Username or password incorrect! Try again!');
            console.error('Eroare la trimiterea cererii:', error.message);
        }

    };

    return (
        <div style={backgroundStyle}>
            <div style={containerStyle} className="container">
                <form style={formStyle} className="box">
                    <h1 style={title}>LOG IN</h1>
                    <div style={lineStyle}></div>
                    <div className="fail" style={{display: errorMess ? 'block' : 'none', color: 'red'}}> {errorMess}
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                 <label className="label" style={labelStyle}>Username</label>
                 <input
                     style={inputStyle}
                     type="text"
                     value={username}
                     onChange={(e) => setUsername(e.target.value)}
                     required
                 />
                    </div>
                    <br />
                 <div style={{ display: 'flex', alignItems: 'center' }}>
                 <label className="label" style={labelStyle}>Password</label>
                  <input
                    style={inputStyle}
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                 </div>
                    <button style={buttonStyle} className="buton" type="button" onClick={handleLogin}>
                        <FaUser style={iconStyle}></FaUser>
                      LOGIN
                  </button>
                </form>
            </div>
        </div>
    )
}
export default Login;