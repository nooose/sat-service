import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import styles from "../styles/layout.module.css"
import TopNavigation from "@/components/top-navigation";
import {Providers} from "./providers";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "S.A.T",
  description: "Stuff all techs",
};

export default async function RootLayout({children}: { children: React.ReactNode }) {
  return (
    <html lang="ko">
      <body>
        <Providers>
          <TopNavigation/>
            <div className={styles.container}>
              {children}
            </div>
        </Providers>
      </body>
    </html>
  );
}