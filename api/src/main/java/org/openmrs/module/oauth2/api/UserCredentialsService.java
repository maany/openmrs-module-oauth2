package org.openmrs.module.oauth2.api;

import org.openmrs.User;
import org.openmrs.api.OpenmrsService;

/**
 * This service fetches {@link org.openmrs.User} credentials
 * These credentials are then used by the {@link org.springframework.security.core.userdetails.UserDetailsService} implementation defined in the OMOD Layer
 * Created by OPSKMC on 8/9/15.
 */
@Deprecated
public interface UserCredentialsService extends OpenmrsService {
    /**
     * Search the OpenMRS database for an existing User with the given credentials
     *
     * @param username
     * @param password
     * @return User, if one exists in OpenMRS database with the given credentials
     */
    public User authenticate(String username, String password);
}
