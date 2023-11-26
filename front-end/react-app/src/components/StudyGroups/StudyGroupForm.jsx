import React, {useEffect, useState} from "react";
import {Form, Button, Container, Col, Row, InputGroup, FormControl, Modal} from "react-bootstrap";
import axiosInstance from "../Config/AxiosConfig";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../Auth/AuthProvider";


function StudyGroupForm() {
    const navigate = useNavigate();
    const {isLoggedIn} = useAuth();

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
    // const daysOfWeek = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];
    const daysOfWeek = {
        MONDAY: "월",
        TUESDAY: "화",
        WEDNESDAY: "수",
        THURSDAY: "목",
        FRIDAY: "금",
        SATURDAY: "토",
        SUNDAY: "일",
    };

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
            timePerSession: studyTimePerSession * 3600
        };

        axiosInstance.post('/v1/studygroups', studyGroupRequest, {}).then(response => {
            console.log(response.data);
            navigate("/");
        }).catch(error => {
            console.log(error)
        });
    };

    if (!isLoggedIn) {
        return (
            <div>
                <h1>경고 모달 창 예제</h1>
                <Button variant="danger" onClick={() => navigate("/")}>
                    경고 표시
                </Button>

                <Modal show={true} onHide={() => navigate("/")}>
                    <Modal.Header closeButton>
                        <Modal.Title></Modal.Title>
                    </Modal.Header>
                    <Modal.Body>로그인 후 이용해 주세요.</Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => navigate("/")}>
                            닫기
                        </Button>
                        <Button variant="warning" onClick={() => navigate("/")}>
                            확인
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }

    return (
        <Container fluid className="project-section d-flex justify-content-center align-items-center">
            <Form className="col-md-6">
                <Form.Group controlId="title">
                    <Row className="form-row">
                        <Col md={4}>
                            <Form.Label className="form-label-name">스터디 그룹 이름</Form.Label>
                        </Col>
                        <Col md={8}>
                            <Form.Control
                                className="form-control"
                                type="text"
                                placeholder="스터디 그룹 이름을 입력해 주세요."
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                            />
                        </Col>
                    </Row>
                </Form.Group>

                <Form.Group controlId="category">
                    <Row>
                        <Col md={4}>
                            <Form.Label className="form-label-name">카테고리</Form.Label>
                        </Col>
                        <Col md={8}>
                            <Form.Select className="form-select"
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
                        </Col>
                    </Row>
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
                        {Object.keys(daysOfWeek).map((day) => (
                            <Col key={day}>
                                <Form.Check
                                    id={day}
                                    type="checkbox"
                                    label={daysOfWeek[day]}
                                    checked={selectedDays.includes(day)}
                                    onChange={() => handleDayChange(day)}
                                    style={{color: "white"}}
                                />
                            </Col>
                        ))}
                    </Row>
                </Form.Group>

                <Form.Group controlId="studyInfo">
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

                <Button variant="primary" onClick={handleSubmit} style={{marginTop: "30px"}}>
                    생성
                </Button>
            </Form>
        </Container>
    );
}

export default StudyGroupForm;
