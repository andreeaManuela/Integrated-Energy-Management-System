import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from "./home/home";
import Login from "./login/login";
import Admin from "./admin/admin";
import './App.css'
import Navbar from "./navbar/NavigationBar"
import Client from "./client/client";
import './SecurityRoute';
import SecurityRoute from "./SecurityRoute";
import NotificationBanner from "./NotificationBanner";
import {WebSocketProvider} from "./websocketService/WebSocketProvider";

function App() {
    const currentUserRole = localStorage.getItem('userRole');
    const websocketUrl = 'http://localhost:8082/websocket';

    return (
        <WebSocketProvider>
        <NotificationBanner url={websocketUrl} />
    <Router>
        <Navbar/>
        <Routes>
            <Route path="/" element={<Home/>} />
            <Route path="/login" element={<Login/>} />
            <Route
                path="/admin"
                element={currentUserRole === "ADMIN" ? <Admin /> : <SecurityRoute requiredRole="ADMIN" />}
            />
            <Route
                path="/client"
                element={currentUserRole === "CLIENT" ? <Client /> : <SecurityRoute requiredRole="CLIENT" />}
            />
        </Routes>
    </Router>
        </WebSocketProvider>

    );
}

export default App;
