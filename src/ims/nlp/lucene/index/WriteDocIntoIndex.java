package ims.nlp.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
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

	/**
	 * 初始化索引目录
	 * 
	 * @param indexPath
	 */
	public static Directory loadDirectory(String indexPath) {

		Directory directory = null;

		try {

			// 创建索引到硬盘当中
			directory = FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return directory;
	}

	public synchronized static void writerSinglePostIntoIndex(String content,
			String collectionName, String postUrlMD5, String indexPath, Analyzer analyzer) {

		Directory directory = loadDirectory(indexPath);

		IndexWriter writer = null;

		try {
			// 创建indexWriter
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
			writer = new IndexWriter(directory, iwc);

			// 创建document
			Document document = new Document();

			// TODO delete print
			System.out.println("正在创建索引：" + collectionName + " " + postUrlMD5);

			// 向document中添加域值，设置是否存储和是否分词
			document.add(new Field("content", content, Field.Store.NO,
					Field.Index.ANALYZED));
			document.add(new Field("collectionName", collectionName,
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("postUrlMD5", postUrlMD5, Field.Store.YES,
					Field.Index.NOT_ANALYZED));

			// 通过IndexWriter添加文档到索引中
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
}
