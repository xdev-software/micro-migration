package software.xdev.micromigration.version;

/**
 * Interface used by the MigrationManagers for easier versioning of objects.
 * 
 * @author Johannes Rabauer
 * 
 */
public interface Versioned 
{
	/**
	 * @param version to set the current version of the object
	 */
	void setVersion(MigrationVersion version);

	/**
	 *
	 * @return the current version of the object
	 */
	MigrationVersion getVersion();
}
