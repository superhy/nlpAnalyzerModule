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

			// 创建Directory
			// 创建索引到内存当中
			// Directory directory = new RAMDirectory();
			// 创建索引到硬盘当中
			directory = FSDirectory.open(new File("./file/Lucene_test_index"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 建立索引
	 * 
	 * @throws Exception
	 */
	public void createStandardIndex() {

		IndexWriter writer = null;

		try {

			// 创建IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(directory, iwc);

			// 创建Documnet
			Document doc = null;

			// 为Documnet添加Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = new Document();

				doc.add(new Field("content", FileUtils.readFileToString(file),
						Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("filelocation", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// 通过IndexWriter添加文档到索引中
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
				// 注意关闭索引写入流，否则会产生写锁
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
	 * 使用mmseg分词器创建索引
	 */
	public void createMmsegIndex() {
		IndexWriter writer = null;

		try {
			// 创建IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new MMSegAnalyzer());
			writer = new IndexWriter(directory, iwc);

			// 创建Documnet
			Document doc = null;

			// 为Documnet添加Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = new Document();

				doc.add(new Field("content", FileUtils.readFileToString(file),
						Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("filelocation", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// 通过IndexWriter添加文档到索引中
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
				// 注意关闭索引写入流，否则会产生写锁
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
			// 创建IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new DiyAnalyzerWithSynonym(new SimpleSynonymContextImpl()));
			writer = new IndexWriter(directory, iwc);

			// 创建Documnet
			Document doc = null;

			// 为Documnet添加Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = new Document();

				doc.add(new Field("content", FileUtils.readFileToString(file),
						Field.Store.NO, Field.Index.ANALYZED));
				doc.add(new Field("filename", file.getName(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("filelocation", file.getAbsolutePath(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));

				// 通过IndexWriter添加文档到索引中
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
				// 注意关闭索引写入流，否则会产生写锁
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
	 * 使用tika生成文档对象（tika用于解析 各种格式的文档）
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
	 * 使用tika生成索引
	 */
	public void createMmsegIndexByTika() {
		IndexWriter writer = null;

		try {
			// 创建IndexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					new MMSegAnalyzer());
			writer = new IndexWriter(directory, iwc);

			// 创建Documnet
			Document doc = null;

			// 为Documnet添加Feild
			File f = new File("./file/lucene_example");
			for (File file : f.listFiles()) {
				doc = generatorDoc(file);

				if (doc != null) {
					// 通过IndexWriter添加文档到索引中
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
				// 注意关闭索引写入流，否则会产生写锁
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
	 * 查询索引
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
	 * 删除索引
	 */
	public void deleteIndex() {

		IndexWriter writer = null;

		try {

			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			// 删除全部索引，也可以删除指定的索引，使用query查询（目前删除只是放在回收站了，不能被搜索到）
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
	 * 强制清空回收站
	 */
	@Test
	public void forceMergeIndex() {
		IndexWriter writer = null;

		try {

			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));

			// 删除全部索引，也可以删除指定的索引，使用query查询（目前删除只是放在回收站了，不能被搜索到）
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
