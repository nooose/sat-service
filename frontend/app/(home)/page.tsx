import Articles from "@/components/article/articles";
import {Suspense} from "react";
import styles from "@/styles/home.module.css"
import ArticleWriteButton from "@/components/article/article-write-button";

export default async function Home() {
    return (
        <div className={styles.container}>
            <div className={styles.writeButton}>
                <ArticleWriteButton/>
            </div>
            <Suspense>
                <Articles/>
            </Suspense>
        </div>
    );
}
