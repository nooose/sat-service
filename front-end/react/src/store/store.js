import { configureStore } from '@reduxjs/toolkit';

const initialState = {
    isLoggedIn: false,
    memberInfo: null,
};

const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case 'LOGIN':
            return {
                ...state,
                isLoggedIn: true,
                memberInfo: action.payload,
            };
        case 'LOGOUT':
            return {
                ...state,
                isLoggedIn: false,
                memberInfo: null,
            };
        default:
            return state;
    }
};

const store = configureStore({
    reducer: {
        auth: authReducer,
    },
});

export default store;
