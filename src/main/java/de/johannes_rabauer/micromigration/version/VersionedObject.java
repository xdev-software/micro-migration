package de.johannes_rabauer.micromigration.version;

import java.util.Objects;

/**
 * This class is inserted as the root of the MicroStream datastore and contains only the 
 * current version and the actual root object.
 * 
 * @author Johannes Rabauer
 * 
 */
public class VersionedObject implements Versioned 
{
	private MicroMigrationVersion currentVersion;
	private Object                actualObject    ;
	
	public VersionedObject(Object actualObject)
	{
		this.actualObject   = actualObject                    ;
		this.currentVersion = new MicroMigrationVersion(0,0,0);
	}
	
	@Override
	public void setVersion(MicroMigrationVersion version)
	{
		Objects.requireNonNull(version);
		this.currentVersion = version;
	}
	
	@Override
	public MicroMigrationVersion getVersion()
	{
		return this.currentVersion;
	}
	
	public void setObject(Object actualObject)
	{
		this.actualObject = actualObject;
	}
	
	public Object getObject()
	{
		return this.actualObject;
	}
	
	@Override
	public String toString() 
	{
		return this.currentVersion.toString() + "\n" + this.actualObject;
	}
}
