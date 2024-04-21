import Articles from "@/components/article/articles";
import {Suspense} from "react";
import styles from "@/styles/home.module.css"
import ArticleWriteButton from "@/components/article/article-write-button";
import {Spinner} from "@nextui-org/spinner";
import {AccordionItem} from "@nextui-org/accordion";
import { Accordion } from "@nextui-org/react";

export const revalidate = 0;

export default async function Home() {
    const defaultContent =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";

    return (
        <div className={styles.container}>
            <div className={styles.writeButton}>
                <ArticleWriteButton/>
            </div>
            <Accordion>
                <AccordionItem key="1" aria-label="Accordion 1" title="Accordion 1">
                    {defaultContent}
                </AccordionItem>
                <AccordionItem key="2" aria-label="Accordion 2" title="Accordion 2">
                </AccordionItem>
                <AccordionItem key="3" aria-label="Accordion 3" title="Accordion 3">

                </AccordionItem>
            </Accordion>
            <Suspense fallback={<Spinner/>}>
                <Articles/>
            </Suspense>
        </div>
    );
}
