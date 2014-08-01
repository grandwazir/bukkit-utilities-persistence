package name.richardson.james.bukkit.utilities.persistence;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.avaje.ebean.validation.NotNull;

@MappedSuperclass
public abstract class AbstractRecord implements Record {

	@Id
	@NotNull
	private UUID id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createdAt;

	@Version
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp modifiedAt;

	@Override public Timestamp getCreatedAt() {
		return createdAt;
	}

	@Override public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	@Override public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Override public UUID getId() {
		return id;
	}

	@Override public void setId(UUID id) {
		this.id = id;
	}

	@Override public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder("AbstractRecord{");
		sb.append("createdAt=").append(createdAt);
		sb.append(", id=").append(id);
		sb.append(", modifiedAt=").append(modifiedAt);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public final void save() {
		getDatabase().save(this);
	}

	protected abstract EbeanServer getDatabase();

}
