package software.xdev.micromigration.version;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Defines one version of the MicroStream datastore.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationVersion 
{
	private final int[] versions;

	/**
	 * @param versions as integers. For example 1.0.2 would be an array of [1,0,2]
	 */
	public MigrationVersion(int... versions)
	{
		if(versions == null || versions.length == 0)
		{
			this.versions = new int[] {0};
		}
		else
		{
			this.versions = versions;
		}
	}

	/**
	 * @param versionsAsList as integers. For example 1.0.2 would be a list of [1,0,2]
	 */
	public MigrationVersion(List<Integer> versionsAsList)
	{
		if(versionsAsList == null || versionsAsList.size() == 0)
		{
			this.versions = new int[] {0};
		}
		else
		{
			int[] versionsAsArray = new int[versionsAsList.size()];
			for(int i = 0; i < versionsAsArray.length; i++)
			{
				versionsAsArray[i] = versionsAsList.get(i);
			}
			this.versions = versionsAsArray;
		}
	}

	/**
	 * @return versions as an array of integers. For example 1.0.2 would be an array of [1,0,2]
	 */
	public int[] getVersions() 
	{
		return this.versions;
	}
	
	@Override
	public String toString() 
	{
		final StringBuilder sb = new StringBuilder("v");
		for (int version : versions) 
		{
			sb.append(version).append(".");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(versions);
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MigrationVersion other = (MigrationVersion) obj;
		return Arrays.equals(versions, other.versions);
	}

	/**
	 * Provides a {@link Comparator} that compares the {@link #getVersions()} of the given versions
	 */
	public static Comparator<MigrationVersion> COMPARATOR =
		Comparator.comparing(MigrationVersion::getVersions, (o1, o2) ->  Arrays.compare(o1,o2));
}
