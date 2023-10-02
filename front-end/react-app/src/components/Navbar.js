import React, { useState } from "react";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Container from "react-bootstrap/Container";
import { Link } from "react-router-dom";
import { ImBlog } from "react-icons/im";
import {
  AiOutlineHome,
} from "react-icons/ai";
import KakaoLoginButton from "./Auth/KakaoLoginButton";
import {useAuth} from "./Auth/AuthProvider";
import Button from "react-bootstrap/Button";

function NavBar() {
  const [expand, updateExpanded] = useState(false);
  const [navColour, updateNavbar] = useState(false);
  const { isLoggedIn, login, logout } = useAuth();

  function scrollHandler() {
    if (window.scrollY >= 20) {
      updateNavbar(true);
    } else {
      updateNavbar(false);
    }
  }

  window.addEventListener("scroll", scrollHandler);

  return (
    <Navbar
      expanded={expand}
      fixed="top"
      expand="md"
      className={navColour ? "sticky" : "navbar"}
    >
      <Container>
        <Navbar.Brand href="/" className="d-flex">
          {/* <img src={logo} className="img-fluid logo" alt="brand" /> */}
        </Navbar.Brand>
        <Navbar.Toggle
          aria-controls="responsive-navbar-nav"
          onClick={() => {
            updateExpanded(expand ? false : "expanded");
          }}
        >
          <span></span>
          <span></span>
          <span></span>
        </Navbar.Toggle>
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="ms-auto" defaultActiveKey="#home">
            <Nav.Item>
              <Nav.Link as={Link} to="/" onClick={() => updateExpanded(false)}>
                <AiOutlineHome style={{ margin: "10px" }} /> 홈
              </Nav.Link>
            </Nav.Item>

            <Nav.Item>
              <Nav.Link
                as={Link}
                to="#"
                onClick={() => updateExpanded(false)}
              >
                <ImBlog
                  style={{ margin: "10px" }}
                />{" "}
                스터디
              </Nav.Link>
            </Nav.Item>

            {isLoggedIn ? (
                <Nav.Item className="fork-btn">
                  <Button onClick={() => logout()}>
                      로그아웃
                  </Button>
                </Nav.Item>

            ) : (
                <KakaoLoginButton/>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}


export default NavBar;
