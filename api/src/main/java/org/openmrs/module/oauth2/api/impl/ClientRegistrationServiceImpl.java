package org.openmrs.module.oauth2.api.impl;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.db.hibernate.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by OPSKMC on 5/5/15.
 */

public class ClientRegistrationServiceImpl extends BaseOpenmrsService implements ClientRegistrationService {


    @Autowired
    protected ClientDAO dao;

    public void setDao(ClientDAO dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public Client saveOrUpdateClient(Client client) {
        return dao.saveOrUpdate(client);
    }

    @Transactional(readOnly = false)
    @Override
    public Client updateClient(Client client) {
        return dao.update(client);
    }


    @Override
    @Transactional(readOnly = true)
    public Client getClient(Integer id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClientsForClientDeveloper(User clientDeveloper) {
        return dao.getAllClientsForClientDeveloper(clientDeveloper);
    }

    @Override
    @Transactional
    public Client unregisterClient(Client client) {
        dao.delete(client);
        return client;
    }

    @Transactional
    @Override
    public Client registerNewClient(Client client) {
        client.setCreator(Context.getAuthenticatedUser());
        generateClientCredentials(client);
        return saveOrUpdateClient(client);
    }

    @Override
    public Client.ClientType[] getAllClientTypes() {
        return Client.ClientType.values();
    }

    @Override
    public Client merge(Client client) {
        return dao.merge(client);
    }

    @Override
    public List<String> encodeCredentials(Client client) {
        String clientIdentifier = client.getClientIdentifier();
        String clientSecret = client.getClientSecret();
        if (clientIdentifier == null || clientSecret == null || clientIdentifier.length() == 0 || clientSecret.length() == 0)
            throw new IllegalStateException("Invalid credentials for OAuth2 Client : " + client.getName() + ". Kindly request generation of new credentials");
        List<String> credentials = new ArrayList<String>();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
        credentials.add(encoder.encode(clientIdentifier));
        credentials.add(encoder.encode(clientSecret));
        return credentials;
    }

    @Override
    public void generateAndPersistClientCredentials(Client client) {
        generateClientCredentials(client);
        merge(client);
        updateClient(client);
    }

    /**
     * Should be called before persisting new client
     *
     * @param client
     */
    private void generateClientCredentials(Client client) {
        SecureRandom random = new SecureRandom();
        client.setClientSecret(new BigInteger(130, random).toString(32));
        client.setClientIdentifier(new Date().toString());
    }

    @Override
    public boolean verifyClientCredentials(Client client, String encodedClientIdentifier, String encodedClientSecret) {
        BCryptPasswordEncoder matcher = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
        if (matcher.matches(client.getClientIdentifier(), encodedClientIdentifier) && matcher.matches(client.getClientSecret(), encodedClientSecret))
            return true;
        return false;
    }


}
