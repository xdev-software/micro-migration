package de.johannes_rabauer.micromigration.scripts;

import de.johannes_rabauer.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

/**
 * Container that holds necessary information for the execution of an {@link MigrationScript}
 * 
 * @author Johannes Rabauer
 */
public class Context<T, E>
{
	private final T migratingObject;
	private final E storageManager ;
	
	public Context(
		final T                                     migratingObject,
		final VersionAgnosticEmbeddedStorageManager<E> storageManager
	) 
	{
		super();
		this.migratingObject = migratingObject;
		this.storageManager  = storageManager.getNativeStorageManager();
	}

	/**
	 * @return the current object where the migration is executed upon
	 */
	public T getMigratingObject() 
	{
		return migratingObject;
	}
	
	/**
	 * @return the responsible storage manager for the migrating object
	 */
	public E getStorageManager()
	{
		return storageManager;
	}
}
