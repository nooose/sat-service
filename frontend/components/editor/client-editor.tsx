'use client';

import '@toast-ui/editor/dist/toastui-editor.css';
import {Editor} from '@toast-ui/react-editor';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax'
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight'
import Prism from 'prismjs'
import React from "react";

const toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr'],
    ['ul', 'ol', 'task'],
    ['table', 'link'],
    ['image'],
    ['code', 'codeblock'],
    ['scrollSync'],
];

export default function ClientEditor({
                                         initialValue,
                                         editorRef,
                                     }: {
    initialValue: string,
    editorRef: any,
}) {
    return (
        <>
            <Editor
                ref={editorRef}
                initialValue={initialValue}
                initialEditType='markdown'
                previewStyle='vertical' // tab || vertical
                hideModeSwitch={true}
                height="calc(100vh - 380px)"
                theme={''} // '' & 'dark'
                usageStatistics={false}
                toolbarItems={toolbarItems}
                useCommandShortcut={true}
                plugins={[colorSyntax, [codeSyntaxHighlight, {highlighter: Prism}]]}
            />
        </>
    )
}