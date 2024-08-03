"use client"

import React from "react";
import {Table, TableBody, TableCell, TableColumn, TableHeader, TableRow} from "@nextui-org/react";

export const formatNumberWithCommas = (number: number) => {
    return new Intl.NumberFormat('en-US').format(number);
};

export default function PointRanking({pointRankings}: { pointRankings: any }) {
    return (
        <div>
            <Table>
                <TableHeader>
                    <TableColumn>순위</TableColumn>
                    <TableColumn>이름</TableColumn>
                    <TableColumn>포인트</TableColumn>
                </TableHeader>
                <TableBody>
                    {pointRankings.map((pointRanking: PointRankingResponse, index: number) =>
                        <TableRow key={index + 1}>
                            <TableCell>{index + 1}</TableCell>
                            <TableCell>{pointRanking.memberName}</TableCell>
                            <TableCell>{formatNumberWithCommas(pointRanking.point)}</TableCell>
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        </div>
    )
};