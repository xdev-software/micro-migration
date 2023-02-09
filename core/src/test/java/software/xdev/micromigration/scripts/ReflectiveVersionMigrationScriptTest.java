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
package software.xdev.micromigration.scripts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import software.xdev.micromigration.microstream.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;
import software.xdev.micromigration.version.MigrationVersion;

class ReflectiveVersionMigrationScriptTest 
{

	public static class v1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v1_CorrectClassName() 
	{
		Assertions.assertEquals(new MigrationVersion(1), new v1_CorrectClassName().getTargetVersion());
	}

	public static class v1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v1_1_CorrectClassName() 
	{
		Assertions.assertEquals(new MigrationVersion(1, 1), new v1_1_CorrectClassName().getTargetVersion());
	}

	public static class v1_1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v1_1_1_CorrectClassName() 
	{
		Assertions.assertEquals(new MigrationVersion(1, 1, 1), new v1_1_1_CorrectClassName().getTargetVersion());
	}

	public static class v10_1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v10_1_1_CorrectClassName() 
	{
		Assertions.assertEquals(new MigrationVersion(10, 1, 1), new v10_1_1_CorrectClassName().getTargetVersion());
	}

	public static class v10_10_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v10_10_1_CorrectClassName() 
	{
		Assertions.assertEquals(new MigrationVersion(10, 10, 1), new v10_10_1_CorrectClassName().getTargetVersion());
	}

	public static class v10_10_10_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v10_10_10_CorrectClassName() 
	{
		Assertions.assertEquals(new MigrationVersion(10, 10, 10), new v10_10_10_CorrectClassName().getTargetVersion());
	}

	public static class a1_InvalidClassName extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_a1_InvalidClassName() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new a1_InvalidClassName();
		});
	}

	public static class foo1_InvalidClassName extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_foo1_InvalidClassName() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new foo1_InvalidClassName();
		});
	}

	public static class InvalidClassName extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_InvalidClassName() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new InvalidClassName();
		});
	}

	public static class InvalidClassName_v1 extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_InvalidClassName_v1() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new InvalidClassName_v1();
		});
	}

	public static class v1_k_InvalidClassName extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_v1_k_InvalidClassName() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new v1_k_InvalidClassName();
		});
	}

	public static class v1_k_2_InvalidClassName extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_v1_k_2_InvalidClassName() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new v1_k_2_InvalidClassName();
		});
	}

	public static class v2147483648_InvalidClassName extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_v2147483648_InvalidClassName() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new v2147483648_InvalidClassName();
		});
	}

	public static class v___InvalidClassName extends ReflectiveVersionMigrationScriptDummy {	}
	@Test
	void testInvalildName_v___InvalidClassName() 
	{
		Assertions.assertThrows(Error.class, () -> {
			new v___InvalidClassName();
		});
	}


	public static class ReflectiveVersionMigrationScriptDummy extends ReflectiveVersionMigrationScript<Object, VersionAgnosticMigrationEmbeddedStorageManager<Object,Object>>
	{
		@Override
		public void migrate(final Context<Object, VersionAgnosticMigrationEmbeddedStorageManager<Object,Object>> context) {
			//Dummy
		}
	}
}
