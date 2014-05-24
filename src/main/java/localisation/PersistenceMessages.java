package localisation;

import com.vityuk.ginger.Localizable;

public interface PersistenceMessages extends Localizable {

	String databaseCreating();

	String databaseDropping();

	String databaseInvalid();

	String databaseLoading();

	String databaseValid();
}
