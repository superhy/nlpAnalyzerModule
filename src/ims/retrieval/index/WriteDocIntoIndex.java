package ims.retrieval.index;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

	// ����ģʽ����̬�ڲ���
	private static class WriterIndexHolder {
		private static final WriteDocIntoIndex WRITE_INDEX = new WriteDocIntoIndex();
	}

	/**
	 * ���췽��ֱ�ӳ�ʼ������
	 * 
	 * @param indexPath
	 */
	public WriteDocIntoIndex() {

		try {

			InputStream ins = new BufferedInputStream(new FileInputStream(
					"./src/index-path.properties"));

			Properties p = new Properties();
			p.load(ins);

			// ����������Ӳ�̵���
			this.directory = FSDirectory.open(new File(p
					.getProperty("lucene_all_index")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final WriteDocIntoIndex getWriteDocIntoIndex() {
		return WriterIndexHolder.WRITE_INDEX;
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
			Document document = new Document();

			// TODO delete print
			System.out.println("���ڴ���������" + collectionName + " " + postUrlMD5);

			// ��document�������ֵ�������Ƿ�洢���Ƿ�ִ�
			document.add(new Field("content", content, Field.Store.NO,
					Field.Index.ANALYZED));
			document.add(new Field("collectionName", collectionName,
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("postUrlMD5", postUrlMD5, Field.Store.YES,
					Field.Index.NOT_ANALYZED));

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
