import React from 'react';
import './LoadingFishingRod.css';

const LoadingFishingRod: React.FC = () => {
    return (
        <div className="fishingRodContainer">
            {/* This is a very basic representation. You might want a detailed SVG for better appearance */}
            <svg viewBox="0 0 100 100" className="fishingRodSVG">
                <line x1="10" y1="50" x2="90" y2="50" stroke="black" strokeWidth="5" />
                <circle cx="10" cy="50" r="8" fill="black" />
            </svg>
        </div>
    );
};

export default LoadingFishingRod;
