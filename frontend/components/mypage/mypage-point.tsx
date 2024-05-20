import {Card, CardBody} from "@nextui-org/react";
import MyPointResponse from "@/model/dto/response/MyPointResponse";
import {format} from "date-fns/format";

export default function MypagePoint({point}: { point: MyPointResponse }) {
    const formattedTime = format(point.createdDateTime, `yyyy-MM-dd h:mm a`)
    return (
        <div>
            <Card key={point.id}>
                <CardBody>
                    <div className="flex justify-between">
                        <div>
                            {point.type} {point.point} 포인트
                        </div>
                        <div className="font-semibold text-default-400 text-xs">
                            {formattedTime}
                        </div>
                    </div>
                </CardBody>
            </Card>
        </div>
    );
}
