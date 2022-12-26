import axios from 'axios';


// const [userState, dispatch] = useContext(AuthContext);


const request = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')

    },
});



export const addModel = async (model) => {
    const response = await request.post('/models',  JSON.stringify(model));
    return response;
}

export const addColor = async (data) => {
    const response = await request.post('/models/colors', JSON.stringify(data));
    return response;
}


export default request;