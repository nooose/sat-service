import React from 'react';
import pkce from 'oauth-pkce';
import login from "../../assets/kakao_login_medium_narrow.png";
import Nav from "react-bootstrap/Nav";

const KakaoLoginButton = () => {
    const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
    const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;

    const handleLogin = () => {
        pkce(32, (error, { verifier, challenge }) => {
            if (!error) {
                sessionStorage.setItem("code_verifier", verifier);
                sessionStorage.setItem("code_challenge", challenge);
                window.location.href = kakaoOAuthURL(challenge);
            }
        });
    };

    const kakaoOAuthURL = (codeChallenge) => {
        return `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code&code_challenge=${codeChallenge}&code_challenge_method=S256`
    }

    return (
        <Nav.Item className="fork-btn" onClick={handleLogin} style={{ cursor: 'pointer' }}>
            <img src={login} className="img" alt="brand"/>
        </Nav.Item>
    );
}

export default KakaoLoginButton;
