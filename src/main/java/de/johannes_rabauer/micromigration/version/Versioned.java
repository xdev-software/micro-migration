package de.johannes_rabauer.micromigration.version;

/**
 * This class is inserted as the root of the MicroStream datastore and contains only the 
 * current version and the actual root object.
 * 
 * @author Johannes Rabauer
 * 
 */
public interface Versioned 
{
	public void setVersion(MicroMigrationVersion version);
	public MicroMigrationVersion getVersion();
}
