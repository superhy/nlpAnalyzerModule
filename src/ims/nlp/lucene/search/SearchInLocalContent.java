package ims.nlp.lucene.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchInLocalContent {

	private Directory directory;
	private IndexReader reader;

	public SearchInLocalContent(String indexPath) {
		super();
		try {

			// ����������Ӳ�̵���
			directory = FSDirectory.open(new File(indexPath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IndexSearcher getSearcher() {
		try {

			if (this.reader == null) {
				this.reader = IndexReader.open(this.directory);
			} else {
				IndexReader tr = IndexReader.openIfChanged(this.reader);
				if (tr != null) {
					this.reader.close();
					this.reader = tr;
				}
			}

			return new IndexSearcher(this.reader);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ģ��������ѯ,fieldΪ��ѯ����ֵ,valueΪ��ѯ�Ĺؼ���,numΪ��ѯ������
	 * 
	 * @param field
	 * @param value
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> phraseQuerySearcher(String value, int num,
			Analyzer analyzer) {

		// ׼����ѯ���ؽ��
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();

		try {

			// ����IndexReader����IndexSeacher
			IndexSearcher searcher = getSearcher();

			// TODO ������Ҫ�����ݿ⴫��ͣ�ôʣ��γ�ͣ�ôʶ���

			// ����������Query
			// ����parser��ȷ���������ݣ��ڶ���������ʾ�����������һ��������ʾ��ʹ�õķִ���
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					analyzer);
			// �趨��������Ϊ"��"����(Ĭ�ϣ���Ϊ����ʾ����)
			parser.setDefaultOperator(QueryParser.OR_OPERATOR);
			// ����query��ʾ������Ϊcontent�����ƶ����ĵ���ʹ�ö����ѯ
			Query query = parser.parse(value);

			// TODO delete print
			System.out.println(query.toString());

			// ����seacher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, num);

			// ����TopDocs��ȡScoreDoc����
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {

				// ����seacher��ScoreDoc�����ȡ�����Documnet����
				Document d = searcher.doc(sd.doc);

				// TODO delete print ����Documnet�����ȡ��Ҫ��ֵ
				// System.out.println(d.get("collectionName") + "["
				// + d.get("postUrlMD5") + "]");

				// װ�ز�ѯ���ؽ��
				Map<String, Object> searchResultMap = new HashMap<String, Object>();
				searchResultMap.put("collectionName", d.get("collectionName"));
				searchResultMap.put("postUrlMD5", d.get("postUrlMD5"));
				searchResults.add(searchResultMap);
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

		return searchResults;
	}
}
