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
		if (!Arrays.equals(versions, other.versions))
			return false;
		return true;
	}

	public static Comparator<MigrationVersion> COMPARATOR = new Comparator<MigrationVersion>() 
	{
		@Override
		public int compare(MigrationVersion o1, MigrationVersion o2) 
		{
			return Arrays.compare(o1.versions, o2.versions);
		}
	};
}
