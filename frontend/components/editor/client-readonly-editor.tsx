"use client"

import React from "react";
import {Button} from "@nextui-org/react";

export default function ClientReadonlyEditor({initialValue}: {initialValue: string}) {
    return (
        <Button
            color={"warning"}
        >
            닉네임 변경
        </Button>
    );
}
