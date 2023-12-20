import React from 'react';
import { Gradient } from './Gradient.js'
import './Gradient.css';

const GradientBackground: React.FC = () => {
    return (
        <div>
            <canvas id="gradient-canvas" data-transition-in />
        </div>
    );
}

export default GradientBackground;