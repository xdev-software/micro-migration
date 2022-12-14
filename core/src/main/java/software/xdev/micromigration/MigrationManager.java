package software.xdev.micromigration;

/**
 * Manages a given object and keeps the version for it.
 * <p>
 * Can be used to keep the version of the MicroStream-Root-Object to keep the whole
 * datastore versioned. Since it is not integrated like the MigrationEmbeddedStorageManager
 * multiple versioned objects can be used in one datastore.
 *
 * @author Johannes Rabauer
 *
 */
public interface MigrationManager
{
	/**
	 * Migrates the given object to the newest possible version, defined by the {@link software.xdev.micromigration.migrater.MicroMigrater}.
	 * @param objectToMigrate is given to the {@link software.xdev.micromigration.migrater.MicroMigrater} for migrating upon
	 */
	void migrate(Object objectToMigrate);
}
