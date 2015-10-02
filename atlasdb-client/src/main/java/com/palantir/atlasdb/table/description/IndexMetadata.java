package com.palantir.atlasdb.table.description;

public interface IndexMetadata {

	/**
	 * @return the table metadata for the index table
	 */
	TableMetadata getTableMetadata();

}