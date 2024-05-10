"use client"

import {useEffect} from "react";
import {EventSourcePolyfill, NativeEventSource} from "event-source-polyfill";
import {API_HOST} from "@/utils/restClient";
import 'react-toastify/dist/ReactToastify.css';
import {toast, ToastContainer} from "react-toastify";

export default function ClientNotification() {
    const notify = (message: string) => toast(message);

    useEffect(() => {
        const EventSource = EventSourcePolyfill || NativeEventSource;
        const eventSource = new EventSource(
            `${API_HOST}/notification:subscribe`, {
                withCredentials: true,
                heartbeatTimeout: 3600000,
            }
        );

        eventSource.addEventListener('notification', (event: any) => {
            const receivedConnectData = JSON.parse(event.data);
            if (receivedConnectData.title === 'SSE Connect') {
                console.log('connected');
            }
        });

        eventSource.addEventListener('comment-notification', (event: any) => {
            const data = JSON.parse(event.data);
            notify(`${data.title}`);
        });


        return () => {
            eventSource.close();
        };
    }, []);
    return <ToastContainer/>
}
