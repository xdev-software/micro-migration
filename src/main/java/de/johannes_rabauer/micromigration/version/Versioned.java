package de.johannes_rabauer.micromigration.version;

import de.johannes_rabauer.micromigration.microstream.v5.MigrationManagerV5;

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
