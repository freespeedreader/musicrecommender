package com.music.recommender.index;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

public class IndexWriterProvider {

	public static IndexWriter getIndexWriter(Directory dir, Analyzer analyzer)
			throws IOException {
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_43,
				analyzer);
		cfg.setRAMBufferSizeMB(256);
		LogByteSizeMergePolicy mp = new LogByteSizeMergePolicy();
		mp.setUseCompoundFile(true);
		mp.setNoCFSRatio(1.0);
		cfg.setMergePolicy(mp);
		cfg.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		return new IndexWriter(dir, cfg);
	}
}
