package de.johannes_rabauer.micromigration.version;

import java.util.Comparator;

/**
 * Defines one version of the MicroStream datastore.
 * 
 * @author Johannes Rabauer
 * 
 */
public class MigrationVersion 
{
	private final int majorVersion;
	private final int minorVersion;
	private final int patchVersion;
	
	public MigrationVersion
	(
		int majorVersion
	)
	{
		this(majorVersion, 0);
	}	
	
	public MigrationVersion
	(
		int majorVersion,
		int minorVersion
	)
	{
		this(majorVersion, minorVersion, 0);		
	}	
	
	public MigrationVersion
	(
		int majorVersion,
		int minorVersion,
		int patchVersion
	)
	{
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.patchVersion = patchVersion;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public int getPatchVersion() {
		return patchVersion;
	}
	
	@Override
	public String toString() {
		return "v" + this.majorVersion + "." + this.minorVersion + "." + this.patchVersion;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + majorVersion;
		result = prime * result + minorVersion;
		result = prime * result + patchVersion;
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
		if (majorVersion != other.majorVersion)
			return false;
		if (minorVersion != other.minorVersion)
			return false;
		if (patchVersion != other.patchVersion)
			return false;
		return true;
	}
	
	public static Comparator<MigrationVersion> COMPARATOR = new Comparator<MigrationVersion>() 
	{
		@Override
		public int compare(MigrationVersion o1, MigrationVersion o2) 
		{
			int majorVersionCompare = Integer.compare(o1.majorVersion, o2.majorVersion);
			if(majorVersionCompare != 0)
			{
				return majorVersionCompare;
			}
			int minorVersionCompare = Integer.compare(o1.minorVersion, o2.minorVersion);
			if(minorVersionCompare != 0)
			{
				return minorVersionCompare;
			}
			return Integer.compare(o1.patchVersion, o2.patchVersion);
		}
	};
}
