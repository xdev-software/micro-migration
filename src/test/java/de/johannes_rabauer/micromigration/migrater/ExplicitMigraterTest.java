package de.johannes_rabauer.micromigration.migrater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.johannes_rabauer.micromigration.testUtil.MicroMigrationScriptDummy;
import de.johannes_rabauer.micromigration.version.MigrationVersion;

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
			new MicroMigrationScriptDummy(new MigrationVersion(1)),
			new MicroMigrationScriptDummy(new MigrationVersion(2))
		);
		assertEquals(1, migrater.getSortedScripts().first().getTargetVersion().getVersions()[0]);
		assertEquals(2, migrater.getSortedScripts().last().getTargetVersion().getVersions()[0]);
	}	
	
	@Test
	void testGetSortedScripts_unsorted() {
		final ExplicitMigrater migrater = new ExplicitMigrater(
			new MicroMigrationScriptDummy(new MigrationVersion(2)),
			new MicroMigrationScriptDummy(new MigrationVersion(1))
		);
		assertEquals(1, migrater.getSortedScripts().first().getTargetVersion().getVersions()[0]);
		assertEquals(2, migrater.getSortedScripts().last().getTargetVersion().getVersions()[0]);
	}

}
