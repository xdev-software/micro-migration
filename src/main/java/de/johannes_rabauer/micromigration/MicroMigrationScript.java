package de.johannes_rabauer.micromigration;

import java.util.Comparator;

import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

public interface MicroMigrationScript 
{
	public final static char VERSION_SEPERATOR = '_';
	       final static String WRONG_FORMAT_ERROR_MESSAGE = "Script has invalid class name. Either rename the class to a valid script class name, or implement method getTargetVersion().";
	
	public default MicroMigrationVersion getTargetVersion()
	{
		final String implementationClassName = this.getClass().getSimpleName();
		int indexOfFirstVersionSeperator = implementationClassName.indexOf(VERSION_SEPERATOR);
		if(indexOfFirstVersionSeperator < 0)
		{
			throw new Error(WRONG_FORMAT_ERROR_MESSAGE);
		}
		int majorVersion = Integer.parseInt(implementationClassName.substring(0, indexOfFirstVersionSeperator));
		return new MicroMigrationVersion(majorVersion);
	}
	
	public void execute(
		Object                          root          ,
		MigrationEmbeddedStorageManager storageManager
	);
	
	public static Comparator<MicroMigrationScript> COMPARATOR = new Comparator<MicroMigrationScript>() 
	{
		@Override
		public int compare(MicroMigrationScript o1, MicroMigrationScript o2) {
			return MicroMigrationVersion.COMPARATOR.compare(o1.getTargetVersion(), o2.getTargetVersion());
		}
	};
}
