package name.richardson.james.bukkit.utilities.persistence.support;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

import com.avaje.ebean.EbeanServer;

import name.richardson.james.bukkit.utilities.persistence.AbstractRecord;

@Entity
public class OneToManyEntity extends AbstractRecord {

	public Set<ManyToOneEntity> getEntities() {
		return entities;
	}

	public void setEntities(final Set<ManyToOneEntity> entities) {
		this.entities = entities;
	}

	@OneToMany(mappedBy = "entity")
	private Set<ManyToOneEntity> entities;

	@Override protected EbeanServer getDatabase() {
		return null;
	}

}
