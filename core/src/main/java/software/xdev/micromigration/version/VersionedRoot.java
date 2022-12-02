package software.xdev.micromigration.version;

import java.util.Objects;

/**
 * This class is inserted as the root of the MicroStream datastore and contains only the 
 * current version and the actual root object.
 * 
 * @author Johannes Rabauer
 * 
 */
public class VersionedRoot implements Versioned 
{
	private MigrationVersion currentVersion;
	private Object           actualRoot    ;
	
	public VersionedRoot(Object actualRoot)
	{
		this.actualRoot     = actualRoot             ;
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
	
	public void setRoot(Object actualRoot)
	{
		this.actualRoot = actualRoot;
	}
	
	public Object getRoot()
	{
		return this.actualRoot;
	}
}
