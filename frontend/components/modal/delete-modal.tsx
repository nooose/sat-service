"use client"

import React from "react";
import {Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader} from "@nextui-org/react";
export default function DeleteModal({isOpen, onOpenChange, deleteEvent}: {isOpen: any, onOpenChange: any, deleteEvent: any}) {
    return (
        <>
            <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
                <ModalContent>
                    {(onClose) => (
                        <>
                            <ModalHeader className="flex flex-col gap-1">확인</ModalHeader>
                            <ModalBody>
                                <p>
                                    삭제 하시겠습니까?
                                </p>
                            </ModalBody>
                            <ModalFooter>
                                <Button color="danger" variant="light" onPress={onClose}>
                                    취소
                                </Button>
                                <Button color="primary" onPress={deleteEvent}>
                                    확인
                                </Button>
                            </ModalFooter>
                        </>
                    )}
                </ModalContent>
            </Modal>
        </>
    );
}
