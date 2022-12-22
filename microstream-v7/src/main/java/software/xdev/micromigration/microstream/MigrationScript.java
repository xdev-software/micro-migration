package software.xdev.micromigration.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.xdev.micromigration.scripts.Context;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;


/**
 * Interface for scripts to migrate / update datastores.
 * <p>
 * One script is supposed to bring a datastore from a lower version to the target version.
 * After the {@link VersionAgnosticMigrationScript#migrate(Context)} method is called,
 * the target version is reached.
 * 
 * @author Johannes Rabauer
 *
 */
public interface MigrationScript<T> extends VersionAgnosticMigrationScript<T, EmbeddedStorageManager>
{}
