package de.johannes_rabauer.micromigration.scripts;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;

/**
 * Container that holds necessary information for the execution of an {@link MigrationScript}
 * 
 * @author Johannes Rabauer
 */
public class Context<T>
{
	private final T                      migratingObject;
	private final EmbeddedStorageManager storageManager ;
	
	public Context(
		final T                      migratingObject, 
		final EmbeddedStorageManager storageManager
	) 
	{
		super();
		this.migratingObject = migratingObject;
		this.storageManager  = storageManager ;
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
	public EmbeddedStorageManager getStorageManager() 
	{
		return storageManager;
	}
}
