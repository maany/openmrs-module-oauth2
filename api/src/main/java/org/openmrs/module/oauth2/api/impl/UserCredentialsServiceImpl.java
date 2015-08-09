package org.openmrs.module.oauth2.api.impl;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.ContextDAO;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.oauth2.api.UserCredentialsService;
import org.openmrs.module.oauth2.api.db.hibernate.UserCredentialsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implementation of {@link org.springframework.security.core.userdetails.UserDetailsService}
 * Read Username and Password from OpenMRS database and map it to {@link org.springframework.security.core.userdetails.UserDetails}
 * Created by OPSKMC on 8/9/15.
 */

public class UserCredentialsServiceImpl extends BaseOpenmrsService implements UserCredentialsService {
    @Autowired
    UserCredentialsDAO userCredentialsDAO;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromUserService = Context.getUserService().getUserByUsername(username);

        return null;
    }

    /**
     * @param username
     * @param password
     * @return
     * @see {@link org.openmrs.module.oauth2.api.UserCredentialsService#authenticate(String, String)}
     */
    @Override
    public User authenticate(String username, String password) {
        User user = userCredentialsDAO.authenticate(username, password);

        return user;
    }
}
