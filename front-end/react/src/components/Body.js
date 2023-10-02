import {Box, Card, CardContent, CardHeader, Fab, Grid, Typography} from "@mui/material";
import {styled} from '@mui/material/styles';
import React, {useEffect, useState} from "react";
import axios from "axios";
import StudyGroupCreateButton from "./StudyGroupCreateButton";
import {useAuth} from "./AuthProvider";
import AddIcon from "@mui/icons-material/Add";

let data

const Body = () => {
    const [studyGroups, setStudyGroups] = useState([]);
    const { isLoggedIn } = useAuth();
    const [isOpen, setIsOpen] = useState(false); // 다이얼로그 열림/닫힘 상태를 관리하는 상태 변수

    const handleOpenDialog = () => {
        setIsOpen(true);
    };

    const handleCloseDialog = () => {
        setIsOpen(false);
    };

    useEffect(() => {
        axios.get("/v1/studygroups")
            .then(response => {
                console.log(response.data);
                setStudyGroups(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return (
        <Box m={20}>
            <Grid container spacing={5}>
                {studyGroups.map((studyGroup, index) => renderCard(studyGroup, index))}
            </Grid>
            {isLoggedIn ? (
                <Fab color="primary" aria-label="add" onClick={handleOpenDialog} handleCloseDialog={handleCloseDialog}
                     style={{
                         position: 'fixed',
                         bottom: '20px',
                         right: '20px',
                     }}>
                    <AddIcon/>
                </Fab>
            ) : (<div></div>)}
            <StudyGroupCreateButton isOpen={isOpen} handleCloseDialog={handleCloseDialog}/>
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
