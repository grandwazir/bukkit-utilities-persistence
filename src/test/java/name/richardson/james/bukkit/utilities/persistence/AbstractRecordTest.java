package name.richardson.james.bukkit.utilities.persistence;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import name.richardson.james.bukkit.utilities.persistence.support.SimpleRecord;
import name.richardson.james.bukkit.utilities.persistence.support.TestDatabaseFactory;

public class AbstractRecordTest {

	private static final DatabaseLoader DATABASE_LOADER = TestDatabaseFactory.getSQLiteDatabaseLoader();

	@Test
	public void insertBlankRecordSuccessfully() {
		SimpleRecord record = new SimpleRecord();
		record.save();
	}

	@Test
	public void shouldReturnId() {
		SimpleRecord record = new SimpleRecord();
		record.save();
		Assert.assertNotNull("Id cannot be null", record.getId());
	}

	@Test
	public void shouldReturnCreatedAt() {
		SimpleRecord record = new SimpleRecord();
		record.save();
		Assert.assertNotNull("CreatedAt cannot be null", record.getId());
	}

	@Test
	public void shouldReturnDifferentModifiedAt() throws InterruptedException {
		SimpleRecord record = new SimpleRecord();
		record.save();
		Timestamp at = record.getModifiedAt();
		record.save();
		Assert.assertNotEquals("The modified time should have been updated!", at, record.getModifiedAt());
	}

	@Test
	public void shouldReturnModifiedAt() {
		SimpleRecord record = new SimpleRecord();
		record.save();
		Assert.assertNotNull("ModifiedAt cannot be null", record.getId());
	}

}
