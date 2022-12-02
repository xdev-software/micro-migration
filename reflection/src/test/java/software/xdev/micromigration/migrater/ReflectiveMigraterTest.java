package software.xdev.micromigration.migrater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.xdev.micromigration.migrater.scripts.abstractReflectiveSuperClass.v1_ValidScript;
import software.xdev.micromigration.migrater.scripts.includeSubPackages.subpackage.ValidScriptInSubpackage;
import software.xdev.micromigration.migrater.scripts.moreClassesIncludingValid.ValidScript;


class ReflectiveMigraterTest
{
	@Test
	void testValidScript() throws ScriptInstantiationException {
		ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.valid");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			software.xdev.micromigration.migrater.scripts.valid.ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}
	
	@Test
	void testValidScriptWithIrrelevantClasses() throws ScriptInstantiationException {
		ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.moreClassesIncludingValid");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}
	
	@Test
	void testValidScriptWithSubpackages() throws ScriptInstantiationException {
		ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.includeSubPackages");
		assertEquals(2, migrater.getSortedScripts().size());
		assertEquals(
			software.xdev.micromigration.migrater.scripts.includeSubPackages.ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
		assertEquals(
			ValidScriptInSubpackage.class,
			migrater.getSortedScripts().last().getClass()
		);
	}
	
	@Test
	void testPackageWithNoScript() throws ScriptInstantiationException {
		ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.packageNotExisting");
		assertEquals(0, migrater.getSortedScripts().size());
	}

	@Test
	void testExceptionThrowingScript() throws ScriptInstantiationException {
		Assertions.assertThrows(ScriptInstantiationException.class, () -> {
			new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.exceptionThrowing");
		});
	}

	@Test
	void testErrorThrowingScript() throws ScriptInstantiationException {
		Assertions.assertThrows(ScriptInstantiationException.class, () -> {
			new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.errorThrowing");
		});
	}

	@Test
	void testNoCorrectConstructor() throws ScriptInstantiationException {
		Assertions.assertThrows(ScriptInstantiationException.class, () -> {
			new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.noCorrectConstructor");
		});
	}

	@Test
	void testAbstractSuperClass() throws ScriptInstantiationException {
		ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.abstractSuperClass");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			software.xdev.micromigration.migrater.scripts.abstractSuperClass.ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}

	@Test
	void testReflectiveVersion() throws ScriptInstantiationException {
		ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.reflectiveVersion");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			software.xdev.micromigration.migrater.scripts.reflectiveVersion.v1_ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}

	@Test
	void testReflectiveSuperClass() throws ScriptInstantiationException {
		ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.abstractReflectiveSuperClass");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			v1_ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}
}
