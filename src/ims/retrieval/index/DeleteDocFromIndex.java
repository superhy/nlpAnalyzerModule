package ims.retrieval.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class DeleteDocFromIndex {

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

	public synchronized static void deleteSinglePostFromIndex(
			String postUrlMD5, String indexPath) {

		Directory directory = loadDirectory(indexPath);

		IndexWriter writer = null;
	}
}
