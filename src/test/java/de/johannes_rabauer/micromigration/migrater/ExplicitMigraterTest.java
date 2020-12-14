package de.johannes_rabauer.micromigration.migrater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.johannes_rabauer.micromigration.testUtil.MicroMigrationScriptDummy;
import de.johannes_rabauer.micromigration.version.MicroMigrationVersion;

class ExplicitMigraterTest 
{
	
	@Test
	void testGetSortedScripts_empty() {
		final ExplicitMigrater migrater = new ExplicitMigrater();
		assertEquals(0,migrater.getSortedScripts().size());
	}	
	
	@Test
	void testGetSortedScripts_sorted() {
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new MicroMigrationScriptDummy(new MicroMigrationVersion(1)),
			new MicroMigrationScriptDummy(new MicroMigrationVersion(2))
		);
		assertEquals(1, migrater.getSortedScripts().first().getTargetVersion().getMajorVersion());
		assertEquals(2, migrater.getSortedScripts().last().getTargetVersion().getMajorVersion());
	}	
	
	@Test
	void testGetSortedScripts_unsorted() {
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new MicroMigrationScriptDummy(new MicroMigrationVersion(2)),
			new MicroMigrationScriptDummy(new MicroMigrationVersion(1))
		);
		assertEquals(1, migrater.getSortedScripts().first().getTargetVersion().getMajorVersion());
		assertEquals(2, migrater.getSortedScripts().last().getTargetVersion().getMajorVersion());
	}

}
