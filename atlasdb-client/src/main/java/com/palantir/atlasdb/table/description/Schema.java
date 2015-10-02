package com.palantir.atlasdb.table.description;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.palantir.atlasdb.cleaner.api.OnCleanupTask;
import com.palantir.atlasdb.schema.Namespace;

public interface Schema {

	TableDefinition getTableDefinition(String tableName);

	Map<String, TableMetadata> getAllTablesAndIndexMetadata();

	Set<String> getAllIndexes();

	IndexDefinition getIndex(String indexName);

	IndexDefinition getIndexForShortName(String indexName);

	/**
	 * Performs some basic checks on this schema to check its validity.
	 */
	void validate();

	Map<String, TableDefinition> getTableDefinitions();

	Map<String, IndexDefinition> getIndexDefinitions();

	Namespace getNamespace();

	Multimap<String, OnCleanupTask> getCleanupTasksByTable();

}