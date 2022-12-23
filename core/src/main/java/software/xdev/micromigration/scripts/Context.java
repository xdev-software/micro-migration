package software.xdev.micromigration.scripts;

/**
 * Container that holds necessary information for the execution of an {@link VersionAgnosticMigrationScript}
 * 
 * @author Johannes Rabauer
 */
public class Context<T, E>
{
	private final T migratingObject;
	private final E storageManager ;

	/**
	 * @param migratingObject that must be migrated to a new version
	 * @param storageManager where the migratingObject is stored
	 */
	public Context(
		final T                                        migratingObject,
		final E storageManager
	) 
	{
		super();
		this.migratingObject = migratingObject;
		this.storageManager  = storageManager;
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
