package ims.nlp.lucene.index;

import ims.nlp.cache.ApplicationContextFactory;
import ims.nlp.lucene.util.TransMongoContentForIndex;
import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.context.ApplicationContext;

import com.mongodb.DBObject;

public class AddNewContentIndexThread implements Callable<Boolean> {

	// 要添加的语料对应的任务编号
	private String taskLogId;
	// 对应的集合名称
	private String collectionName;

	// 索引所建的地址查询参数
	private String indexAllContentPath;

	public AddNewContentIndexThread(String taskLogId, String collectionName,
			String indexAllContentPath) {
		super();
		this.taskLogId = taskLogId;
		this.collectionName = collectionName;
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
	public List<Map<String, Object>> transPostContent(DBObject postObject) {
		List<Map<String, Object>> postIndexContentMapList = TransMongoContentForIndex
				.producePostIndexContent(postObject, this.collectionName);

		return postIndexContentMapList;
	}

	/**
	 * 将每一个节点写入索引，并返回是否成功
	 * 
	 * @param postIndexContentMap
	 * @return
	 */
	public boolean writePostIntoIndex(Map<String, Object> nodeIndexContentMap) {

		// 取出需要建立索引的域值
		String collectionName = (String) nodeIndexContentMap
				.get("collectionName");
		String postUrlMD5 = (String) nodeIndexContentMap.get("postUrlMD5");
		String nodeFlag = (String) nodeIndexContentMap.get("nodeFlag");
		String nodeId = (String) nodeIndexContentMap.get("nodeId");
		String nodeContent = (String) nodeIndexContentMap.get("nodeContent");

		try {
			WriteDocIntoIndex.writerSinglePostIntoIndex(collectionName,
					postUrlMD5, nodeFlag, nodeId, nodeContent,
					this.indexAllContentPath);

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

		// 获得单个集合中对应任务的所有的节点对象
		List<DBObject> postObjects = this.getPostsByTaskInColl();

		for (DBObject postObject : postObjects) {
			List<Map<String, Object>> postIndexContentMapList = this
					.transPostContent(postObject);

			// 为每一个post节点中的每一个语料点(article or reply)建立索引
			for (Map<String, Object> nodeIndexContentMap : postIndexContentMapList) {
				Boolean singleSuccFlag = this
						.writePostIntoIndex(nodeIndexContentMap);
				if (singleSuccFlag == false) {
					succFlag = false;
				}
			}
		}

		// 返回是否全部成功
		return succFlag;
	}
}
