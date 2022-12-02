package software.xdev.micromigration.migrater;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.TreeSet;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import software.xdev.micromigration.scripts.MigrationScript;

/**
 * Contains all the available scripts to migrate the datastore to a certain version.
 * <p>
 * Searches all implementation of {@link MigrationScript} in the specified package
 * and it's the sub packages.
 * 
 * @author Johannes Rabauer
 * 
 */
public class ReflectiveMigrater extends AbstractMigrater
{
	private final TreeSet<MigrationScript<?,?>> sortedScripts = new TreeSet<>(MigrationScript.COMPARATOR);
	
	/**
	 * @param packagePath defines the package in which {@link MigrationScript}s will be searched.
	 * Also searches through all sub packages of <code>packagePath</code>
	 * @throws ScriptInstantiationException if a class in the given package could not be instantiated
	 */
	@SuppressWarnings("rawtypes")
	public ReflectiveMigrater(final String packagePath) throws ScriptInstantiationException 
	{
		Reflections reflections = new Reflections(
			new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage(packagePath))
				.setScanners(new SubTypesScanner())
				//I don't get why you have to filter again, but if you don't, super-packages will get included
				.filterInputsBy(new FilterBuilder().includePackage(packagePath))
		);
		
		for (Class<? extends MigrationScript> scriptClass : reflections.getSubTypesOf(MigrationScript.class)) 
		{
			//Only instanciate non abstract classes
			if(!Modifier.isAbstract(scriptClass.getModifiers()))
			{
				MigrationScript<?,?> instanciatedScript = instanciateClass(scriptClass);
				checkIfVersionIsAlreadyRegistered(instanciatedScript);
				this.sortedScripts.add(instanciatedScript);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private MigrationScript<?,?> instanciateClass(Class<? extends MigrationScript> scriptClass) throws ScriptInstantiationException
	{
		try {
			return scriptClass.getDeclaredConstructor().newInstance();
		} catch (
			InstantiationException    | 
			IllegalAccessException    | 
			IllegalArgumentException  | 
			InvocationTargetException | 
			NoSuchMethodException     | 
			SecurityException        e
		) {
			throw new ScriptInstantiationException("Could not instanciate class " + scriptClass.getName(), e);
		}
	}
	
	@Override
	public TreeSet<MigrationScript<?,?>> getSortedScripts()
	{
		return this.sortedScripts;
	}
}
