import React, {useEffect, useState} from "react";
import {Container, Row, Col, Modal} from "react-bootstrap";
import ProjectCard from "./StudyGroupCards";
import Particle from "../Particle";
import Button from "react-bootstrap/Button";
import {AiOutlineAppstoreAdd} from "react-icons/ai";

import axiosInstance from "../Config/AxiosConfig";
import {useAuth} from "../Auth/AuthProvider";
import Nav from "react-bootstrap/Nav";
import KakaoLoginButton from "../Auth/KakaoLoginButton";

function StudyGroups() {
    const [studyGroups, setStudyGroups] = useState([]);
    const {isLoggedIn} = useAuth();


    useEffect(() => {
        axiosInstance.get("/v1/studygroups")
            .then(response => {
                console.log(response.data);
                setStudyGroups(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    return (
        <Container fluid className="project-section">
            <Particle/>
            <Container>
                <h1 className="project-heading">
                    등록된 <strong className="purple">스터디 그룹</strong>
                </h1>
                <p style={{color: "white"}}>
                    스터디 그룹에 들어가거나 만들 수 있어요.
                </p>
                <Row style={{justifyContent: "center", paddingBottom: "10px"}}>
                    {studyGroups.map((studygroup, index) => (
                        <Col key={index} md={3} className="project-card">
                            <ProjectCard
                                isBlog={false}
                                title={studygroup.title}
                                contents={studygroup.contents}
                                startDate={studygroup.startDate}
                                endDate={studygroup.endDate}
                            />
                        </Col>
                    ))}
                </Row>

                {isLoggedIn ? (
                    <Row style={{justifyContent: "center", position: "relative"}}>
                        <Button
                            variant="primary"
                            style={{maxWidth: "250px"}}
                            href="/studygroups/form"
                        >
                            <AiOutlineAppstoreAdd/>
                            &nbsp;스터디 그룹 만들기
                        </Button>
                    </Row>
                ) : (
                    <div/>
                )}

            </Container>
        </Container>
    );
}

export default StudyGroups;
