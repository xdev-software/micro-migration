package software.xdev.micromigration.microstream.versionagnostic;

import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * Manages a given object and keeps the version for it.
 * <p>
 * Can be used to keep the version of the MicroStream-Root-Object to keep the whole
 * datastore versioned. Since it is not integrated like the {@link software.xdev.micromigration.microstream.MigrationEmbeddedStorageManager}
 * multiple versioned objects can be used in one datastore.
 * 
 * @author Johannes Rabauer
 *
 */
public class VersionAgnosticMigrationManager<T>
{
	private final Supplier<MigrationVersion> currentVersionGetter;
	private final Consumer<MigrationVersion> currentVersionSetter;
	private final Consumer<MigrationVersion> currentVersionStorer;
	private final MicroMigrater migrater            ;
	private final VersionAgnosticMigrationEmbeddedStorageManager<?, T> storageManager      ;

	/**
	 * Much more complicated constructor than {@link VersionAgnosticMigrationManager#MigrationManager(Versioned, MicroMigrater, EmbeddedStorageManager)}.
	 * 
	 * @param currentVersionGetter which supplies the current version of the object to update.
	 * @param currentVersionSetter which sets the new version of the object in some membervariable. This Consumer is not supposed to store the version, but only save it in some membervariable to be stored after.
	 * @param currentVersionStorer which is supposed to store the new version of the object somewhere in the datastore.
	 * @param migrater does the actual migration with the given {@link VersionAgnosticMigrationScript}
	 * @param storageManager for the {@link VersionAgnosticMigrationScript}s to use. Is not used for the storing of the new version.
	 */
	public VersionAgnosticMigrationManager
	(
		final Supplier<MigrationVersion> currentVersionGetter,
		final Consumer<MigrationVersion> currentVersionSetter,
		final Consumer<MigrationVersion> currentVersionStorer,
		final MicroMigrater              migrater            ,
		final VersionAgnosticMigrationEmbeddedStorageManager<?, T>     storageManager
	) 
	{
		Objects.requireNonNull(currentVersionGetter);
		Objects.requireNonNull(currentVersionSetter);
		Objects.requireNonNull(currentVersionStorer);
		Objects.requireNonNull(migrater);
		Objects.requireNonNull(storageManager);
		this.currentVersionGetter = currentVersionGetter;
		this.currentVersionSetter = currentVersionSetter;
		this.currentVersionStorer = currentVersionStorer;
		this.migrater = migrater;
		this.storageManager = storageManager;
	}
	
	/**
	 * Simple Constructor.
	 * 
	 * @param versionedObject which provides getter and setter for the current version. 
	 * This object will be stored after the {@link VersionAgnosticMigrationScript}s are executed.
	 * @param migrater does the actual migration with the given {@link VersionAgnosticMigrationScript}
	 * @param storageManager for the {@link VersionAgnosticMigrationScript}s to use. Is not used for the storing of the new version.
	 */
	public VersionAgnosticMigrationManager
	(
		final Versioned              versionedObject,
		final MicroMigrater          migrater       ,
		final VersionAgnosticMigrationEmbeddedStorageManager<?, T> storageManager
	) 
	{
		this(
			(       ) -> versionedObject.getVersion()         ,
			(version) -> versionedObject.setVersion(version)  ,
			(version) -> storageManager.store(versionedObject),
			migrater                                          ,
			storageManager
		);
		Objects.requireNonNull(versionedObject);
	}

	/**
	 * Migrates the given object to the newest possible version, defined by the {@link MicroMigrater}.
	 * @param objectToMigrate is given to the {@link MicroMigrater} for migrating upon
	 */
	public void migrate(Object objectToMigrate)
	{
		final MigrationVersion versionBeforeUpdate = currentVersionGetter.get();
		// Execute Updates
		final MigrationVersion versionAfterUpdate = this.migrater.migrateToNewest(
			versionBeforeUpdate,
			this.storageManager,
			objectToMigrate
		);
		//Update stored version, if needed
		if(!versionAfterUpdate.equals(versionBeforeUpdate))
		{
			currentVersionSetter.accept(versionAfterUpdate);
			currentVersionStorer.accept(versionAfterUpdate);
		}
	}
}
