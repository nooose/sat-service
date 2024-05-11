import ClientEditor from "@/components/editor/client-editor";
import React from "react";

export default function ClientReadonlyEditor({initialValue}: {initialValue: string}) {
    return (
        <div>
            <ClientEditor initialValue={initialValue} editorRef={null}/>
        </div>
    );
}
