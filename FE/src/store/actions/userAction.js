import actionType from "./actionType"



export const userLogginSuccess = (userInfo) => ({
    type: actionType.USER_LOGIN_SUCCESS,
    userInfo
});

export const userLogout = () => ({
    type: actionType.PROCESS_LOGOUT,
})