import React from "react";
import Card from "react-bootstrap/Card";

function StudyGroupCards(props) {
  return (
    <Card className="project-card-view">
      <Card.Body>
        <Card.Title style={{ fontSize: "25px", fontWeight: "bold"}}>
          <strong className="purple">{props.title}</strong>
        </Card.Title>
        <Card.Text style={{ textAlign: "justify", height: "100px", overflow: "auto", textOverflow: "ellipsis"}}>
        {props.contents}
        </Card.Text>
        <Card.Footer>
          시작일: {props.startDate}
        </Card.Footer>
        <Card.Footer>
          종료일: {props.endDate}
        </Card.Footer>
      </Card.Body>
    </Card>
  );
}
export default StudyGroupCards;
