import {Spinner} from "@nextui-org/spinner";
import styles from "@/styles/loading.module.css"

export default function Loading() {
    return (
        <div className={styles.loadingContainer}>
            <Spinner size={"lg"}/>
        </div>
    );
}
