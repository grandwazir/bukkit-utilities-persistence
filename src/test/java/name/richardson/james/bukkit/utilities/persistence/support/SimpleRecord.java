package name.richardson.james.bukkit.utilities.persistence.support;

import javax.persistence.Entity;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

import name.richardson.james.bukkit.utilities.persistence.AbstractRecord;

@Entity
public class SimpleRecord extends AbstractRecord {

	@Override
	public void save() {
		getDatabase().save(this);
	}

	@Override protected EbeanServer getDatabase() {
		return Ebean.getServer("Test");
	}



}
