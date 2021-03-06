package com.smcpartners.shape.crosscutting.security;

import javax.enterprise.context.RequestScoped;

/**
 * Responsible:</br>
 * 1. Holds information from the callers JWT. This can be used in other classes in an
 * http request chain. This only works if the class target method is annotated with
 * SecureRequireActiveLogActivityInterceptor or JWTSecurityAuthorizerWithUserIdParam</br>
 * <p>
 * Created by johndestefano on 3/15/16.
 * </p>
 * <p>
 * Changes:</br>
 * 1. </br>
 * </p>
 */
@RequestScoped
public class RequestScopedUserId {

    private String requestUserId;
    private String securityRole;
    private int orgId;


    public RequestScopedUserId() {
    }

    public RequestScopedUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    public String getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(String securityRole) {
        this.securityRole = securityRole;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}
