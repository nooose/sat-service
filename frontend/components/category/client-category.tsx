"use client"

import CategoryResponse from "@/model/dto/response/CategoryResponse";
import styles from "@styles/category.module.css";
import {Button, Card, CardBody} from "@nextui-org/react";
import React, {useState} from "react";
import ClientChildCategoryWrite from "@/components/category/client-child-category-write";
import ClientCategoryEdit from "@/components/category/client-category-edit";

export default function ClientCategory({category}: { category: CategoryResponse }) {
    const [isCreateOpen, setIsCreateOpen] = useState(false);
    const [isClickedEditButton, setIsClickedEditButton] = useState(false)

    const childCategoryToggleAccordion = () => {
        setIsCreateOpen(!isCreateOpen);
    }

    const editAction = () => {
        setIsClickedEditButton(!isClickedEditButton)
    };

    return (
        <div className={styles.category}>
            <Card key={category.id}>
                <CardBody>
                    <div style={{display: 'flex', alignItems: 'center'}}>
                        {!isClickedEditButton ?
                            <p>{category.name}</p>
                            :
                            <ClientCategoryEdit category={category} setIsEdit={setIsClickedEditButton}/>
                        }
                        {!isClickedEditButton &&
                            <div style={{display: 'flex', flexDirection: 'column', marginLeft: 'auto'}}>
                                <Button className={styles.categoryButton} color={"primary"} size={"sm"} onClick={childCategoryToggleAccordion}>자식 등록</Button>
                                <Button className={styles.categoryButton} color={"success"} size={"sm"} onClick={editAction}>수정</Button>
                            </div>
                        }
                    </div>
                </CardBody>
            </Card>
            {isCreateOpen &&
                <Card style={{marginTop: "10px"}}>
                    <CardBody>
                        <ClientChildCategoryWrite parentCategory={category} setIsCreateOpen={setIsCreateOpen}/>
                    </CardBody>
                </Card>
            }
            {category.children?.map((category) => (
                <ClientCategory category={category} key={category.id}/>
            ))}
        </div>
    );
};
