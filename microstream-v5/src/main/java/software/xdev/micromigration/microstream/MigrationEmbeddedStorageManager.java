package software.xdev.micromigration.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationManager;
import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.Versioned;


/**
 * Wrapper class for the MicroStream {@link software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager} interface.
 * <p>
 * Basically it intercepts storing the root object and places a {@link software.xdev.micromigration.version.Versioned}
 * in front of it. This means the root object of the datastore is then versioned.<br>
 * Internally uses the {@link VersionAgnosticMigrationManager} to do the actual migration.
 *
 * @author Johannes Rabauer
 *
 */
public class MigrationEmbeddedStorageManager
	extends VersionAgnosticMigrationEmbeddedStorageManager<MigrationEmbeddedStorageManager, EmbeddedStorageManager>
{
	/**
	 * @param nativeManager which will be used as the underlying storage manager. 
	 * Almost all methods are only rerouted to this native manager. 
	 * Only {@link #start()}, {@link #root()} and {@link #setRoot(Object)} are intercepted 
	 * and a {@link Versioned} is placed between the requests.
	 * @param migrater which is used as source for the migration scripts
	 */
	public MigrationEmbeddedStorageManager(
		EmbeddedStorageManager nativeManager,
		MicroMigrater          migrater
	)
	{
		super(new TunnelingEmbeddedStorageManager(nativeManager), migrater);
	}
}
