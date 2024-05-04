"use client"

import React from "react";
import {Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader} from "@nextui-org/react";

export default function ErrorModal({title, message, disclosure}: {title?: string, message: string, disclosure: any}) {
    return (
        <>
            <Modal isOpen={disclosure.isOpen} onOpenChange={disclosure.onOpenChange}
                   backdrop={"blur"}>
                <ModalContent>
                    {(onClose) => (
                        <>
                            <ModalHeader className="flex flex-col gap-1">{title == undefined ? "에러" : title}</ModalHeader>
                            <ModalBody>
                                <p>
                                    {message}
                                </p>
                            </ModalBody>
                            <ModalFooter>
                                <Button color="danger" variant="light" onPress={onClose}>
                                    닫기
                                </Button>
                            </ModalFooter>
                        </>
                    )}
                </ModalContent>
            </Modal>
        </>
    );
}
