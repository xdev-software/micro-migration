package software.xdev.micromigration.version;

/**
 * Interface used by the MigrationManagers for easier versioning of objects.
 * 
 * @author Johannes Rabauer
 * 
 */
public interface Versioned 
{
	public void setVersion(MigrationVersion version);
	public MigrationVersion getVersion();
}
