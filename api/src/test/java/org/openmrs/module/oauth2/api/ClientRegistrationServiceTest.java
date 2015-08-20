package org.openmrs.module.oauth2.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.oauth2.Client;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.Date;
import java.util.List;

/**
 * Created by OPSKMC on 5/5/15.
 */
public class ClientRegistrationServiceTest extends BaseModuleContextSensitiveTest {
    protected static final String CLIENT_INITIAL_DATA_XML = "ClientRegistrationServiceTest-initialData.xml";//"org/openmrs/api/include/LocationServiceTest-initialData.xml";
    protected static final String INITIAL_IN_MEMORY_TESTDATASET_XML = "org/openmrs/include/initialInMemoryTestDataSet.xml";
    private static final String SAMPLE_CLIENT_REDIRECTION_URI = "www.demoapp.com";
    private int demoClientId;

    public ClientRegistrationService getService() {
        return Context.getService(ClientRegistrationService.class);
    }

    @Before
    public void runBeforeEachTest() throws Exception {
        executeDataSet(CLIENT_INITIAL_DATA_XML);
        //demoClientId=initDatabase();
        //   executeDataSet(INITIAL_IN_MEMORY_TESTDATASET_XML);
    }

    @Test
    public void saveOrUpdate_shouldSaveNewClientsUpdateExistingClients() {
        Client client = createSampleClient();
        getService().saveOrUpdateClient(client);
        client = getService().getClient(client.getId());
        Assert.assertNotNull(client);
    }

    @Test
    public void getClient_shouldGetClientById() {
        Client client = getService().getClient(1);
        Assert.assertNotNull(client);
    }

    /**
     * test data has been set in the xml to return nto return Client with clientId = 1
     */
    //TODO add more test data in ClientRegistrationServiceTest-initialData.xml
    @Test
    public void getAllClientsForClientDeveloper_shouldListAllClientsRegisteredByClientDeveloper() {
        User clientDeveloper = Context.getUserService().getUser(4);
        List<Client> clients = getService().getAllClientsForClientDeveloper(clientDeveloper);
        Assert.assertNotNull(clients);
    }

    @Test
    /**
     * @see
     * @verifies
     */
    //TODO add more comparisons besides TEST_NAME. Update operation should not modify id and clientDeveloper? Ask Harsha
    public void updateClient_shouldUpdateExistingClient() {
        String TEST_NAME = "demo name";
        Client client = getService().getClient(1);
        client.setName(TEST_NAME);
        getService().updateClient(client);
        client = getService().getClient(1);
        Assert.assertEquals(TEST_NAME, client.getName());
    }

    /**
     *
     */
    @Test
    public void unregisterClient_shouldUnregisterClient() {
        Client client = getService().getClient(1);
        Assert.assertNotNull(client);
        getService().unregisterClient(client);
        client = getService().getClient(1);
        Assert.assertNull(client);
    }

    /**
     *
     */
    @Test
    public void registerNewClient_shouldCreateANewClientForCurrentLoggedInUser() {

        Client client = createSampleClient();
        client = getService().registerNewClient(client);
//        List<Client> clients = getService().getAllClientsForClientDeveloper(Context.getAuthenticatedUser());
//        client = clients.get(clients.size() - 1);
//        for (String redirectUri : client.getRegisteredRedirectUri()) {
//            Assert.assertEquals("", redirectUri);
//            break;

        client = getService().getClient(client.getId());
        Assert.assertNotNull(client);
    }

    //TODO : write a better test

    /**
     *
     */
    @Test
    public void getAllClientTypes_shouldReturnAllSupportedClientTypes() {
        Assert.assertNotEquals("At least 1 clientType should be supported", getService().getAllClientTypes().length, 0);
    }

    @Test
    public void merge_shouldMergeWithExistingClient() {
        final String DEMO_NAME = "Merge New Client";
        Client client = getService().getClient(1);
        client.setName(DEMO_NAME);
        client = getService().merge(client);
//        getService().updateClient(client);
        Client newClient = getService().getClient(1);
        Assert.assertEquals(DEMO_NAME, newClient.getName());

    }

    @Test
    public void encodeDecideClientCredentialsTest_shouldEncodeClientCredentialsAndBeAbleToDecodeThem() {
        Client client = getService().getClient(1);
        getService().generateAndPersistClientCredentials(client);
        List<String> credentials = getService().encodeCredentials(client);
        Assert.assertNotNull(credentials);
        Assert.assertEquals(2, credentials.size());
        boolean decodeResult = getService().verifyClientCredentials(client, credentials.get(0), credentials.get(1));
        Assert.assertTrue(decodeResult);
    }

    private Client createSampleClient() {
        Client client = new Client("my-trusted-client-with-secret", "somesecret", "openmrs", "read,write", "authorization_code,refresh_token,implicit,client_credentials,password", "ROLE_CLIENT", "http://anywhere?key=value");
        client.setVoided(false);
        client.setDateCreated(new Date());
        client.setName("Demo Application");
        client.setClientType(Client.ClientType.WEB_APPLICATION);
        return client;
    }

    /**
     * Currently DBUnit is not configure to create tables that do not have primary keys. As a consequence, we
     * cannot specify tables representing the fields of {@link org.openmrs.module.oauth2.Client} annotated with
     *
     * @return
     * @ElementCollection in the xml file containing test data.
     * So, as an alternative, call this method in @Before to create test data until the above mentioned issue is resolved
     */
    private int initDatabase() {
        Client client = new Client("my-trusted-client-with-secret", "somesecret", "openmrs", "read,write", "authorization_code,refresh_token,implicit,client_credentials,password", "ROLE_CLIENT", "http://anywhere?key=value");
        client.setVoided(false);
        client.setDateCreated(new Date());
        client.setName("Demo Application");
        client.setClientType(Client.ClientType.WEB_APPLICATION);
        client.getRegisteredRedirectUri().add(SAMPLE_CLIENT_REDIRECTION_URI);
        getService().saveOrUpdateClient(client);
        getService().generateAndPersistClientCredentials(client);
        return client.getId();
    }

}
