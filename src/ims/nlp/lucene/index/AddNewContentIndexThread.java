package ims.nlp.lucene.index;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.lucene.util.TransMongoContentForIndex;
import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.context.ApplicationContext;

import com.mongodb.DBObject;

public class AddNewContentIndexThread implements Callable<Boolean> {

	// 要添加的语料对应的任务编号
	private String taskLogId;
	// 对应的集合名称
	private String collectionName;
	// 所选用的分词器
	private Analyzer analyzer;

	// 索引所建的地址查询参数
	private String indexAllContentPath;

	public AddNewContentIndexThread(String taskLogId, String collectionName,
			Analyzer analyzer, String indexAllContentPath) {
		super();
		this.taskLogId = taskLogId;
		this.collectionName = collectionName;
		this.analyzer = analyzer;
		this.indexAllContentPath = indexAllContentPath;
	}

	/**
	 * 得到某个集合当中对应任务的所有的节点对象
	 * 
	 * @return
	 */
	public List<DBObject> getPostsByTaskInColl() {
		ApplicationContext appContext = ApplicationContextFactory.appContext;
		RetrievalMongoService retrievalMongoService = (RetrievalMongoService) appContext
				.getBean("retrievalMongoService");

		List<DBObject> postObjects = retrievalMongoService
				.findPostInCollByTasklogId(this.taskLogId, this.collectionName);

		return postObjects;
	}

	/**
	 * 将每个节点对象转化成建立索引需要的映射集合
	 * 
	 * @param postObject
	 * @return
	 */
	public Map<String, Object> transPostContent(DBObject postObject) {
		Map<String, Object> postIndexContentMap = TransMongoContentForIndex
				.producePostIndexContent(postObject, this.collectionName);

		return postIndexContentMap;
	}

	/**
	 * 将每一个节点写入索引，并返回是否成功
	 * 
	 * @param postIndexContentMap
	 * @return
	 */
	public boolean writePostIntoIndex(Map<String, Object> postIndexContentMap) {

		// 取出需要建立索引的域值
		String content = (String) postIndexContentMap.get("content");
		String collectionName = (String) postIndexContentMap
				.get("collectionName");
		String postUrlMD5 = (String) postIndexContentMap.get("postUrlMD5");

		try {
			WriteDocIntoIndex.writerSinglePostIntoIndex(content,
					collectionName, postUrlMD5, this.indexAllContentPath,
					this.analyzer);

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}

	public Boolean call() throws Exception {
		// 判断是否全部成功写入索引的标记
		Boolean succFlag = true;

		// 获得单个集合中所有的节点对象
		List<DBObject> postObjects = this.getPostsByTaskInColl();

		for (DBObject postObject : postObjects) {
			Map<String, Object> postIndexContentMap = this
					.transPostContent(postObject);

			Boolean singleSuccFlag = this
					.writePostIntoIndex(postIndexContentMap);
			if (singleSuccFlag == false) {
				succFlag = false;
			}
		}

		// 返回是否全部成功
		return succFlag;
	}
}
