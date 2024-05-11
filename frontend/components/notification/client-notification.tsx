"use client"

import React, {useEffect} from "react";
import {EventSourcePolyfill, NativeEventSource} from "event-source-polyfill";
import {API_HOST} from "@/utils/rest-client";
import 'react-toastify/dist/ReactToastify.css';
import {toast, ToastContainer} from "react-toastify";
import {useRouter} from "next/navigation";

export default function ClientNotification() {
    const router = useRouter();

    let articleId: number;
    const notify = (message: string) => {
        toast(message, {
            onClick: (event: React.MouseEvent) => {
                router.push(`/articles/${articleId}`);
            },
            pauseOnHover: false,
        });
    }

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
            articleId = data.data;
            notify(`${data.title}`);
        });

        return () => {
            eventSource.close();
        };
    }, []);

    return <ToastContainer/>
}
