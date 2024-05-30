"use client"

import {
    Button,
    Card,
    CardBody,
    CardFooter,
    CardHeader,
    Image,
    useDisclosure
} from "@nextui-org/react";
import React, {useState} from "react";
import CommentResponse from "@/model/dto/response/CommentResponse";
import CommentUpdate from "@/components/comment/client-comment-update";
import {Textarea} from "@nextui-org/input";
import ChildCategoryWrite from "@/components/comment/client-children-comment-write";
import styles from "@styles/comment.module.css"
import deleteStyles from "@styles/delete-button.module.css";
import {RestClient} from "@/utils/rest-client";
import {useRouter} from "next/navigation";
import {errorToast} from "@/utils/toast-utils";
import DeleteModal from "@/components/modal/delete-modal";

export default function ClientComment({articleId, comment, loginUserId}: {
    articleId: number,
    comment: CommentResponse,
    loginUserId: number | undefined | null
}) {
    const [isUpdateOpen, setIsUpdateOpen] = useState(false);
    const [isReplyOpen, setIsReplyOpen] = useState(false);
    const {isOpen, onOpen, onOpenChange} = useDisclosure();

    const updateToggleAccordion = () => {
        setIsReplyOpen(false);
        setIsUpdateOpen(!isUpdateOpen);
    }

    const toggleReply = () => {
        setIsUpdateOpen(false);
        setIsReplyOpen(!isReplyOpen);
    }

    const router = useRouter();
    const deleteEvent = () => {
        RestClient.delete(`/board/comments/${comment.id}`)
            .successHandler(() => {
                router.push(`/articles/${articleId}`);
                router.refresh();
            })
            .errorHandler(error => {
                errorToast("존재하지 않는 게시글이므로 삭제에 실패했습니다.");
            })
            .fetch();
    }

    if (comment.isDeleted) {
        return (
            <div className={styles.comment}>
                <Card key={comment.id}>
                    <CardBody>
                        삭제된 댓글입니다.
                    </CardBody>
                </Card>

                <div className={styles.children}>
                    {comment.children?.map(
                        (comment: CommentResponse) => (
                            <ClientComment articleId={articleId}
                                           key={comment.id}
                                           comment={comment}
                                           loginUserId={loginUserId}
                            />
                        )
                    )}
                </div>
            </div>
        )
    }

    return (
        <div className={styles.comment}>
            <Card key={comment.id}>
                <CardHeader className="flex items-center justify-between">
                    <div className="flex items-center gap-3">
                        <Image
                            alt="avatar"
                            height={30}
                            radius="sm"
                            src="https://avatars.githubusercontent.com/u/86160567?s=200&v=4"
                            width={30}
                        />
                        <div className="flex flex-col">
                            <p>{comment.memberName}</p>
                        </div>
                    </div>
                    {loginUserId === comment.memberId ?
                        <div>
                            <button className={deleteStyles.deleteButton} onClick={onOpen}>
                                <Image src={"/x-button.svg"} alt="Delete"/>
                            </button>
                        </div>
                        :
                        ''
                    }
                </CardHeader>
                <CardBody>
                    {isUpdateOpen ?
                        <CommentUpdate
                            articleId={articleId}
                            comment={comment}
                            setIsUpdateOpen={setIsUpdateOpen}/>
                        :
                        <Textarea
                            variant="flat"
                            value={comment.content}
                            readOnly={true}
                        />
                    }
                </CardBody>
                <CardFooter>
                    <div className="flex items-center gap-1">
                        {loginUserId === comment.memberId ?
                            <Button size={"sm"}
                                    color={"danger"}
                                    onClick={updateToggleAccordion}>
                                수정
                            </Button>
                            :
                            <div/>
                        }
                        <Button size={"sm"}
                                color={"primary"}
                                onClick={toggleReply}>
                            댓글
                        </Button>
                    </div>
                </CardFooter>
            </Card>

            {isReplyOpen &&
                <ChildCategoryWrite
                    articleId={articleId}
                    parentId={comment.id}
                    setIsReplyOpen={setIsReplyOpen}
                />
            }

            <div className={styles.children}>
                {comment.children?.map(
                    (comment: CommentResponse) => (
                        <ClientComment articleId={articleId}
                                       key={comment.id}
                                       comment={comment}
                                       loginUserId={loginUserId}
                        />
                    )
                )}
            </div>
            <DeleteModal
                isOpen={isOpen}
                onOpenChange={onOpenChange}
                deleteEvent={deleteEvent}
            />
        </div>
    );
};
