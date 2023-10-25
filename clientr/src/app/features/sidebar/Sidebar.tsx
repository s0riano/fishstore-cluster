import React from 'react';
import './Sidebar.css';

const Sidebar: React.FC = () => {
    return (
        <div className="sidebar">
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="/fish">Fish</a></li>
                <li><a href="/seafood">Seafood</a></li>
                <li><a href="/about">About</a></li>
                <li><a href="/contact">Contact</a></li>
            </ul>
            <div className="wave"></div>
        </div>
    );
}

export default Sidebar;