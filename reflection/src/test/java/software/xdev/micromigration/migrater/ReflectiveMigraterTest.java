/*
 * Copyright © 2021 XDEV Software GmbH (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.valid");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			software.xdev.micromigration.migrater.scripts.valid.ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}
	
	@Test
	void testValidScriptWithIrrelevantClasses() throws ScriptInstantiationException {
		final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.moreClassesIncludingValid");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}
	
	@Test
	void testValidScriptWithSubpackages() throws ScriptInstantiationException {
		final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.includeSubPackages");
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
		final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.packageNotExisting");
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
		final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.abstractSuperClass");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			software.xdev.micromigration.migrater.scripts.abstractSuperClass.ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}

	@Test
	void testReflectiveVersion() throws ScriptInstantiationException {
		final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.reflectiveVersion");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			software.xdev.micromigration.migrater.scripts.reflectiveVersion.v1_ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}

	@Test
	void testReflectiveSuperClass() throws ScriptInstantiationException {
		final ReflectiveMigrater migrater = new ReflectiveMigrater("software.xdev.micromigration.migrater.scripts.abstractReflectiveSuperClass");
		assertEquals(1, migrater.getSortedScripts().size());
		assertEquals(
			v1_ValidScript.class,
			migrater.getSortedScripts().first().getClass()
		);
	}
}
