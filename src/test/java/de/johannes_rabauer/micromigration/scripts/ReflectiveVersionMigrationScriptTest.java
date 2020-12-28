package de.johannes_rabauer.micromigration.scripts;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.johannes_rabauer.micromigration.version.MigrationVersion;

class ReflectiveVersionMigrationScriptTest 
{

	public static class v1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v1_CorrectClassName() 
	{
		assertEquals(new MigrationVersion(1), new v1_CorrectClassName().getTargetVersion());
	}

	public static class v1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v1_1_CorrectClassName() 
	{
		assertEquals(new MigrationVersion(1, 1), new v1_1_CorrectClassName().getTargetVersion());
	}

	public static class v1_1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v1_1_1_CorrectClassName() 
	{
		assertEquals(new MigrationVersion(1, 1, 1), new v1_1_1_CorrectClassName().getTargetVersion());
	}

	public static class v10_1_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v10_1_1_CorrectClassName() 
	{
		assertEquals(new MigrationVersion(10, 1, 1), new v10_1_1_CorrectClassName().getTargetVersion());
	}

	public static class v10_10_1_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v10_10_1_CorrectClassName() 
	{
		assertEquals(new MigrationVersion(10, 10, 1), new v10_10_1_CorrectClassName().getTargetVersion());
	}

	public static class v10_10_10_CorrectClassName extends ReflectiveVersionMigrationScriptDummy{}
	@Test
	void testCorrectName_v10_10_10_CorrectClassName() 
	{
		assertEquals(new MigrationVersion(10, 10, 10), new v10_10_10_CorrectClassName().getTargetVersion());
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


	public static class ReflectiveVersionMigrationScriptDummy extends ReflectiveVersionMigrationScript<Object>
	{
		@Override
		public void migrate(Context<Object> context) {
			//Dummy
		}
	}
}
