package name.richardson.james.bukkit.utilities.persistence;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

import name.richardson.james.bukkit.utilities.localisation.BasicMessageHandler;
import name.richardson.james.bukkit.utilities.localisation.ColouredMessageHandler;

public final class MessagesFactory {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bukkit-utilities-persistence");
	private static final Messages PLAIN_MESSAGES = (Messages) Proxy.newProxyInstance(Messages.class.getClassLoader(), new Class[]{Messages.class}, new BasicMessageHandler(RESOURCE_BUNDLE));
	private static final Messages COLOURED_MESSAGES = (Messages) Proxy.newProxyInstance(Messages.class.getClassLoader(), new Class[]{Messages.class}, new ColouredMessageHandler(RESOURCE_BUNDLE));

	private MessagesFactory() {}

	public static Messages getMessages() {
		return PLAIN_MESSAGES;
	}

}
