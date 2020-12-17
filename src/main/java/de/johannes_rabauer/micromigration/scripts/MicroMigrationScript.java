package de.johannes_rabauer.micromigration.scripts;

import java.util.Comparator;

import de.johannes_rabauer.micromigration.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

/**
 * Interface for scripts to migrate / update datastores.
 * <p>
 * One script is supposed to bring a datastore from a lower version to the target version.
 * After the {@link MicroMigrationScript#execute(Object, MigrationEmbeddedStorageManager)} method is called,
 * the target version is reached.
 * 
 * @author Johannes Rabauer
 *
 */
public interface MicroMigrationScript 
{	
	/**
	 * @return the version of the datastore after this script is executed.
	 */
	public MicroMigrationVersion getTargetVersion();
	
	/**
	 * Execute logic to migrate the given datastore to a newer version of the store.
	 * After executing the {@link #getTargetVersion()} is reached.
	 * 
	 * @param root which is the current root object. Must be cast to the desired class.
	 * @param storageManager for storing-calls or other usage
	 */
	public void execute(
		Object                          root          ,
		MigrationEmbeddedStorageManager storageManager
	);
	
	public static Comparator<MicroMigrationScript> COMPARATOR = new Comparator<MicroMigrationScript>() 
	{
		@Override
		public int compare(MicroMigrationScript o1, MicroMigrationScript o2) {
			return MicroMigrationVersion.COMPARATOR.compare(o1.getTargetVersion(), o2.getTargetVersion());
		}
	};
}
