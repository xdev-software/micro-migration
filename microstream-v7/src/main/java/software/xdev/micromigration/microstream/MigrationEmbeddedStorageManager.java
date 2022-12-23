package software.xdev.micromigration.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.Versioned;


/**
 * Specific implementation of the {@link VersionAgnosticMigrationEmbeddedStorageManager} for one
 * specific MicroStream version.
 *
 * @see VersionAgnosticMigrationEmbeddedStorageManager
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
