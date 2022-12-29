import axios from 'axios';





export const addModel = async (model) => {
    const request = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    
        },
    });
    const response = await request.post('/models',  JSON.stringify(model));
    return response;
}

export const addColor = async (data) => {
    const request = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    
        },
    });
    const response = await request.post('/models/colors', JSON.stringify(data));
    return response;
}


export const addWarehouse = async (warehouse) => {
    const request = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    
        },
    });
    
    const response = await request.post('/warehouses', JSON.stringify(warehouse));
    return response;
}

export const warehousingFactory = async (data) => {
    const request = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    
        },
    });
    const response = request.post('/factories/warehousing', JSON.stringify(data));
    return response;
}

export const exportProducts = async (data) => {
    const request = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    
        },
    });
    const response = request.post('/factories/export', JSON.stringify(data));
    return response;
}

export const distributorWarehousingAll = async (data) => {
    const request = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    
        },
    });
    const response = request.post('/distributors/warehousing', JSON.stringify(data));
    return response;
}

export const newPurchase = async (data) => {
    const request = axios.create({
        baseURL: 'http://localhost:8080/api/v1',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
    
        },
    });
    const response = request.post('/distributors/payments', JSON.stringify(data));
    return response;
}
