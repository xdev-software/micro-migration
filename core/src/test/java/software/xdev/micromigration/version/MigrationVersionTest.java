package software.xdev.micromigration.version;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class MigrationVersionTest
{
	
	@Test
	void testToString_v1()
	{
		final MigrationVersion version = new MigrationVersion(1);
		assertEquals("v1", version.toString());
	}
	
	@Test
	void testToString_v11()
	{
		final MigrationVersion version = new MigrationVersion(1, 1);
		assertEquals("v1.1", version.toString());
	}
	
	@Test
	void testToString_v111()
	{
		final MigrationVersion version = new MigrationVersion(1, 1, 1);
		assertEquals("v1.1.1", version.toString());
	}
	
	@Test
	void testToString_v0()
	{
		final MigrationVersion version = new MigrationVersion(0);
		assertEquals("v0", version.toString());
	}
	
	@Test
	void testToString_vNull()
	{
		final MigrationVersion version = new MigrationVersion();
		assertEquals("v0", version.toString());
	}
	
}
