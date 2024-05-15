"use client"

import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {Button, Card, CardBody, CardFooter} from "@nextui-org/react";
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
        <div>
            <Card key={category.id}>
                <CardBody>
                    <div>
                        {!isClickedEditButton ?
                            <p>{category.name}</p>
                            :
                            <ClientCategoryEdit category={category} setIsEdit={setIsClickedEditButton}/>
                        }
                    </div>
                </CardBody>
                {!isClickedEditButton &&
                    <CardFooter className="flex justify-between">
                        <Button
                            color={"primary"}
                            size={"sm"} onClick={childCategoryToggleAccordion}>
                            자식 등록
                        </Button>
                        <Button
                            color={"danger"}
                            size={"sm"}
                            onClick={editAction}>
                            수정
                        </Button>
                    </CardFooter>
                }
            </Card>
            {isCreateOpen &&
                <Card>
                    <CardBody>
                        <ClientChildCategoryWrite parentCategory={category} setIsCreateOpen={setIsCreateOpen}/>
                    </CardBody>
                </Card>
            }
            <div style={{paddingLeft: 30}}>
                {category.children?.map((category) => (
                    <ClientCategory category={category} key={category.id}/>
                ))}
            </div>
        </div>
    );
};
