package org.superhy.search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchUnit {

	private Directory directory;
	private IndexReader reader;

	public SearchUnit() {
		try {
			directory = FSDirectory.open(new File("./file/index"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IndexSearcher getSearcher() {
		try {

			if (reader == null) {
				reader = IndexReader.open(directory);
			} else {
				IndexReader tr = IndexReader.openIfChanged(reader);
				if (tr != null) {
					reader.close();
					reader = tr;
				}
			}

			return new IndexSearcher(reader);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void firstSearcher(String keyWord) {

		try {

			// ����IndexReader����IndexSeacher
			IndexSearcher searcher = getSearcher();

			// ����������Query
			// ����parser��ȷ�������Ƚ������ݣ��ڶ���������ʾ��������
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			// ����query��ʾ������Ϊcontent�����ƶ����ĵ�
			Query query = parser.parse(keyWord);

			// ����seacher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, 10);

			// ����TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {

				// ����seacher��ScoreDoc�����ȡ�����Documnet����
				Document d = searcher.doc(sd.doc);

				// ����Documnet�����ȡ��Ҫ��ֵ
				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			// �ر�reader
			reader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void termSearcher(String field, String name, int num) {

		try {
			IndexSearcher searcher = getSearcher();
			Query query = new TermQuery(new Term(field, name));
			TopDocs tds = searcher.search(query, num);

			System.out.println("allSearchNum:" + tds.totalHits);

			for (ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);

				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			searcher.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void termRangeSearcher(String field, String start, String end,
			int num) {

		try {
			IndexSearcher searcher = getSearcher();
			Query query = new TermRangeQuery(field, start, end, true, true);
			TopDocs tds = searcher.search(query, num);

			System.out.println("allSearchNum:" + tds.totalHits);

			for (ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);

				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			searcher.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ǰ׺��ѯ
	 * 
	 * @param field
	 * @param value
	 * @param num
	 */
	public void prefixSearcher(String field, String value, int num) {

		try {
			IndexSearcher searcher = getSearcher();
			Query query = new PrefixQuery(new Term(field, value));
			TopDocs tds = searcher.search(query, num);

			System.out.println("allSearchNum:" + tds.totalHits);

			for (ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);

				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			searcher.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ͨ�����ѯ
	 * 
	 * @param field
	 * @param value
	 * @param num
	 */
	public void wildcardSearcher(String field, String value, int num) {
		try {
			IndexSearcher searcher = getSearcher();

			// �ڴ����value�п������ͨ�������*��ǰ�ߴ������ַ������ߴ������ַ���
			Query query = new WildcardQuery(new Term(field, value));
			TopDocs tds = searcher.search(query, num);

			System.out.println("allSearchNum:" + tds.totalHits);

			for (ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);

				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			searcher.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ģ����ѯ
	 * 
	 * @param field
	 * @param value
	 * @param num
	 */
	public void fuzzySearcher(String field, String value, int num) {

		try {
			IndexSearcher searcher = getSearcher();
			Query query = new FuzzyQuery(new Term(field, value));
			TopDocs tds = searcher.search(query, num);

			System.out.println("allSearchNum:" + tds.totalHits);

			for (ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);

				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			searcher.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void queryParserSearcher(Query query, int num) {
		
		try {
			IndexSearcher searcher = getSearcher();
			TopDocs tds = searcher.search(query, num);

			System.out.println("allSearchNum:" + tds.totalHits);

			for (ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);

				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			searcher.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
