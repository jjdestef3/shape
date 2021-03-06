/*******************************************************************************
 * Copyright (c) 2013 Pronoia Health LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Contributors:
 * Pronoia Health LLC - initial API and implementation
 *******************************************************************************/
package com.smcpartners.shape.crosscutting.security;



import com.smcpartners.shape.shared.constants.SecurityRoleEnum;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * ServerUserToken.java<br/>
 * Responsibilities:<br/>
 * 1. A session scoped component that holds user information<br/>s
 *
 * @author John DeStefano
 * @version 1.0
 * @since May 26, 2013
 */
@SessionScoped
public class ServerUserToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String userLastName;
    private String userFirstName;
    private String role;
    private boolean loggedIn;
    private boolean forcePasswordReset;

    /**
     * Constructor
     */
    public ServerUserToken() {
        this.role = SecurityRoleEnum.REGISTERED.getName();
    }

    public void clearToken() {
        this.userId = "";
        this.userLastName = "";
        this.userFirstName = "";
        this.role = SecurityRoleEnum.REGISTERED.toString();
        this.loggedIn = false;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Tests to see if this user is an Admin
     *
     * @return
     */
    public boolean getAdmin() {
        if (role != null) {
            if (role.equals(SecurityRoleEnum.ADMIN.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean getForcePasswordReset() {
        return forcePasswordReset;
    }

    public void setForcePasswordReset(boolean forcePasswordReset) {
        this.forcePasswordReset = forcePasswordReset;
    }
}
