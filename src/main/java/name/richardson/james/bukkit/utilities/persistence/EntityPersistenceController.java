package name.richardson.james.bukkit.utilities.persistence;

import java.sql.Timestamp;
import java.util.Set;

import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebean.event.BeanPersistRequest;

public class EntityPersistenceController implements BeanPersistController {

	@Override public int getExecutionOrder() {
		return 0;
	}

	@Override public boolean isRegisterFor(final Class<?> aClass) {
		return Record.class.isAssignableFrom(aClass);
	}

	@Override public boolean preInsert(final BeanPersistRequest<?> beanPersistRequest) {
		Record entity = (Record) beanPersistRequest.getBean();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		entity.setCreatedAt(now);
		return true;
	}

	@Override public boolean preUpdate(final BeanPersistRequest<?> beanPersistRequest) {
		return true;
	}

	@Override public boolean preDelete(final BeanPersistRequest<?> beanPersistRequest) {
		return true;
	}

	@Override public void postInsert(final BeanPersistRequest<?> beanPersistRequest) {

	}

	@Override public void postUpdate(final BeanPersistRequest<?> beanPersistRequest) {

	}

	@Override public void postDelete(final BeanPersistRequest<?> beanPersistRequest) {

	}

	@Override public void postLoad(final Object o, final Set<String> strings) {

	}

}
