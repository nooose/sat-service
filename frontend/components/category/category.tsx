"use client"

import CategoryResponse from "@/model/dto/response/CategoryResponse";
import styles from "@styles/category.module.css";
import {Button, Card, CardBody} from "@nextui-org/react";
import React, {useState} from "react";
import ChildCategoryWrite from "@/components/category/child-category-write";
import CategoryEdit from "@/components/category/category-edit";

export default function Category({category}: { category: CategoryResponse }) {
    const [isCreateOpen, setIsCreateOpen] = useState(false);
    const [isEdit, setIsEdit] = useState(false)

    const childCategoryToggleAccordion = () => {
        setIsCreateOpen(!isCreateOpen);
    }

    const editAction = () => {
        setIsEdit(!isEdit)
    };

    return (
        <div className={styles.category}>
            <Card key={category.id}>
                <CardBody>
                    <div style={{display: 'flex', alignItems: 'center'}}>
                        {!isEdit ?
                            <p>{category.name}</p>
                            :
                            <CategoryEdit category={category} setIsEdit={setIsEdit}/>
                        }
                        {!isEdit &&
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
                        <ChildCategoryWrite parentCategory={category} setIsCreateOpen={setIsCreateOpen}/>
                    </CardBody>
                </Card>
            }
            {category.children?.map((category) => (
                <Category category={category} key={category.id}/>
            ))}
        </div>

    );

};