package org.openmrs.module.oauth2.api;

import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.db.Oauth2DAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by OPSKMC on 5/5/15.
 */
@Transactional
public interface ClientRegistrationService extends Oauth2DAO<Client> {

}
