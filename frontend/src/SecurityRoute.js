import { Route, Navigate } from 'react-router-dom';

function SecurityRoute({ requiredRole }) {
    if (requiredRole === "ADMIN") {
        window.location.href = '/login';
        return null;
    }

    if (requiredRole === "CLIENT") {
        window.location.href = '/';
        return null;
    }

    return null;
}

export default SecurityRoute;
