package org.openmrs.module.oauth2.api.db.hibernate;

import org.hibernate.Query;
import org.openmrs.User;
import org.openmrs.api.db.ContextDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by OPSKMC on 8/9/15.
 */
@Repository
public class UserCredentialsDAO extends HibernateOauth2DAO<User> {
    @Autowired
    ContextDAO contextDAO;

    /**
     * You must call this before using any of the data access methods, since it's not actually
     * possible to write them all with compile-time class information due to use of Generics.
     *
     * @param mappedClass
     */
    protected UserCredentialsDAO(Class<User> mappedClass) {
        super(mappedClass);
    }

    /**
     * private default constructor to prevent accidental instantiation using the default constructor
     */
    private UserCredentialsDAO() {
        super(User.class);
    }

    /**
     * Validate the user credentials for spring security context by using the user credentials from OpenMRS users table
     *
     * @param username
     * @param password
     * @return null, if no valid user exists in OpenMRS database
     */
    public User authenticate(String username, String password) {
        User user = null;
        user = contextDAO.authenticate(username, password);
        return user;
    }
}
