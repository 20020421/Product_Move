import axios from 'axios';


const qs = require('qs');

const request = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
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

export default request;