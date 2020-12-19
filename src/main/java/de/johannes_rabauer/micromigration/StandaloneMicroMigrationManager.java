package de.johannes_rabauer.micromigration;

import java.util.function.Consumer;
import java.util.function.Supplier;

import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;
import one.microstream.storage.types.EmbeddedStorageManager;

public class StandaloneMicroMigrationManager 
{
	private final Supplier<MicroMigrationVersion> currentVersionGetter;
	private final Consumer<MicroMigrationVersion> currentVersionSetter;
	private final Consumer<MicroMigrationVersion> currentVersionStorer;
	private final MicroMigrater                   migrater            ;
	private final EmbeddedStorageManager          storageManager      ;
	
	public StandaloneMicroMigrationManager
	(
		final Supplier<MicroMigrationVersion> currentVersionGetter,
		final Consumer<MicroMigrationVersion> currentVersionSetter, 
		final Consumer<MicroMigrationVersion> currentVersionStorer,
		final MicroMigrater                   migrater            , 
		final EmbeddedStorageManager          storageManager
	) 
	{
		this.currentVersionGetter = currentVersionGetter;
		this.currentVersionSetter = currentVersionSetter;
		this.currentVersionStorer = currentVersionStorer;
		this.migrater = migrater;
		this.storageManager = storageManager;
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
