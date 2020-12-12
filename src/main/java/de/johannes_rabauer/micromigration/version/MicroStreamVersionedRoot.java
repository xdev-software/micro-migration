package de.johannes_rabauer.micromigration.version;

public class MicroStreamVersionedRoot 
{
	private MicroMigrationVersion currentVersion;
	private Object                actualRoot    ;
	
	public MicroStreamVersionedRoot(Object actualRoot)
	{
		setRoot(actualRoot);
		setVersion(new MicroMigrationVersion(0,1,0));
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
