package software.xdev.micromigration.microstream.v5;

import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.MigrationScript;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


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
