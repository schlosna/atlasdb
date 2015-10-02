package com.palantir.atlasdb.table.description;

public interface TableDefinition {

	boolean isDbCompressionRequested();

	boolean isRangeScanAllowed();

	boolean hasNegativeLookups();

	int getMaxValueSize();

	String getGenericTableName();

	String getJavaTableName();

	TableMetadata toTableMetadata();
}