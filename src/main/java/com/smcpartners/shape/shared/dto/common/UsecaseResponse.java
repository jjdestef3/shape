package com.smcpartners.shape.shared.dto.common;

import com.smcpartners.shape.shared.constants.UseCaseConstants;

/**
 * Responsible:<br/>
 * 1. Returned from a use case
 * <p>
 * Created by johndestefano on 10/7/15.
 * <p>
 * Changes:<b/>
 */
public class UsecaseResponse extends RequestResponse {

    /**
     * Any exception thrown in the use case
     */
    private Exception error;

    /**
     * Custom error message from a use case
     */
    private String errMsg;
    private String responseCode;

    /**
     * Return object from a use case invocation
     */
    private Object response;

    public UsecaseResponse() {
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isErr() {
        if (error != null || errMsg != null ||
                (this.getResponseCode() != null && UseCaseConstants.valueOf(this.getResponseCode()) != UseCaseConstants.OK_RESPONSE)) {
            return true;
        }
        return false;
    }

    public boolean isOk() {
        if (this.getResponseCode() != null &&
                this.getResponseCode() != UseCaseConstants.OK_RESPONSE.getVal()) {
            return false;
        } else {
            return true;
        }
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
