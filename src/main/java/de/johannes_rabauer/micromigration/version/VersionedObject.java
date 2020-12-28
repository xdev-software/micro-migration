package de.johannes_rabauer.micromigration.version;

import java.util.Objects;

/**
 * Simple container to hold a specific object and a correlating version for it. 
 * 
 * @param <T> type of the object that's contained
 * 
 * @author Johannes Rabauer
 * 
 */
public class VersionedObject<T> implements Versioned 
{
	private MigrationVersion currentVersion;
	private T                actualObject  ;
	
	public VersionedObject(T actualObject)
	{
		this.actualObject   = actualObject               ;
		this.currentVersion = new MigrationVersion(0);
	}
	
	@Override
	public void setVersion(MigrationVersion version)
	{
		Objects.requireNonNull(version);
		this.currentVersion = version;
	}
	
	@Override
	public MigrationVersion getVersion()
	{
		return this.currentVersion;
	}
	
	public void setObject(T actualObject)
	{
		this.actualObject = actualObject;
	}
	
	public T getObject()
	{
		return this.actualObject;
	}
	
	@Override
	public String toString() 
	{
		return this.currentVersion.toString() + "\n" + this.actualObject;
	}
}
