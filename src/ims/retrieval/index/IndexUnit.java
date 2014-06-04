package ims.retrieval.index;

import ims.retrieval.analyzer.DiyAnalyzerWithSynonym;
import ims.retrieval.analyzer.impl.SimpleSynonymContextImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.junit.Test;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class IndexUnit {

	private Directory directory = null;

	public IndexUnit() {

		try {

			// ����Directory
			// �����������ڴ浱��
			// Directory directory = new RAMDirectory();
			// ����������Ӳ�̵���
			directory = FSDirectory.open(new File("./file/Lucene_test_index"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��������
	 * 
	 * @throws Exception
	 */
	public void createStandardIndex() {

		IndexWriter writer = null;

		try {

			// ����IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(directory, iwc);

			// ����Documnet
			Document doc = null;

			// ΪDocumnet���Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = new Document();

				doc.add(new Field("content", FileUtils.readFileToString(file),
						Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("filelocation", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// ͨ��IndexWriter����ĵ���������
				writer.addDocument(doc);
			}

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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

	/**
	 * ʹ��mmseg�ִ�����������
	 */
	public void createMmsegIndex() {
		IndexWriter writer = null;

		try {
			// ����IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new MMSegAnalyzer());
			writer = new IndexWriter(directory, iwc);

			// ����Documnet
			Document doc = null;

			// ΪDocumnet���Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = new Document();

				doc.add(new Field("content", FileUtils.readFileToString(file),
						Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("filelocation", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// ͨ��IndexWriter����ĵ���������
				writer.addDocument(doc);
			}

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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

	public void createDiySynonymIndex() {
		IndexWriter writer = null;

		try {
			// ����IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new DiyAnalyzerWithSynonym(new SimpleSynonymContextImpl()));
			writer = new IndexWriter(directory, iwc);

			// ����Documnet
			Document doc = null;

			// ΪDocumnet���Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = new Document();

				doc.add(new Field("content", FileUtils.readFileToString(file),
						Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("filelocation", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// ͨ��IndexWriter����ĵ���������
				writer.addDocument(doc);
			}

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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

	/**
	 * ʹ��tika�����ĵ�����tika���ڽ��� ���ָ�ʽ���ĵ���
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static Document generatorDoc(File file) throws Exception {

		if (file.isDirectory()) {
			return null;
		}

		Document doc = new Document();
		Metadata metadata = new Metadata();
		doc.add(new Field("content", new Tika().parse(
				new FileInputStream(file), metadata),
				TermVector.WITH_POSITIONS_OFFSETS));
		doc.add(new Field("filename", file.getName(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("filelocation", file.getAbsolutePath(),
				Field.Store.YES, Field.Index.NOT_ANALYZED));

		return doc;
	}

	/**
	 * ʹ��tika��������
	 */
	public void createMmsegIndexByTika() {
		IndexWriter writer = null;

		try {
			// ����IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new MMSegAnalyzer());
			writer = new IndexWriter(directory, iwc);

			// ����Documnet
			Document doc = null;

			// ΪDocumnet���Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = generatorDoc(file);

				if (doc != null) {
					// ͨ��IndexWriter����ĵ���������
					writer.addDocument(doc);
				}

			}

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
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

	/**
	 * ��ѯ����
	 */
	public void queryIndex() {

		try {

			IndexReader reader = IndexReader.open(directory);

			System.out.println("numDocs:" + reader.numDocs());
			System.out.println("maxDocs:" + reader.maxDoc());
			System.out.println("deleteDocs:" + reader.numDeletedDocs());

			reader.close();
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ɾ������
	 */
	public void deleteIndex() {

		IndexWriter writer = null;

		try {

			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			// ɾ��ȫ��������Ҳ����ɾ��ָ����������ʹ��query��ѯ��Ŀǰɾ��ֻ�Ƿ��ڻ���վ�ˣ����ܱ���������
			// writer.deleteDocuments(new Term("filename", "java.txt"));
			writer.deleteAll();
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

	/**
	 * ǿ����ջ���վ
	 */
	@Test
	public void forceMergeIndex() {
		IndexWriter writer = null;

		try {

			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			// ɾ��ȫ��������Ҳ����ɾ��ָ����������ʹ��query��ѯ��Ŀǰɾ��ֻ�Ƿ��ڻ���վ�ˣ����ܱ���������
			writer.forceMergeDeletes();
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
