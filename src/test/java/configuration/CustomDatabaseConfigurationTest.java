package configuration;

import java.io.File;
import java.io.InputStream;

import com.avaje.ebean.config.ServerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CustomDatabaseConfigurationTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private SimpleDatabaseConfiguration configuration;


	@Before
	public void setUp()
	throws Exception {
		ServerConfig serverConfig = getDefaultServerConfig();
		InputStream stream = getClass().getClassLoader().getResourceAsStream("database-custom.yml");
		configuration = new SimpleDatabaseConfiguration(folder.newFile("database.yml"), stream, "Test", serverConfig);
	}

	@Test
	public void whenGettingUsernameMatchValueInConfiguration() {
		Assert.assertEquals("Value should be the same as provided by the configuration", "frank", configuration.getDataSourceConfig().getUsername());
	}

	@Test
	public void whenGettingDriverMatchValueInConfiguration() {
		Assert.assertEquals("Value should be the same as provided by the configuration", "org.sqf.JDBC", configuration.getDataSourceConfig().getDriver());
	}

	@Test
	public void whenGettingIsolationLevelMatchValueInConfiguration() {
		Assert.assertEquals("Value should be the same as provided by the configuration", 2, configuration.getDataSourceConfig().getIsolationLevel());
	}

	@Test
	public void whenGettingPasswordMatchValueInConfiguration() {
		Assert.assertEquals("Value should be the same as provided by the configuration", "ted", configuration.getDataSourceConfig().getPassword());
	}

	@Test
	public void whenGettingUrlMatchValueFromConfiguration() {
		Assert.assertEquals("Value should be the same as provided by the configuration", "jdbc:mysql:/3019", configuration.getDataSourceConfig().getUrl());
	}

	@Test
	public void whenGettingVersionShouldMatchValueFromConfiguration() {
		Assert.assertEquals("Value should be the same as provided by the configuration", 10, configuration.getDatabaseVersion());
	}

	private static ServerConfig getDefaultServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.getDataSourceConfig().setUrl("jdbc:sqlite:{DIR}{NAME}.db");
		serverConfig.getDataSourceConfig().setPassword("");
		serverConfig.getDataSourceConfig().setUsername("travis");
		serverConfig.getDataSourceConfig().setDriver("org.sqlite.JDBC");
		serverConfig.getDataSourceConfig().setIsolationLevel(8);
		return serverConfig;
	}


}
