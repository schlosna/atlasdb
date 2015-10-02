package com.palantir.atlasdb.table.description;

import com.palantir.atlasdb.protos.generated.TableMetadataPersistence.SweepStrategy;
import com.palantir.atlasdb.transaction.api.ConflictHandler;

public interface TableMetadata {

	NameMetadataDescription getRowMetadata();

	ColumnMetadataDescription getColumns();

	boolean isRangeScanAllowed();

	boolean isDbCompressionRequested();

	boolean hasNegativeLookups();

	byte[] persistToBytes();

	ConflictHandler getConflictHandler();

	SweepStrategy getSweepStrategy();

}