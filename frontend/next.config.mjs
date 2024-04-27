/** @type {import('next').NextConfig} */
const nextConfig = {
    env: {
        API_HOST: process.env.API_HOST,
        LOGIN_REQUEST_URL: process.env.LOGIN_REQUEST_URL,
    },
};

export default nextConfig;
