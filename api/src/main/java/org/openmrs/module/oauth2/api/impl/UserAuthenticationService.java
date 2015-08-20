package org.openmrs.module.oauth2.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.LoginCredential;
import org.openmrs.api.db.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OPSKMC on 8/18/15.
 */
public class UserAuthenticationService implements AuthenticationProvider {
    private static final Log log = LogFactory.getLog(UserAuthenticationService.class);
    @Autowired
    UserDAO userDAO;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = Context.getUserService().getUserByUsername(username);
        if (user == null) {
            String errorMessage = String.format("No user found with username : %s", username);
            log.error(errorMessage);
            throw new AuthenticationCredentialsNotFoundException(errorMessage);
        }
        LoginCredential credentials = userDAO.getLoginCredential(user);
        if (!credentials.checkPassword(password)) {
            String errorMessage = String.format("The password does not match for the user : %s", username);
            log.error(errorMessage);
            throw new AuthenticationCredentialsNotFoundException(errorMessage);
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
