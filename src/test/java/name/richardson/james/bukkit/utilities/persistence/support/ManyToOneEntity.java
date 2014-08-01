package name.richardson.james.bukkit.utilities.persistence.support;

import javax.persistence.*;
import java.util.Set;

import com.avaje.ebean.EbeanServer;

import name.richardson.james.bukkit.utilities.persistence.AbstractRecord;

@Entity
public class ManyToOneEntity extends AbstractRecord {

	public OneToManyEntity getEntity() {
		return entity;
	}

	public void setEntity(OneToManyEntity entity) {
		this.entity = entity;
	}

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private OneToManyEntity entity;

	@Override protected EbeanServer getDatabase() {
		return null;
	}

}
