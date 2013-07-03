package com.music.recommender.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class BehaviorIndexService {

	private IndexWriter indexwriter;

	public BehaviorIndexService() throws IOException {
		this.indexwriter = IndexWriterProvider.getIndexWriter(
				FSDirectory.open(new File("behaviourindex")),
				new StandardAnalyzer(Version.LUCENE_43));
	}

	public void index(UserBehavior userBehavior) throws IOException {
		Document doc = new Document();

		Field userField = new Field("user", userBehavior.getUserName(),
				StringField.TYPE_STORED);
		Field musicField = new Field("music", userBehavior.getMusicName(),
				StringField.TYPE_STORED);
		Field id = new Field("id", userBehavior.getMusicId() + "",
				StringField.TYPE_STORED);
		IntField rating = new IntField("rating", userBehavior.getRating(),
				Store.YES);
		IntField timesPlayed = new IntField("timesplayed",
				userBehavior.getNoOfTimesMusicPlayed(), Store.YES);

		doc.add(userField);
		doc.add(musicField);
		doc.add(id);
		doc.add(rating);
		doc.add(timesPlayed);

		indexwriter.addDocument(doc);
		indexwriter.commit();
	}

	public UserBehavior getUserBehavior(long id, String user)
			throws IOException, QueryNodeException {
		SearcherManager searcherManager = new SearcherManager(indexwriter,
				true, new SearcherFactory());
		IndexSearcher indexSearcher = searcherManager.acquire();
		try {
			Query luceneQuery;
			StandardQueryParser queryParser = new StandardQueryParser();
			luceneQuery = queryParser.parse(
					String.format("user:%s AND id:%s", user, id + ""), "");
			Sort sort = new Sort(new SortField(null, SortField.Type.DOC, true));
			TopFieldDocs topFieldDocs = indexSearcher.search(luceneQuery, null,
					1, sort);
			ScoreDoc[] scoreDocs = topFieldDocs.scoreDocs;
			if (scoreDocs.length > 0) {
				Document document = indexSearcher.doc(scoreDocs[0].doc);
				return getUserBehaviorFromDoc(document);
			}
			return null;
		} finally {
			searcherManager.release(indexSearcher);
		}
	}

	private UserBehavior getUserBehaviorFromDoc(Document doc) {
		UserBehavior userBehavior = new UserBehavior();
		userBehavior.setMusicId(Long
				.parseLong(doc.getField("id").stringValue()));
		userBehavior.setMusicName(doc.getField("music").stringValue());
		userBehavior.setNoOfTimesMusicPlayed(doc.getField("timesplayed")
				.numericValue().intValue());
		userBehavior
				.setRating(doc.getField("rating").numericValue().intValue());
		userBehavior.setUserName(doc.getField("user").stringValue());
		return userBehavior;
	}

	public void close() throws IOException {
		indexwriter.waitForMerges();
		indexwriter.close();
	}

	public int getTotalCount() {
		return indexwriter.numDocs();
	}
}
