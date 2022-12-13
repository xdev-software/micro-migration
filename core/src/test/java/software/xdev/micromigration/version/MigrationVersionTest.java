package software.xdev.micromigration.version;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import software.xdev.micromigration.version.MigrationVersion;


class MigrationVersionTest
{

	@Test
	void testToString_v1() 
	{
		MigrationVersion version = new MigrationVersion(1);
		assertEquals("v1", version.toString());
	}
	
	@Test
	void testToString_v11() 
	{
		MigrationVersion version = new MigrationVersion(1,1);
		assertEquals("v1.1", version.toString());
	}
	
	@Test
	void testToString_v111() 
	{
		MigrationVersion version = new MigrationVersion(1,1,1);
		assertEquals("v1.1.1", version.toString());
	}
	
	@Test
	void testToString_v0() 
	{
		MigrationVersion version = new MigrationVersion(0);
		assertEquals("v0", version.toString());
	}
	
	@Test
	void testToString_vNull() 
	{
		MigrationVersion version = new MigrationVersion();
		assertEquals("v0", version.toString());
	}

}
