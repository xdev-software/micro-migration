package software.xdev.micromigration.microstream;

import software.xdev.micromigration.scripts.Context;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;


/**
 * Interface for scripts to migrate / update datastores.
 * <p>
 * One script is supposed to bring a datastore from a lower version to the target version.
 * After the {@link software.xdev.micromigration.scripts.MigrationScript#migrate(Context)} method is called,
 * the target version is reached.
 * 
 * @author Johannes Rabauer
 *
 */
public interface MigrationScript<T> extends software.xdev.micromigration.scripts.MigrationScript<T, EmbeddedStorageManager>
{}
