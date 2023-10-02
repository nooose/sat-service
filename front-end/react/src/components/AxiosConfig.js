import axios from 'axios';

const axiosInstance = axios.create();

axiosInstance.interceptors.request.use((config) => {
        const token = localStorage.getItem('sat-access-token');
        config.headers.Authorization = token ? `Bearer ${token}` : "";
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
