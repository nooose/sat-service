import {Box, Card, CardContent, CardHeader, Grid, Typography} from "@mui/material";
import {styled} from '@mui/material/styles';
import React, {useEffect} from "react";

const data = [
    {
        id: 1,
        title: "제목1",
        contents: "내용이 길어내용이 길어내용이 길어",
        startDate: "2023-01-01",
        endDate: "2023-02-01"
    },
    {
        id: 2,
        title: "제목 길이 제한 걸려있다.",
        contents: "내용2",
        startDate: "2023-02-01",
        endDate: "2023-03-01"
    },
    {
        id: 3,
        title: "제목3",
        contents: "내용3",
        startDate: "2023-02-01",
        endDate: "2023-03-01"
    },
    {
        id: 4,
        title: "제목3",
        contents: "내용3",
        startDate: "2023-02-01",
        endDate: "2023-03-01"
    },
];

const Body = () => {
    useEffect(() => {
        // TODO: 스터디그룹 정보 받기
        console.log("스터디그룹 정보 받기");
    }, []);

    return (
        <Box m={20}>
            <Grid container spacing={5}>
                {data.map((studyGroup, index) => renderCard(studyGroup, index))}
            </Grid>
        </Box>
    );
};


const CardWrapper = styled(Card)({
    transition: 'transform 0.2s',
    '&:hover': {
        transform: 'scale(1.15)',
    },
});

const renderCard = (studyGroup, index) => {
    return (
        <Grid key={index} item xs={12} sm={6} md={4} lg={3}>
            <CardWrapper onClick={() => handleCardClick(studyGroup)}>
                <CardHeader title={studyGroup.title}/>
                <CardContent>
                    <Typography noWrap>{studyGroup.contents}</Typography>
                    <Typography>시작일: {studyGroup.startDate}</Typography>
                    <Typography>종료일: {studyGroup.endDate}</Typography>
                </CardContent>
            </CardWrapper>
        </Grid>
    );
};

const handleCardClick = (studyGroup) => {
    console.log('Clicked card:', studyGroup.id);
};

export default Body;
