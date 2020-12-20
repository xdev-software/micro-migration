package de.johannes_rabauer.micromigration;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;
import de.johannes_rabauer.micromigration.version.Versioned;
import one.microstream.storage.types.EmbeddedStorageManager;

public class MigrationManager 
{
	private final Supplier<MicroMigrationVersion> currentVersionGetter;
	private final Consumer<MicroMigrationVersion> currentVersionSetter;
	private final Consumer<MicroMigrationVersion> currentVersionStorer;
	private final MicroMigrater                   migrater            ;
	private final EmbeddedStorageManager          storageManager      ;

	public MigrationManager
	(
		final Supplier<MicroMigrationVersion> currentVersionGetter,
		final Consumer<MicroMigrationVersion> currentVersionSetter, 
		final Consumer<MicroMigrationVersion> currentVersionStorer,
		final MicroMigrater                   migrater            , 
		final EmbeddedStorageManager          storageManager
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
	
	public MigrationManager
	(
		final Versioned              versionedObject,
		final MicroMigrater          migrater       , 
		final EmbeddedStorageManager storageManager
	) 
	{
		this(
			()        -> versionedObject.getVersion()         ,
			(version) -> versionedObject.setVersion(version)  ,
			(version) -> storageManager.store(versionedObject),
			migrater,
			storageManager
		);
		Objects.requireNonNull(versionedObject);
	}
	
	public MigrationManager
	(
		final Versioned                       versionedObject     ,
		final Consumer<MicroMigrationVersion> currentVersionStorer,
		final MicroMigrater                   migrater            , 
		final EmbeddedStorageManager          storageManager
	) 
	{
		this(
			()        -> versionedObject.getVersion()       ,
			(version) -> versionedObject.setVersion(version),
			currentVersionStorer,
			migrater,
			storageManager
		);
		Objects.requireNonNull(versionedObject);
	}

	public void migrate(Object root)
	{
		final MicroMigrationVersion versionBeforeUpdate = currentVersionGetter.get();
		// Execute Updates
		final MicroMigrationVersion versionAfterUpdate = this.migrater.migrateToNewest(
			versionBeforeUpdate,
			this.storageManager,
			root
		);
		//Update stored version, if needed
		if(!versionAfterUpdate.equals(versionBeforeUpdate))
		{
			currentVersionSetter.accept(versionAfterUpdate);
			currentVersionStorer.accept(versionAfterUpdate);
		}
	}
}
