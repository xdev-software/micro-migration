/*
 * Copyright © 2021 XDEV Software (https://xdev.software)
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

import software.xdev.micromigration.version.MigrationVersion;
import software.xdev.micromigration.versionagnostic.VersionAgnosticMigrationEmbeddedStorageManager;


@SuppressWarnings({"checkstyle:TypeName", "checkstyle:MethodName"})
class ReflectiveVersionMigrationScriptTest
{
	public static class v1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void correctName_v1_CorrectClassName()
	{
		Assertions.assertEquals(new MigrationVersion(1), new v1_CorrectClassName().getTargetVersion());
	}
	
	public static class v1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void correctName_v1_1_CorrectClassName()
	{
		Assertions.assertEquals(new MigrationVersion(1, 1), new v1_1_CorrectClassName().getTargetVersion());
	}
	
	public static class v1_1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void correctName_v1_1_1_CorrectClassName()
	{
		Assertions.assertEquals(new MigrationVersion(1, 1, 1), new v1_1_1_CorrectClassName().getTargetVersion());
	}
	
	public static class v10_1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void correctName_v10_1_1_CorrectClassName()
	{
		Assertions.assertEquals(new MigrationVersion(10, 1, 1), new v10_1_1_CorrectClassName().getTargetVersion());
	}
	
	public static class v10_10_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void correctName_v10_10_1_CorrectClassName()
	{
		Assertions.assertEquals(new MigrationVersion(10, 10, 1), new v10_10_1_CorrectClassName().getTargetVersion());
	}
	
	public static class v10_10_10_CorrectClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void correctName_v10_10_10_CorrectClassName()
	{
		Assertions.assertEquals(new MigrationVersion(10, 10, 10), new v10_10_10_CorrectClassName().getTargetVersion());
	}
	
	public static class a1_InvalidClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_a1_InvalidClassName()
	{
		Assertions.assertThrows(IllegalArgumentException.class, a1_InvalidClassName::new);
	}
	
	public static class foo1_InvalidClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_foo1_InvalidClassName()
	{
		Assertions.assertThrows(IllegalArgumentException.class, foo1_InvalidClassName::new);
	}
	
	public static class InvalidClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_InvalidClassName()
	{
		Assertions.assertThrows(IllegalArgumentException.class, InvalidClassName::new);
	}
	
	public static class InvalidClassName_v1 extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_InvalidClassName_v1()
	{
		Assertions.assertThrows(IllegalArgumentException.class, InvalidClassName_v1::new);
	}
	
	public static class v1_k_InvalidClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_v1_k_InvalidClassName()
	{
		Assertions.assertThrows(IllegalArgumentException.class, v1_k_InvalidClassName::new);
	}
	
	public static class v1_k_2_InvalidClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_v1_k_2_InvalidClassName()
	{
		Assertions.assertThrows(IllegalArgumentException.class, v1_k_2_InvalidClassName::new);
	}
	
	public static class v2147483648_InvalidClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_v2147483648_InvalidClassName()
	{
		Assertions.assertThrows(IllegalArgumentException.class, v2147483648_InvalidClassName::new);
	}
	
	public static class v___InvalidClassName extends ReflectiveVersionMigrationScriptDummy
	{
	}
	
	@Test
	void invalidName_v___InvalidClassName()
	{
		Assertions.assertThrows(IllegalArgumentException.class, v___InvalidClassName::new);
	}
	
	public static class ReflectiveVersionMigrationScriptDummy
		extends ReflectiveVersionMigrationScript<Object, VersionAgnosticMigrationEmbeddedStorageManager<Object,
		Object>>
	{
		@Override
		public void migrate(final Context<Object, VersionAgnosticMigrationEmbeddedStorageManager<Object, Object>> context)
		{
			// Dummy
		}
	}
}
