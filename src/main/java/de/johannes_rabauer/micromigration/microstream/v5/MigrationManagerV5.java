package de.johannes_rabauer.micromigration.microstream.v5;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import de.johannes_rabauer.micromigration.MigrationManager;
import de.johannes_rabauer.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import de.johannes_rabauer.micromigration.version.Versioned;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


/**
 * Manages a given object and keeps the version for it.
 * <p>
 * Can be used to keep the version of the MicroStream-Root-Object to keep the whole
 * datastore versioned. Since it is not integrated like the {@link MigrationEmbeddedStorageManager}
 * multiple versioned objects can be used in one datastore.
 * 
 * @author Johannes Rabauer
 *
 */
public class MigrationManagerV5 implements MigrationManager
{
	private final Supplier<MigrationVersion> currentVersionGetter;
	private final Consumer<MigrationVersion> currentVersionSetter;
	private final Consumer<MigrationVersion> currentVersionStorer;
	private final MicroMigrater              migrater            ;
	private final EmbeddedStorageManager     storageManager      ;

	/**
	 * Much more complicated constructor than {@link MigrationManagerV5#MigrationManagerV5(Versioned, MicroMigrater, EmbeddedStorageManager)}.
	 * 
	 * @param currentVersionGetter which supplies the current version of the object to update.
	 * @param currentVersionSetter which sets the new version of the object in some membervariable. This Consumer is not supposed to store the version, but only save it in some membervariable to be stored after.
	 * @param currentVersionStorer which is supposed to store the new version of the object somewhere in the datastore.
	 * @param migrater does the actual migration with the given {@link MigrationScript}
	 * @param storageManager for the {@link MigrationScript}s to use. Is not used for the storing of the new version.
	 */
	public MigrationManagerV5
	(
		final Supplier<MigrationVersion> currentVersionGetter,
		final Consumer<MigrationVersion> currentVersionSetter,
		final Consumer<MigrationVersion> currentVersionStorer,
		final MicroMigrater              migrater            ,
		final EmbeddedStorageManager     storageManager
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
	 * This object will be stored after the {@link MigrationScript}s are executed.
	 * @param migrater does the actual migration with the given {@link MigrationScript}
	 * @param storageManager for the {@link MigrationScript}s to use. Is not used for the storing of the new version.
	 */
	public MigrationManagerV5
	(
		final Versioned              versionedObject,
		final MicroMigrater          migrater       ,
		final EmbeddedStorageManager storageManager
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
			new TunnelingEmbeddedStorageManager(this.storageManager),
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
