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

			// 根据IndexReader创建IndexSeacher
			IndexSearcher searcher = getSearcher();

			// 创建搜索的Query
			// 创建parser来确定搜索稳健的内容，第二个参数表示搜索的域
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));
			// 创建query表示搜索域为content包含制定的文档
			Query query = parser.parse(keyWord);

			// 根据seacher搜索并且返回TopDocs
			TopDocs tds = searcher.search(query, 10);

			// 根据TopDocs获取ScoreDoc对象
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {

				// 根据seacher和ScoreDoc对象获取具体的Documnet对象
				Document d = searcher.doc(sd.doc);

				// 根据Documnet对象获取需要的值
				System.out.println(d.get("filename") + "["
						+ d.get("filelocation") + "]");
			}

			// 关闭reader
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
	 * 前缀查询
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
	 * 通配符查询
	 * 
	 * @param field
	 * @param value
	 * @param num
	 */
	public void wildcardSearcher(String field, String value, int num) {
		try {
			IndexSearcher searcher = getSearcher();

			// 在传入的value中可以添加通配符？和*（前者代表单个字符，后者代表多个字符）
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
	 * 模糊查询
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
