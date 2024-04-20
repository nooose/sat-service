'use client'

import {NextUIProvider} from '@nextui-org/react'
import React from "react";
import { LoginProvider } from './authProvider';

export function Providers({children}: { children: React.ReactNode }) {
  return (
    <NextUIProvider>
        <LoginProvider>
            <main className="light text-foreground bg-background">
                {children}
            </main>
        </LoginProvider>
    </NextUIProvider>
  )
}