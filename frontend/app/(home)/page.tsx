import Articles from "@/components/article/articles";
import {Suspense} from "react";
import styles from "@/styles/home.module.css"
import ArticleWriteButton from "@/components/article/article-write-button";
import {Spinner} from "@nextui-org/spinner";
import {cookies} from "next/headers";
import {getUserInfo} from "@/components/user-login";

export default async function Home() {
    const cookie = cookies().get("JSESSIONID")?.value
    const userInfo = await getUserInfo(cookie);

    return (
        <div className={styles.container}>
            { userInfo.isAuthenticated() &&
                <div className={styles.writeButton}>
                    <ArticleWriteButton/>
                </div>
            }
            <Suspense fallback={<Spinner/>}>
                <Articles/>
            </Suspense>
        </div>
    );
}
