package org.tader.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.tader.EntitySchema;
import org.tader.PropertyDef;
import org.tader.TestUtils;
import org.tader.jdbc.JdbcEntitySchema;
import org.tader.jdbc.NameTranslator;
import org.tader.jdbc.UpperCamelNameTranslator;

public class JdbcEntitySchemaTest {
	private static final Set<String> AUTHOR_COLUMN_NAMES = new HashSet<String>(Arrays.asList("AUTHOR_ID", "AUTHOR_NAME", "AUTHOR_HOBBY"));

	@Test
	public void testColumns() {
		for (TestJdbcTemplate template : TestUtils.getTestJdbcTemplates()) {
			testColumns(template);
		}
	}

	private void testColumns(TestJdbcTemplate template) {
		TestUtils.createTableAuthor(template);

		EntitySchema schema = createEntitySchema(template);

		assertEquals("authorId", schema.getPrimaryKeyPropertyName("author"));
		assertColumnNames(AUTHOR_COLUMN_NAMES, schema.getPropertyDefs("author"));
	}

	private EntitySchema createEntitySchema(TestJdbcTemplate template) {
		NameTranslator nameTranslator = new UpperCamelNameTranslator();
		return new JdbcEntitySchema(template, nameTranslator);
	}

	private void assertColumnNames(Set<String> expected, Collection<PropertyDef> propertyDefs) {
		Set<String> actual = new HashSet<String>();
		for (PropertyDef propDef : propertyDefs) {
			actual.add(propDef.getColumnName());
		}
		assertEquals(expected, actual);
	}
}