package org.openmrs.module.oauth2.api.impl;

import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.db.hibernate.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * Created by OPSKMC on 8/20/15.
 */
public class ClientDetailsServiceImpl implements ClientDetailsService {
    public static final String REQUEST_SOURCE = ClientDetailsService.class.getName();
    @Autowired
    private ClientDAO dao;

    /**
     * @param clientId The clientId of the Client that is to be loaded
     * @return {@link org.springframework.security.oauth2.provider.ClientDetails} to be consumed by Spring Security OAuth2 API
     * @throws org.springframework.security.oauth2.provider.ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = dao.loadClientByClientId(clientId);
        client.getAdditionalInformation().put("REQUEST_SOURCE", REQUEST_SOURCE);
        return client;
    }
}
