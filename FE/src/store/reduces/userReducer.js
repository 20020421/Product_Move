import actionTypes from '../actions/actionType';
import jwtDecode from 'jwt-decode';

const getUserInfoByAccessToken = (accessToken) => {
    if (accessToken) {
        const decode = jwtDecode(accessToken);
        if (tokenValid(decode.exp)) {
            return {
                name: decode.sub,
                role: decode.role[0],
            };
        } else {
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('access_token');
        }
    }

    return null;
};

const tokenValid = (expiry) => {
    const currentDate = Date.now() / 1000;
    return currentDate < expiry;
};

export const initialState = {
    isLoggedIn: localStorage.getItem('isLoggedIn') === 'true' || false,
    userInfo: localStorage.getItem('access_token')
        ? getUserInfoByAccessToken(localStorage.getItem('access_token'))
        : null,
    accessToken: localStorage.getItem('access_token') || null,
    refreshToken: localStorage.getItem('refresh_token') || null,
};

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.USER_LOGIN_SUCCESS:
            localStorage.setItem('isLoggedIn', true);
            localStorage.setItem('access_token', action.userInfo.access_token);
            localStorage.setItem('refresh_token', action.userInfo.refresh_token);
            // console.log(action.userInfo.access_token)
            return {
                ...state,
                isLoggedIn: true,
                userInfo: getUserInfoByAccessToken(localStorage.getItem('access_token')),
                accessToken: action.userInfo.access_token,
                refreshToken: action.userInfo.refresh_token,
            };
        case actionTypes.PROCESS_LOGOUT:
            localStorage.setItem('isLoggedIn', false);
            localStorage.removeItem('access_token');
            localStorage.removeItem('refresh_token');
            return {
                ...state,
                isLoggedIn: false,
                userInfo: null,
                accessToken: null,
                refreshToken: null
            }


    
        default:
            return state;
    }
};

export default userReducer;