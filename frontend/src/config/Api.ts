import axios from 'axios';

// This is the KEY CHANGE:
// 1. It checks for the environment variable you set on Netlify.
// 2. If it's not found (like when you run locally), it defaults to localhost.
export const API_URL = process.env.REACT_APP_API_URL || "http://localhost:5454";

// You can keep this for your reference, but it's not used
export const DEPLOYED_URL = "https://samcart.onrender.com"; // (or the -sz5d one)

// This 'api' object will now be DYNAMIC.
// On Netlify, it will use your Render URL.
// On your computer, it will use localhost.
export const api = axios.create({
  baseURL: API_URL, 
  headers: {
    'Content-Type': 'application/json',
  },
});