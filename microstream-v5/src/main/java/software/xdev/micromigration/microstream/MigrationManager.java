package software.xdev.micromigration.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationManager;
import software.xdev.micromigration.migrater.MicroMigrater;
import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.version.Versioned;

import java.util.function.Consumer;
import java.util.function.Supplier;


public class MigrationManager extends VersionAgnosticMigrationManager<EmbeddedStorageManager>
{
	public MigrationManager(
		Supplier<MigrationVersion> currentVersionGetter,
		Consumer<MigrationVersion> currentVersionSetter,
		Consumer<MigrationVersion> currentVersionStorer,
		MicroMigrater migrater,
		EmbeddedStorageManager storageManager)
	{
		super(currentVersionGetter, currentVersionSetter, currentVersionStorer, migrater, new TunnelingEmbeddedStorageManager(storageManager));
	}

	public MigrationManager(
		Versioned versionedObject, MicroMigrater migrater,
		EmbeddedStorageManager storageManager)
	{
		super(versionedObject, migrater, new TunnelingEmbeddedStorageManager(storageManager));
	}
}
