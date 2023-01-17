package software.xdev.micromigration.scripts;

import java.util.Comparator;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.version.MigrationVersion;


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
public interface VersionAgnosticMigrationScript<T, E extends VersionAgnosticMigrationEmbeddedStorageManager<?,?>>
{	
	/**
	 * @return the version of the datastore after this script is executed.
	 */
	MigrationVersion getTargetVersion();
	
	/**
	 * Execute logic to migrate the given datastore to a newer version of the store.
	 * After executing the {@link #getTargetVersion()} is reached.
	 * 
	 * @param context that holds necessary data for the migration
	 */
	void migrate(Context<T, E> context);

	/**
	 * Provides a {@link Comparator} that compares the {@link #getTargetVersion()} of the given scripts
	 */
	Comparator<VersionAgnosticMigrationScript<?, ?>> COMPARATOR =
		(o1, o2) -> MigrationVersion.COMPARATOR.compare(o1.getTargetVersion(), o2.getTargetVersion());
}
