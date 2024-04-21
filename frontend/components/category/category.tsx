import CategoryResponse from "@/model/dto/response/CategoryResponse";
import styles from "@styles/category.module.css";
import {ButtonGroup, Card, CardBody} from "@nextui-org/react";
import React from "react";
import CategoryCreateButton from "@/components/category/category-write-button";
import CategoryUpdateButton from "@/components/category/category-update-button";
import {CardFooter} from "@nextui-org/card";

export default function Category({category}: { category: CategoryResponse }) {
    return (
        <div className={styles.category}>
            <Card key={category.id}>
                <CardBody>
                    <p>{category.name}</p>
                </CardBody>
                <CardFooter>
                    <ButtonGroup>
                        <CategoryCreateButton buttonName={"자식"} category={category}/>
                        <CategoryUpdateButton category={category}/>
                    </ButtonGroup>
                </CardFooter>
            </Card>
            {category.children?.map((category) => (
                <Category category={category} key={category.id}/>
            ))}
        </div>
    );

};