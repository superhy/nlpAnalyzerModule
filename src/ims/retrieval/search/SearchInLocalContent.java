package ims.retrieval.search;

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
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class SearchInLocalContent {

	private Directory directory;
	private IndexReader reader;

	public SearchInLocalContent(String indexPath) {
		super();
		try {
			this.directory = FSDirectory.open(new File(indexPath));
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
	 * �����ѯ,fieldΪ��ѯ����ֵ,valueΪ��ѯ�Ĺؼ���,numΪ��ѯ������
	 * 
	 * @param field
	 * @param value
	 * @param num
	 */
	public void phraseQuerySearcher(String value, int num) {

		try {

			// ����IndexReader����IndexSeacher
			IndexSearcher searcher = getSearcher();

			// ����������Query
			// ����parser��ȷ���������ݣ��ڶ���������ʾ�����������һ��������ʾ��ʹ�õķִ���
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new MMSegAnalyzer());
			// �趨��������Ϊ"��"����(Ĭ�ϣ���Ϊ����ʾ����)
			parser.setDefaultOperator(QueryParser.OR_OPERATOR);
			// ����query��ʾ������Ϊcontent�����ƶ����ĵ���ʹ�ö����ѯ
			Query query = parser.parse("\"" + "value" + "\"");

			// ����seacher�������ҷ���TopDocs
			TopDocs tds = searcher.search(query, num);

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
}
