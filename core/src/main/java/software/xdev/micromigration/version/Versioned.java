package software.xdev.micromigration.version;

import software.xdev.micromigration.microstream.v5.MigrationManagerV5;

/**
 * Interface used by the {@link MigrationManagerV5} for easier versioning of objects.
 * 
 * @author Johannes Rabauer
 * 
 */
public interface Versioned 
{
	public void setVersion(MigrationVersion version);
	public MigrationVersion getVersion();
}
