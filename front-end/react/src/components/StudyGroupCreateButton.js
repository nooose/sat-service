import React, {useEffect, useState} from 'react';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField,
    Grid, MenuItem, InputLabel, Select, FormControl, Box,
} from '@mui/material';
import {DemoContainer} from '@mui/x-date-pickers/internals/demo';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import {LocalizationProvider} from '@mui/x-date-pickers/LocalizationProvider';
import {DatePicker} from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs'
import {navigateRelativePath} from "../common/common";
import axiosInstance from './AxiosConfig';

const StudyGroupCreateButton = ({ isOpen, handleCloseDialog }) => {
    const [open, setOpen] = useState(false);
    const [title, setTitle] = useState('');
    const [contents, setContents] = useState('');
    const [category, setCategory] = useState('');
    const [maxCapacity, setMaxCapacity] = useState(2);
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [studyRounds, setStudyRounds] = useState(1);
    const [items, setItems] = useState([]);

    useEffect(() => {
        axiosInstance.get('/v1/studygroups/categories')
            .then(response => {
                console.log(response.data);
                setItems(response.data);
            })
            .catch(error => {
                console.log(error);
            });

    }, []);

    const handleSubmit = () => {
        const studyGroupRequest = {
            title,
            contents,
            category,
            maxCapacity,
            startDateTime: startDate,
            endDateTime: endDate,
            studyRounds
        };

        console.log(studyGroupRequest);
        axiosInstance.post('/v1/studygroups', studyGroupRequest, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem("sat-access-token"),
            },
        }).then(response => {
            console.log(response.data);
            navigateRelativePath("/");
        })
            .catch(error => {
                console.log(error)
            });
        handleCloseDialog();
    };


    return (
        <Dialog open={isOpen} onClose={handleCloseDialog}>
            <DialogTitle>스터디그룹 생성</DialogTitle>
            <DialogContent dividers>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            autoFocus
                            label="제목"
                            type="text"
                            fullWidth
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="내용"
                            type="text"
                            fullWidth
                            multiline
                            rows={4}
                            value={contents}
                            onChange={(e) => setContents(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <Box sx={{minWidth: 125}}>
                            <FormControl fullWidth>
                                <InputLabel id="select-small-label">카테고리</InputLabel>
                                <Select
                                    labelId="select-small-label"
                                    id="select-small"
                                    label="카테고리"
                                    value={category}
                                    onChange={(e) => setCategory(e.target.value)}>
                                    {items.map((item) => (<MenuItem value={item}>{item}</MenuItem>))}
                                </Select>
                            </FormControl>
                        </Box>
                    </Grid>
                    <Grid item xs={3}>
                        <TextField
                            label="스터디 하루 회차"
                            type="number"
                            fullWidth
                            value={studyRounds}
                            onChange={(e) => {
                                if (e.target.value < 1) {
                                    setStudyRounds(1);
                                }
                                setStudyRounds(e.target.value)
                            }}
                        />
                    </Grid>
                    <Grid item xs={3}>
                        <TextField
                            label="최대 수용 인원"
                            type="number"
                            fullWidth
                            value={maxCapacity}
                            onChange={(e) => {
                                if (e.target.value < 2) {
                                    setMaxCapacity(2);
                                }
                                setMaxCapacity(e.target.value);
                            }}
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <LocalizationProvider dateAdapter={AdapterDayjs} dateFormats={{monthShort: `M`}}>
                            <DemoContainer components={['DatePicker']}>
                                <DatePicker
                                    label="시작일"
                                    format="YYYY-MM-DD"
                                    value={startDate}
                                    onChange={(newValue) => setStartDate(newValue)}
                                    shouldDisableDate={day => {
                                        const today = new Date();
                                        const formattedToday = dayjs(today).format("YYYY-MM-DD");
                                        return dayjs(day).isBefore(dayjs(formattedToday));
                                    }}
                                />
                            </DemoContainer>
                        </LocalizationProvider>
                    </Grid>
                    <Grid item xs={6}>
                        <LocalizationProvider dateAdapter={AdapterDayjs} dateFormats={{monthShort: `M`}}>
                            <DemoContainer components={['DatePicker']}>
                                <DatePicker
                                    label="종료일"
                                    format="YYYY-MM-DD"
                                    value={endDate}
                                    onChange={(newValue) => setEndDate(newValue)}
                                    shouldDisableDate={day => {
                                        const today = new Date();
                                        const formattedToday = dayjs(today).format("YYYY-MM-DD");
                                        return dayjs(day).isBefore(dayjs(formattedToday));
                                    }}
                                />
                            </DemoContainer>
                        </LocalizationProvider>
                    </Grid>
                    {/*다른 입력 필드들 추가 */}
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleCloseDialog} color="error">
                    취소
                </Button>
                <Button onClick={handleSubmit} color="success">
                    생성
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default StudyGroupCreateButton;
