package ims.nlp.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class DeleteDocFromIndex {

	/**
	 * ��ʼ������Ŀ¼
	 * 
	 * @param indexPath
	 */
	public static Directory loadDirectory(String indexPath) {

		Directory directory = null;

		try {

			// ����������Ӳ�̵���
			directory = FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return directory;
	}

	/**
	 * ɾ�������е��ĵ�������վ
	 * 
	 * @param postUrlMD5
	 * @param indexPath
	 * @param analyzer
	 */
	public synchronized static void deleteSingleDocFromIndex(String postUrlMD5,
			String indexPath, Analyzer analyzer) {

		IndexWriter writer = null;

		try {
			Directory directory = loadDirectory(indexPath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			writer.deleteDocuments(new Term("postUrlMD5", postUrlMD5));
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
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ָ������ĵ�����վ��������ɾ���ĵ�
	 * 
	 * @param indexPath
	 */
	public synchronized static void recoverAllDeleteDoc(String indexPath) {

		IndexReader reader = null;

		try {
			Directory directory = loadDirectory(indexPath);
			// ����ֻ��Ϊfalse
			reader = IndexReader.open(directory, false);

			reader.undeleteAll();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ǿ����������ĵ�����վ�����Ƽ�ʹ��
	 * 
	 * @param indexPath
	 * @param analyzer
	 */
	@Deprecated
	public synchronized static void forceMergeAllDeleteDoc(String indexPath,
			Analyzer analyzer) {

		IndexWriter writer = null;

		try {
			Directory directory = loadDirectory(indexPath);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);
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
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
