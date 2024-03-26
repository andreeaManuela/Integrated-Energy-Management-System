import React from 'react';
import img from '../images/EnergyManagementSystem.jpeg';

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "100vh",
    backgroundImage: `url(${img})`
};

function Home(){
    return (
       <div style={backgroundStyle}></div>
    )
}
export default Home;