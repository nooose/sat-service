import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import StudyGroups from "../StudyGroups/StudyGroups";

function Home2() {
  return (
    <Container fluid className="home-about-section" id="about">
      <Container>
          <StudyGroups>
          </StudyGroups>
      </Container>
    </Container>
  );
}
export default Home2;
