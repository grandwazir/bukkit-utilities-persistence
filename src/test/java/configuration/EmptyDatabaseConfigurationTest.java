package configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.avaje.ebean.config.ServerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EmptyDatabaseConfigurationTest {

	private static final String URL = "jdbc:sqlite:{DIR}{NAME}.db";
	private static final String PASSWORD = "";
	private static final String USERNAME = "travis";
	private static final String DRIVER = "org.sqlite.JDBC";
	private static final int ISOLATION_LEVEL = 8;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private SimpleDatabaseConfiguration configuration;


	@Before
	public void setUp()
	throws Exception {
		ServerConfig serverConfig = getDefaultServerConfig();
		InputStream stream = getClass().getClassLoader().getResourceAsStream("database-empty.yml");
		configuration = new SimpleDatabaseConfiguration(folder.newFile("database.yml"), stream, "Test", serverConfig);
	}

	@Test
	public void whenGettingUsernameMatchValueInServerConfig() {
		Assert.assertEquals("Value should be the same as provided by the ServerConfig", USERNAME, configuration.getDataSourceConfig().getUsername());
	}

	@Test
	public void whenGettingDriverMatchValueInServerConfig() {
		Assert.assertEquals("Value should be the same as provided by the ServerConfig", DRIVER, configuration.getDataSourceConfig().getDriver());
	}

	@Test
	public void whenGettingIsolationLevelMatchValueInServerConfig() {
		Assert.assertEquals("Value should be the same as provided by the ServerConfig", ISOLATION_LEVEL, configuration.getDataSourceConfig().getIsolationLevel());
	}

	@Test
	public void whenGettingPasswordMatchValueInServerConfig() {
		Assert.assertEquals("Value should be the same as provided by the ServerConfig", PASSWORD, configuration.getDataSourceConfig().getPassword());
	}

	@Test
	public void whenGettingUrlReplacePlaceholderValuesFromServerConfig() {
		File[] files = folder.getRoot().listFiles();
		File database = files[0];
		Assert.assertTrue("Placeholder values in URL String have not been replaced", !database.getAbsolutePath().contains("{DIR}|{FILE}"));
	}

	@Test
	public void whenGettingVersionShouldAlwaysBe1() {
		Assert.assertEquals("Value should be the same as the default", 1, configuration.getDatabaseVersion());
	}

	private static ServerConfig getDefaultServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.getDataSourceConfig().setUrl(URL);
		serverConfig.getDataSourceConfig().setPassword(PASSWORD);
		serverConfig.getDataSourceConfig().setUsername(USERNAME);
		serverConfig.getDataSourceConfig().setDriver(DRIVER);
		serverConfig.getDataSourceConfig().setIsolationLevel(ISOLATION_LEVEL);
		return serverConfig;
	}

}
