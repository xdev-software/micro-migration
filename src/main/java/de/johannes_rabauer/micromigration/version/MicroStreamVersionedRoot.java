package de.johannes_rabauer.micromigration.version;

/**
 * This class is inserted as the root of the MicroStream datastore and contains only the 
 * current version and the actual root object.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MicroStreamVersionedRoot 
{
	private MicroMigrationVersion currentVersion;
	private Object                actualRoot    ;
	
	public MicroStreamVersionedRoot(Object actualRoot)
	{
		setRoot(actualRoot);
		setVersion(new MicroMigrationVersion(0,0,0));
	}
	
	public void setVersion(MicroMigrationVersion version)
	{
		this.currentVersion = version;
	}
	
	public MicroMigrationVersion getVersion()
	{
		return this.currentVersion;
	}
	
	public void setRoot(Object actualRoot)
	{
		this.actualRoot = actualRoot;
	}
	
	public Object getRoot()
	{
		return this.actualRoot;
	}
}
