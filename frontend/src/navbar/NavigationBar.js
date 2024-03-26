import React from 'react'
import {
    Nav,
    NavLink,
} from 'reactstrap';
import './NavigationBar.css';
import image from './icon.png';


export default function NavigationBar(){
    return(
        <div>
            <nav className="navbar navbar-expand-lg navbar -dark bg-info with-border">
                <a className="navbar-brand">
                    <img src={image} width="70" height="60" align="left"></img>
                </a>
                <NavLink href={"/login"} className="my-custom-link">LOGIN</NavLink>
            </nav>
        </div>
    )
}