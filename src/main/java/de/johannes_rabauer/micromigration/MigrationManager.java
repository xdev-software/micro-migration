package de.johannes_rabauer.micromigration;

import de.johannes_rabauer.micromigration.microstream.v5.MigrationEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.microstream.versionagnostic.VersionAgnosticEmbeddedStorageManager;
import de.johannes_rabauer.micromigration.migrater.MicroMigrater;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import de.johannes_rabauer.micromigration.version.Versioned;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;


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
public interface MigrationManager
{
	/**
	 * Migrates the given object to the newest possible version, defined by the {@link MicroMigrater}.
	 * @param objectToMigrate is given to the {@link MicroMigrater} for migrating upon
	 */
	public void migrate(Object objectToMigrate);
}
