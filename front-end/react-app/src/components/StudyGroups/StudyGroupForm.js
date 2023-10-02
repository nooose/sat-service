import React, {useEffect, useState} from "react";
import {Form, Button, Container, Col, Row, InputGroup, FormControl} from "react-bootstrap";
import axiosInstance from "../Config/AxiosConfig";


function StudyGroupForm() {
    const [title, setTitle] = useState("");
    const [contents, setContents] = useState("");
    const [selectedCategory, setSelectedCategory] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [selectedDays, setSelectedDays] = useState([]);
    const [studyRounds, setStudyRounds] = useState(1);
    const [studyTimePerSession, setStudyTimePerSession] = useState(1);
    const [maxCapacity, setMaxCapacity] = useState(2);
    const [categories, setCategories] = useState([]);
    const daysOfWeek = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];

    useEffect(() => {
        axiosInstance.get('/v1/studygroups/categories')
            .then(response => {
                console.log(response.data);
                setCategories(response.data);
            })
            .catch(error => {
                console.log(error);
            });

    }, []);


    const handleDayChange = (day) => {
        const updatedSelectedDays = [...selectedDays];
        if (updatedSelectedDays.includes(day)) {
            updatedSelectedDays.splice(updatedSelectedDays.indexOf(day), 1);
        } else {
            updatedSelectedDays.push(day);
        }
        setSelectedDays(updatedSelectedDays);
    };

    const handleSubmit = (e) => {
        const studyGroupRequest = {
            title,
            contents,
            category: selectedCategory,
            maxCapacity,
            startDate,
            endDate,
            studyRounds: studyRounds,
            studyDays: selectedDays,
            timePerSession: studyTimePerSession
        };

        axiosInstance.post('/v1/studygroups', studyGroupRequest, {}).
        then(response => {
            console.log(response.data);
            window.location.href = "/";
        }).
        catch(error => {
            console.log(error)
        });
    };

    return (
        <Container fluid className="project-section d-flex justify-content-center align-items-center">
            <Form className="col-md-6" onSubmit={handleSubmit}>
                <Form.Group controlId="title">
                    <Form.Label className="form-label-name">스터디 그룹 이름</Form.Label>
                    <Form.Control
                        className="form-control"
                        type="text"
                        placeholder="스터디 그룹 이름을 입력해 주세요."
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="category">
                    <Form.Label className="form-label-name">카테고리</Form.Label>
                    <Form.Select
                        value={selectedCategory}
                        onChange={(e) => setSelectedCategory(e.target.value)}
                    >
                        <option value="">카테고리 선택</option>
                        {categories.map((category) => (
                            <option key={category} value={category}>
                                {category}
                            </option>
                        ))}
                    </Form.Select>
                </Form.Group>

                <Form.Group controlId="contents">
                    <Form.Label className="form-label-name">스터디 그룹 설명</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={4}
                        placeholder="스터디 그룹에 대한 설명을 입력해 주세요."
                        value={contents}
                        onChange={(e) => setContents(e.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="dates" className="mb-3">
                    <Row>
                        <Col xs={6}>
                            <Form.Label className="form-label-name">시작일</Form.Label>
                            <Form.Control
                                type="date"
                                value={startDate}
                                onChange={(e) => setStartDate(e.target.value)}
                                min={(() => {
                                    const today = new Date();
                                    return today.toISOString().split("T")[0];
                                })()}
                            />
                        </Col>
                        <Col xs={6}>
                            <Form.Label className="form-label-name">종료일</Form.Label>
                            <Form.Control
                                type="date"
                                value={endDate}
                                onChange={(e) => setEndDate(e.target.value)}
                                min={(() => {
                                    const today = new Date();
                                    return today.toISOString().split("T")[0];
                                })()}
                            />
                        </Col>
                    </Row>
                </Form.Group>

                <Form.Group controlId="daysOfWeek">
                    <Form.Label className="form-label-name">스터디 요일</Form.Label>
                    <Row>
                        {daysOfWeek.map((day) => (
                            <Col key={day}>
                                <Form.Check
                                    type="checkbox"
                                    label={day}
                                    checked={selectedDays.includes(day)}
                                    onChange={() => handleDayChange(day)}
                                    style={{color: "white"}}
                                />
                            </Col>
                        ))}
                    </Row>
                </Form.Group>

                <Form.Group controlId="studyInfo">
                    <Form.Label className="form-label-name">스터디 정보</Form.Label>
                    <Row>
                        <Col>
                            <Form.Label className="form-label-name">스터디 회차</Form.Label>
                            <InputGroup>
                                <FormControl
                                    type="number"
                                    placeholder="스터디 회차"
                                    value={studyRounds}
                                    onChange={(e) => setStudyRounds(e.target.value)}
                                />
                            </InputGroup>
                        </Col>
                        <Col>
                            <Form.Label className="form-label-name">회차당 진행할 시간</Form.Label>
                            <InputGroup>
                                <FormControl
                                    type="number"
                                    placeholder="회차당 진행할 시간"
                                    value={studyTimePerSession}
                                    onChange={(e) => setStudyTimePerSession(e.target.value)}
                                />
                            </InputGroup>
                        </Col>
                        <Col>
                            <Form.Label className="form-label-name">스터디 최대 인원 수</Form.Label>
                            <InputGroup>
                                <FormControl
                                    type="number"
                                    placeholder="스터디 최대 인원 수"
                                    value={maxCapacity}
                                    onChange={(e) => setMaxCapacity(e.target.value)}
                                />
                            </InputGroup>
                        </Col>
                    </Row>
                </Form.Group>

                <Button variant="primary" type="submit" style={{marginTop: "30px"}}>
                    저장
                </Button>
            </Form>
        </Container>
    );
}

export default StudyGroupForm;
