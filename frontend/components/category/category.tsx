import CategoryResponse from "@/model/dto/response/CategoryResponse";
import styles from "@styles/category.module.css";
import {ButtonGroup, Card, CardBody} from "@nextui-org/react";
import React from "react";
import CategoryCreateButton from "@/components/category/category-write-button";
import CategoryUpdateButton from "@/components/category/category-update-button";

export default function Category({ category }: { category: CategoryResponse }) {
        return (
            <div className={styles.category}>
                <Card key={category.id}>
                    <CardBody>
                        <p>{category.name}</p>
                        <div className="flex gap-10 items-center">
                            <ButtonGroup>
                                <CategoryCreateButton buttonName={"자식"} category={category}/>
                                <CategoryUpdateButton category={category}/>
                            </ButtonGroup>
                        </div>
                    </CardBody>
                </Card>
                {category.children?.map((category: CategoryResponse) => (
                    <Category category={category} key={category.id}/>
                ))}
            </div>
        );

};