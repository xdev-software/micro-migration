package de.johannes_rabauer.micromigration.microstream.v5;

import de.johannes_rabauer.micromigration.scripts.Context;
import de.johannes_rabauer.micromigration.scripts.MigrationScript;
import de.johannes_rabauer.micromigration.version.MigrationVersion;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

import java.util.Comparator;


/**
 * Interface for scripts to migrate / update datastores.
 * <p>
 * One script is supposed to bring a datastore from a lower version to the target version.
 * After the {@link MigrationScriptV5#migrate(Context)} method is called,
 * the target version is reached.
 * 
 * @author Johannes Rabauer
 *
 */
public interface MigrationScriptV5<T> extends MigrationScript<T, EmbeddedStorageManager>
{}
