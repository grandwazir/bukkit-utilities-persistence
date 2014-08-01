/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 AbstractDatabaseLoader.java is part of bukkit-utilities.

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

package name.richardson.james.bukkit.utilities.persistence;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import name.richardson.james.bukkit.utilities.persistence.configuration.DatabaseConfiguration;

import org.apache.commons.lang.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public abstract class AbstractDatabaseLoader implements DatabaseLoader {

	private static final Messages MESSAGES = MessagesFactory.getMessages();
	private static final Logger LOGGER = LogManager.getLogger();

	private final ClassLoader classLoader;
	private final ServerConfig serverConfig;
	private EbeanServer ebeanserver;
	private DdlGenerator generator;

	public AbstractDatabaseLoader(DatabaseConfiguration configuration) {
		Validate.notEmpty(configuration.getServerConfig().getClasses(), "Database classes must be provided!");
		Validate.notNull(configuration, "A name.richardson.james.bukkit.utilities.persistence.configuration is required!");
		this.serverConfig = configuration.getServerConfig();
		this.classLoader = configuration.getClass().getClassLoader();
	}

	@Override
	public final EbeanServer getEbeanServer() {
		return ebeanserver;
	}

	synchronized public final void initalise() {
		if (this.ebeanserver != null) return;
		LOGGER.debug(MESSAGES.databaseLoading());
		this.load();
		if (!this.isSchemaValid()) {
			SpiEbeanServer server = (SpiEbeanServer) this.ebeanserver;
			generator = server.getDdlGenerator();
			this.drop();
			this.create();
		}
	}

	protected abstract void afterDatabaseCreate();

	protected abstract void beforeDatabaseCreate();

	protected abstract void beforeDatabaseDrop();

	protected String getDeleteDLLScript() {
		SpiEbeanServer server = (SpiEbeanServer) getEbeanServer();
		DdlGenerator generator = server.getDdlGenerator();
		return generator.generateDropDdl();
	}

	protected String getGenerateDDLScript() {
		SpiEbeanServer server = (SpiEbeanServer) getEbeanServer();
		DdlGenerator generator = server.getDdlGenerator();
		return generator.generateCreateDdl();
	}

	@Override
	public final void create() {
		LOGGER.info(MESSAGES.databaseCreating());
		this.beforeDatabaseCreate();
		this.load();
		String script = getGenerateDDLScript();
		generator.runScript(false, script);
		this.afterDatabaseCreate();
	}

	@Override
	public final void drop() {
		LOGGER.info(MESSAGES.databaseDropping());
		this.beforeDatabaseDrop();
		String script = this.getDeleteDLLScript();
		generator.runScript(true, script);
	}

	@Override
	public final void load() {
		ClassLoader currentClassLoader = null;
		try {
			currentClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(this.classLoader);
			this.ebeanserver = EbeanServerFactory.create(this.serverConfig);
		} finally {
			if (currentClassLoader != null) {
				Thread.currentThread().setContextClassLoader(currentClassLoader);
			}
		}
	}

	@Override
	public final boolean isSchemaValid() {
		List<Class<?>> classes = this.serverConfig.getClasses();
		boolean valid = true;
		for (Class<?> ebean : classes) {
			try {
				this.ebeanserver.find(ebean).findRowCount();
			} catch (final Exception e) {
				valid = false;
				break;
			}
		}
		if (valid) {
			LOGGER.debug(MESSAGES.databaseValid());
		} else {
			LOGGER.warn(MESSAGES.databaseInvalid());
		}
		return valid;
	}

}
