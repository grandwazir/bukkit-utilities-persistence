/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 SimpleDatabaseConfiguration.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;

import name.richardson.james.bukkit.utilities.configuration.AbstractConfiguration;
import name.richardson.james.bukkit.utilities.updater.PluginUpdater;

public class SimpleDatabaseConfiguration extends AbstractConfiguration implements DatabaseConfiguration {

	private enum Keys {
		USER_NAME("username"),
		PASSWORD("password"),
		DRIVER("driver"),
		ISOLATION("isolation"),
		URL("url"),
		VERSION("version");
		private final String path;

		Keys(final String path) {
			this.path = path;
		}

		private String getPath() {
			return path;
		}

	}

	private final DataSourceConfig dataSourceConfig;
	private final File folder;
	private final String pluginName;
	private final ServerConfig serverConfig;

	public SimpleDatabaseConfiguration(final File file, final InputStream defaults, final String pluginName, final ServerConfig serverConfig)
	throws IOException {
		super(file, defaults);
		this.folder = file.getParentFile();
		this.serverConfig = serverConfig;
		this.dataSourceConfig = serverConfig.getDataSourceConfig();
		this.pluginName = pluginName;
		setDefaults();
		setUserName();
		setPassword();
		setDriver();
		setIsolation();
		setUrl();
	}

	public DataSourceConfig getDataSourceConfig() {
		return this.dataSourceConfig;
	}

	@Override public int getDatabaseVersion() {
		YamlConfiguration configuration = getConfiguration();
		return configuration.getInt(Keys.VERSION.getPath(), 1);
	}

	public ServerConfig getServerConfig() {
		return this.serverConfig;
	}

	private String replaceDatabaseString(String input) {
		input = input.replaceAll("\\{DIR\\}", this.folder.getAbsolutePath() + File.separator);
		input = input.replaceAll("\\{NAME\\}", this.pluginName.replaceAll("[^\\w_-]", ""));
		return input;
	}

	private void setDefaults() {
		this.serverConfig.setDefaultServer(false);
		this.serverConfig.setRegister(false);
		this.serverConfig.setName(pluginName);
	}

	private void setDriver() {
		String driver = this.getConfiguration().getString(Keys.DRIVER.getPath());
		if (driver != null) {
			this.dataSourceConfig.setDriver(driver);
		}
	}

	private void setIsolation() {
		try {
			String isolation = this.getConfiguration().getString(Keys.ISOLATION.getPath());
			if (isolation != null) {
				this.dataSourceConfig.setIsolationLevel(TransactionIsolation.getLevel(isolation));
			}
		} catch (RuntimeException e) {
			// logger.log(Level.WARNING, "transaction-level-invalid");
		}
	}

	private void setPassword() {
		String password = this.getConfiguration().getString(Keys.PASSWORD.getPath());
		if (password != null) {
			this.dataSourceConfig.setPassword(password);
		}
	}

	private void setUrl() {
		String url = this.getConfiguration().getString(Keys.URL.getPath());
		if (url != null) {
			this.dataSourceConfig.setUrl(replaceDatabaseString(url));
		} else {
			this.dataSourceConfig.setUrl(replaceDatabaseString(dataSourceConfig.getUrl()));
		}
	}

	private void setUserName() {
		String username = this.getConfiguration().getString(Keys.USER_NAME.getPath());
		if (username != null) {
			this.dataSourceConfig.setUsername(username);
		}
	}

}
