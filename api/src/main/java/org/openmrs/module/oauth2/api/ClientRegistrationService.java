package org.openmrs.module.oauth2.api;

import org.openmrs.User;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.oauth2.Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import java.util.List;

/**
 *
 */

public interface ClientRegistrationService extends OpenmrsService {

    public static final int BCRYPT_STRENGTH = 4;
    /**
     * update details of the client if exists, else create new client
     *
     * @param client
     * @should
     */
    public Client saveOrUpdateClient(Client client);

    /**
     * @param client
     */
    public Client updateClient(Client client);

    /**
     * @param id
     * @return
     */
    public Client getClient(Integer id);

    /**
     * @param clientDeveloper
     * @return
     */
    public List<Client> getAllClientsForClientDeveloper(User clientDeveloper);

    /**
     * @param client
     * @return
     * @should unregister a client and clear footprint from database
     */
    public Client unregisterClient(Client client);

    /**
     * @param client
     * @should register a new client into the database for currently logged in User {@link org.openmrs.api.context.Context#getAuthenticatedUser()}
     */
    //TODO Feature Discussion : is this required at all? or should saveOrUpdate suffice? Ask Harsha
    public Client registerNewClient(Client client);

    /**
     * This method returns the list of the supported Oauth2 Client Types.
     * Default values are : {WEB_APPLICATION, USER_AGENT_BASED_APPLICATION, NATIVE_APPLICATION}
     *
     * @return
     */
    public Client.ClientType[] getAllClientTypes();

    /**
     * @param client
     * @return
     */
    public Client merge(Client client);

    /**
     * Encodes {@link org.openmrs.module.oauth2.Client#name} to  {@link org.openmrs.module.oauth2.Client#clientIdentifier} and {@link Client#}{@link org.openmrs.module.oauth2.Client#clientSecret} with <a href="https://en.wikipedia.org/wiki/Bcrypt">BCrypt Hashing function</a>
     *
     * @param client
     * @return A List with 1st element as encoded clientIdentifier and second element as encoded clientSecret
     */
    public List<String> encodeCredentials(Client client);

    /**
     * Generate {@link org.openmrs.module.oauth2.Client#clientIdentifier} , {@link org.openmrs.module.oauth2.Client#clientSecret} and persist values in database.
     * Generation strategy for clientId is current
     *
     * @param client
     */
    public void generateAndPersistClientCredentials(Client client);

    /**
     * @param client
     * @param encodedClientIdentifier
     * @param encodedClientSecret
     * @return
     */

    public boolean verifyClientCredentials(Client client, String encodedClientIdentifier, String encodedClientSecret);

}
