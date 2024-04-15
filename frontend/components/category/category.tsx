import CategoryResponse from "@/model/response/CategoryResponse";
import styles from "@styles/category.module.css";
import {Accordion, AccordionItem, Button, Card, CardBody} from "@nextui-org/react";
import React from "react";
import ChildCategoryWrite from "@/components/category/child-category-write";

export default function Category({ category }: { category: CategoryResponse }) {
    if (category && category.children) {
        return (
            <div className={styles.category}>
                <Card key={category.id}>
                    <CardBody>
                        <p>{category.name}</p>
                    </CardBody>
                </Card>
                {category.children.map((category: CategoryResponse) => (
                    <Category category={category}/>
                ))}
            </div>
        );
    }
    return (
        <div className={styles.category}>
            <Card key={category.id}>
                <CardBody>
                    <p>{category.name}</p>
                </CardBody>
            </Card>
        </div>
    );
};