import axios from 'axios';
import { useContext } from 'react';
import { AuthContext } from '../context/UserContext';


const qs = require('qs');

// const [userState, dispatch] = useContext(AuthContext);


const request = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')

    },
});

export const get = async (url) => {
    const response = await request.get(url);
    return response.data;
};

export const login = async (username, password) => {
    const response = await request.post('/login', qs.stringify({
        username,
        password
    }));

    return response;
};

export const addBranch = async (branch) => {
    console.log(branch)
    const response = await request.post("/api/v1/branches", JSON.stringify(branch));
    return response;
}

export default request;