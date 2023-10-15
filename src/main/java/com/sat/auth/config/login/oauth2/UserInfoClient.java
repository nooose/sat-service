package com.sat.auth.config.login.oauth2;

import com.sat.auth.config.dto.OAuth2Response;

public interface UserInfoClient {

    OAuth2Response response(String accessToken);

    boolean supports(String providerName);
}
