package de.johannes_rabauer.micromigration.version;

import de.johannes_rabauer.micromigration.MigrationManager;

/**
 * Interface used by the {@link MigrationManager} for easier versioning of objects.
 * 
 * @author Johannes Rabauer
 * 
 */
public interface Versioned 
{
	public void setVersion(MigrationVersion version);
	public MigrationVersion getVersion();
}
