import Articles from "@/components/article/articles";
import {Suspense} from "react";
import styles from "@/styles/home.module.css"
import ArticleWriteButton from "@/components/article/article-write-button";
import {Spinner} from "@nextui-org/spinner";

export const revalidate = 0;

export default async function Home() {
    return (
        <div className={styles.container}>
            <div className={styles.writeButton}>
                <ArticleWriteButton/>
            </div>
            <Suspense fallback={<Spinner/>}>
                <Articles/>
            </Suspense>
        </div>
    );
}
