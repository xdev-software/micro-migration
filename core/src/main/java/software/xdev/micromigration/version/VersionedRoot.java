package software.xdev.micromigration.version;

import java.util.Objects;

/**
 * This class is inserted as the root of the MicroStream datastore and contains only the current version and the actual
 * root object.
 *
 * @author Johannes Rabauer
 */
public class VersionedRoot implements Versioned
{
	private MigrationVersion currentVersion;
	private Object actualRoot;
	
	/**
	 * @param actualRoot which is stored in the datastore and defined by the user
	 */
	public VersionedRoot(final Object actualRoot)
	{
		this.actualRoot = actualRoot;
		this.currentVersion = new MigrationVersion(0);
	}
	
	@Override
	public void setVersion(final MigrationVersion version)
	{
		Objects.requireNonNull(version);
		this.currentVersion = version;
	}
	
	@Override
	public MigrationVersion getVersion()
	{
		return this.currentVersion;
	}
	
	/**
	 * @param actualRoot which is stored in the datastore and defined by the user
	 */
	public void setRoot(final Object actualRoot)
	{
		this.actualRoot = actualRoot;
	}

	/**
	 * @return the actual root, that's defined by the user
	 */
	public Object getRoot()
	{
		return this.actualRoot;
	}
}
