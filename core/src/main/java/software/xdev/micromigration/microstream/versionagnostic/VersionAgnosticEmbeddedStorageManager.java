package software.xdev.micromigration.microstream.versionagnostic;

/**
 * To keep MicroStream from being directly referenced, this is a abstraction to keep the
 * actual EmbeddedStorageManager concealed.
 *
 * @param <T> is usually the one.microstream.storage.embedded.types.EmbeddedStorageManager,
 *           but to keep it version agnostic, this is dynamically typed.
 *
 * @author Johannes Rabauer
 */
public interface VersionAgnosticEmbeddedStorageManager<T>
{
	/**
	 * Stores the given object instance
	 * @param instance to store
	 * @return the object id representing the passed instance
	 */
	long store(final Object instance);

	/**
	 * @return the actual EmbeddedStorageManager
	 */
	T getNativeStorageManager();
}
