package org.openmrs.module.oauth2.api.impl;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.oauth2.Client;
import org.openmrs.module.oauth2.api.ClientRegistrationService;
import org.openmrs.module.oauth2.api.db.hibernate.ClientDAO;
import org.openmrs.module.oauth2.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
        setClientForParametrizedCollections(client);
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

    /**
     *  The elements in the Collection<? extends Parametrized> in Client such as {@link Client#redirectUriCollection} have a @ManyToOne mapping for {@link Client}.
     *  In order to make sure proper foreign key mapping takes place for this relation, this method calls {@link org.openmrs.module.oauth2.api.model.Parametrized#setClient(Client)}for all objects of these collections
     *  This was necessary as despite using mappedBy in the @OneToMany side of the relation, client_id was not saved in the database tables of these collections.
     *  @param client Client whose inverse mappings are to be established
     */

    private void setClientForParametrizedCollections(Client client){
        if(client.getResourceCollection()!=null) {
            for (Resource resource : client.getResourceCollection())
                resource.setClient(client);
        }
        if(client.getScopeCollection()!=null) {
            for (Scope scope : client.getScopeCollection())
                scope.setClient(client);
        }
        if(client.getAuthorities()!=null) {
            for (GrantedAuthority authority : client.getAuthorities()) {
                try {
                    ((CustomGrantedAuthority) authority).setClient(client);
                } catch (ClassCastException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(client.getRedirectUriCollection()!=null) {
            for (RedirectURI redirectURI : client.getRedirectUriCollection())
                redirectURI.setClient(client);
        }
        if(client.getGrantTypeCollection()!=null) {
            for (AuthorizedGrantType grantType : client.getGrantTypeCollection())
                grantType.setClient(client);
        }
    }

    @Override
    public boolean verifyClientCredentials(Client client, String encodedClientIdentifier, String encodedClientSecret) {
        BCryptPasswordEncoder matcher = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
        if (matcher.matches(client.getClientIdentifier(), encodedClientIdentifier) && matcher.matches(client.getClientSecret(), encodedClientSecret))
            return true;
        return false;
    }


}
