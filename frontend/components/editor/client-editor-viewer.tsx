'use client';

import '@toast-ui/editor/dist/toastui-editor.css';
import {Viewer} from '@toast-ui/react-editor';
import React from "react";

export default function ClientEditorViewer({
                                               initialValue,
                                           }: {
    initialValue: string,
}) {
    return (
        <>
            <Viewer
                initialValue={initialValue}
                initialEditType='markdown'
                height="calc(100vh - 380px)"
                usageStatistics={false}
            />
        </>
    )
}