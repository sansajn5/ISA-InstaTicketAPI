package com.isa.instaticketapi.web.rest.vm.AccountResource;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to return as body in JWT Authentication.
 */
public class JWTTokenResponse {

    private String idToken;

    public JWTTokenResponse(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("id_token")
    String getIdToken() {
        return idToken;
    }

    void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}

