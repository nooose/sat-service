package com.sat.auth.application;

import com.sat.auth.config.dto.OAuth2Response;

public interface UserInfoClient {

    OAuth2Response response(String accessToken);

    boolean isSupported(String providerName);
}
