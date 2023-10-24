import newAxios from 'axios';

const API_SERVER = import.meta.env.VITE_API_SERVER;

const axiosInstance = newAxios.create({
    baseURL: API_SERVER,
});

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
