package ims.retrieval.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * 
 * @author superhy
 * 
 */
public class WriteDocIntoIndex {

	private Directory directory = null;

	/**
	 * ���췽��ֱ�ӳ�ʼ������
	 * 
	 * @param indexPath
	 */
	public WriteDocIntoIndex(String indexPath) {

		try {
			// ����������Ӳ�̵���
			this.directory = FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void writerSinglePostIntoIndex(String content,
			String collectionName, String postUrlMD5) {

		IndexWriter writer = null;

		try {
			// ����indexWriter
			MMSegAnalyzer mmSegAnalyzer = new MMSegAnalyzer(); // ʹ��mmseg���ķִ���
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					mmSegAnalyzer);
			writer = new IndexWriter(this.directory, iwc);

			// ����document
			Document document = null;

			// ��document�������ֵ�������Ƿ�洢���Ƿ�ִ�
			document.add(new Field("collectionName", collectionName,
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("postUrlMD5", postUrlMD5, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			document.add(new Field("content", content, Field.Store.NO,
					Field.Index.ANALYZED));

			// ͨ��IndexWriter����ĵ���������
			writer.addDocument(document);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// ע��ر�����д��������������д��
				if (writer != null) {
					writer.close();
				}
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
