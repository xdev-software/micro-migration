package software.xdev.micromigration.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationManager;
import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;

import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * Specific implementation of the {@link VersionAgnosticMigrationManager} for one
 * specific MicroStream version.
 *
 * @see VersionAgnosticMigrationManager
 */
public class MigrationManager extends VersionAgnosticMigrationManager<EmbeddedStorageManager>
{
	/**
	 * Much more complicated constructor than {@link MigrationManager(Versioned, MicroMigrater, EmbeddedStorageManager)}.
	 *
	 * @param currentVersionGetter which supplies the current version of the object to update.
	 * @param currentVersionSetter which sets the new version of the object in some membervariable. This Consumer is not supposed to store the version, but only save it in some membervariable to be stored after.
	 * @param currentVersionStorer which is supposed to store the new version of the object somewhere in the datastore.
	 * @param migrater does the actual migration with the given {@link VersionAgnosticMigrationScript}
	 * @param storageManager for the {@link VersionAgnosticMigrationScript}s to use. Is not used for the storing of the new version.
	 */
	public MigrationManager(
		Supplier<MigrationVersion> currentVersionGetter,
		Consumer<MigrationVersion> currentVersionSetter,
		Consumer<MigrationVersion> currentVersionStorer,
		MicroMigrater              migrater,
		EmbeddedStorageManager     storageManager
	)
	{
		super(currentVersionGetter, currentVersionSetter, currentVersionStorer, migrater, new MigrationEmbeddedStorageManager(storageManager, migrater));
	}

	/**
	 * Simple Constructor.
	 *
	 * @param versionedObject which provides getter and setter for the current version.
	 * This object will be stored after the {@link VersionAgnosticMigrationScript}s are executed.
	 * @param migrater does the actual migration with the given {@link VersionAgnosticMigrationScript}
	 * @param storageManager for the {@link VersionAgnosticMigrationScript}s to use. Is not used for the storing of the new version.
	 */
	public MigrationManager(
		Versioned              versionedObject,
		MicroMigrater          migrater       ,
		EmbeddedStorageManager storageManager
	)
	{
		super(versionedObject, migrater, new MigrationEmbeddedStorageManager(storageManager, migrater));
	}
}
