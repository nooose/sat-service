
export const login = (memberInfo) => {
    return {
        type: 'LOGIN',
        payload: memberInfo,
    };
};

export const logout = () => {
    return {
        type: 'LOGOUT',
    };
};
