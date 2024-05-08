package software.xdev.micromigration.migrater.reflection;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

import software.xdev.micromigration.migrater.AbstractMigrater;
import software.xdev.micromigration.scripts.VersionAgnosticMigrationScript;


/**
 * Contains all the available scripts to migrate the datastore to a certain version.
 * <p>
 * Searches all implementation of {@link VersionAgnosticMigrationScript} in the specified package and it's the sub
 * packages.
 * </p>
 * <p>
 * Reflection source: <a href="https://stackoverflow.com/a/520344">https://stackoverflow.com/a/520344</a>
 * </p>
 * @author AB
 */
public class ReflectiveMigrater extends AbstractMigrater
{
	private final TreeSet<VersionAgnosticMigrationScript<?, ?>> sortedScripts = new TreeSet<>(
		VersionAgnosticMigrationScript.COMPARATOR);
	
	/**
	 * @param packagePath defines the package in which {@link VersionAgnosticMigrationScript}s will be searched. Also
	 *                    searches through all sub packages of <code>packagePath</code>
	 * @throws ScriptInstantiationException if a class in the given package could not be instantiated
	 */
	@SuppressWarnings("unchecked")
	public ReflectiveMigrater(final String packagePath) throws ScriptInstantiationException
	{
		getClasses(packagePath)
			.stream()
			.filter(c -> !Modifier.isAbstract(c.getModifiers()))
			.filter(VersionAgnosticMigrationScript.class::isAssignableFrom)
			.map(c -> (Class<? extends VersionAgnosticMigrationScript>)c)
			.map(this::instantiateClass)
			.forEach(instantiateScript -> {
				this.checkIfVersionIsAlreadyRegistered(instantiateScript);
				this.sortedScripts.add(instantiateScript);
			});
	}
	
	@SuppressWarnings("rawtypes")
	private VersionAgnosticMigrationScript<?, ?> instantiateClass(
		final Class<? extends VersionAgnosticMigrationScript> scriptClass)
		throws ScriptInstantiationException
	{
		try
		{
			return scriptClass.getDeclaredConstructor().newInstance();
		}
		catch(final InstantiationException
					| IllegalAccessException
					| IllegalArgumentException
					| InvocationTargetException
					| NoSuchMethodException
					| SecurityException e
		)
		{
			throw new ScriptInstantiationException("Could not instantiate class " + scriptClass.getName(), e);
		}
	}
	
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 */
	private static List<Class<?>> getClasses(final String packageName)
	{
		try
		{
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			assert classLoader != null;
			final String path = packageName.replace('.', '/');
			final Enumeration<URL> resources = classLoader.getResources(path);
			final List<File> dirs = new ArrayList<>();
			while(resources.hasMoreElements())
			{
				final URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			final ArrayList<Class<?>> classes = new ArrayList<>();
			for(final File directory : dirs)
			{
				classes.addAll(findClasses(directory, packageName));
			}
			return classes;
		}
		catch(final ClassNotFoundException e)
		{
			throw new IllegalStateException("Unable to find class", e);
		}
		catch(final IOException ioe)
		{
			throw new UncheckedIOException(ioe);
		}
	}
	
	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 */
	private static List<Class<?>> findClasses(final File directory, final String packageName)
		throws ClassNotFoundException
	{
		if(!directory.exists())
		{
			return new ArrayList<>();
		}
		
		final List<Class<?>> classes = new ArrayList<>();
		for(final File file : directory.listFiles())
		{
			if(file.isDirectory())
			{
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			}
			else if(file.getName().endsWith(".class"))
			{
				classes.add(Class.forName(
					packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
	@Override
	public TreeSet<VersionAgnosticMigrationScript<?, ?>> getSortedScripts()
	{
		return this.sortedScripts;
	}
}
