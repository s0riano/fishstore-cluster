import * as React from 'react';
import {createRoot} from "react-dom/client";
import ReactDOM from 'react-dom';
import { App } from './app'

const element = document.getElementById("app");
const root = createRoot(element);



root.render(<App/>);