package de.johannes_rabauer.micromigration.scripts;

import de.johannes_rabauer.micromigration.version.MigrationVersion;

/**
 * Script which creates the target version of the script through the class name.
 * <p>
 * Class name has to be in the scheme:<br>
 * <code>
 * vM_Classname<br>
 * vM_m_Classname<br>
 * vM_m_p_Classname<br>
 * </code>
 * Where <code>v</code> is short for version and is a constant (just a char),<br>
 * <code>M</code> is a integer for the major version,<br>
 * <code>m</code> is a integer for the minor version,<br>
 * <code>p</code> is a integer for the patch version and<br>
 * <code>Classname</code> is a custom String that the user can choose.
 * <p>
 * If the class name has the wrong format, an {@link Error} is thrown.
 * 
 * @author Johannes Rabauer
 *
 */
public abstract class ReflectiveVersionMigrationScript implements  MigrationScript 
{
	private final static char   PREFIX                     = 'v';
	private final static String VERSION_SEPERATOR          = "_";
	private final static String WRONG_FORMAT_ERROR_MESSAGE = "Script has invalid class name. Either rename the class to a valid script class name, or implement method getTargetVersion().";

    private final MigrationVersion version;
    
    /**
     * @throws Error if the class name has the wrong format
     */
    public ReflectiveVersionMigrationScript() 
    {
    	this.version = createTargetVersionFromClassName();
	}
    
    private MigrationVersion createTargetVersionFromClassName()
    {
		final String implementationClassName = this.getClass().getSimpleName();
		if(PREFIX != implementationClassName.charAt(0))
		{
			throw new Error(WRONG_FORMAT_ERROR_MESSAGE);				
		}
		final String implementationClassNameWithoutPrefix = implementationClassName.substring(1);
		final String[] classNameParts = implementationClassNameWithoutPrefix.split(VERSION_SEPERATOR);
		if(classNameParts.length < 2)
		{
			throw new Error(WRONG_FORMAT_ERROR_MESSAGE);			
		}
		try
		{
			int majorVersion = Integer.parseInt(classNameParts[0]);
			if(classNameParts.length == 2)
			{
				return new MigrationVersion(majorVersion);				
			}
			int minorVersion = Integer.parseInt(classNameParts[1]);
			if(classNameParts.length == 3)
			{
				return new MigrationVersion(majorVersion, minorVersion);				
			}
			int patchVersion = Integer.parseInt(classNameParts[2]);
			return new MigrationVersion(majorVersion, minorVersion, patchVersion);	
		}
		catch (NumberFormatException e)
		{
			throw new Error(WRONG_FORMAT_ERROR_MESSAGE);			
		}
    }
    
	@Override
	public MigrationVersion getTargetVersion()
	{
		return this.version;
	}

}
