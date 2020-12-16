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
	public MicroMigrationVersion getTargetVersion();
	
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
