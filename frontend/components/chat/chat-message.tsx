import {Card, CardBody, CardHeader} from "@nextui-org/react";
import {format} from "date-fns/format";

export default function ChatMessage(
    {
        chatMessage,
        isOwner,
    }: {
        chatMessage: ChatMessageResponse,
        isOwner: boolean
    },
) {

    const messageAlignment = isOwner ? 'flex-end' : 'flex-start';
    const ownerColor = isOwner ? '#E1FFC7' : '#FFFFFF';
    const formattedTime = format(chatMessage.createdDateTime, `yyyy-MM-dd h:mm a`)

    return (
        <div className="flex" style={{justifyContent: messageAlignment, margin: "5px"}}>
            <Card className="max-w-[350px]" shadow="none" style={{background: ownerColor}}>
                {!isOwner && (
                    <CardHeader className="flex gap-1.5" style={{fontSize: '10px'}}>
                        <p style={{fontWeight: "bold"}}>{chatMessage.senderId}</p>
                        <p>{formattedTime}</p>
                    </CardHeader>
                )}
                <CardBody className="pt-1">
                    <p>{chatMessage.text}</p>
                </CardBody>
            </Card>
        </div>
    );
}