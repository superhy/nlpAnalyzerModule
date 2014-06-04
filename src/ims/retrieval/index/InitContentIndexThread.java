package ims.retrieval.index;

import ims.nlp.cache.ApplicationContextFactory;
import ims.nlp.mongo.service.RetrievalMongoService;
import ims.retrieval.util.TransMongoContent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.context.ApplicationContext;

import com.mongodb.DBObject;

/**
 * 全部文档初始索引建立线程
 * 
 * @author superhy
 * 
 */
public class InitContentIndexThread implements Callable<Boolean> {

	// TODO 添加注释

	private String collectionName;

	public InitContentIndexThread(String collectionName) {
		super();
		this.collectionName = collectionName;
	}

	public List<DBObject> getPostsInColl() {

		ApplicationContext appContext = ApplicationContextFactory.appContext;
		RetrievalMongoService retrievalMongoService = (RetrievalMongoService) appContext
				.getBean("retrievalMongoService");

		List<DBObject> postObjects = retrievalMongoService
				.findAllPostInColl(this.collectionName);

		return postObjects;
	}

	public Map<String, Object> transPostContent(DBObject postObject) {
		Map<String, Object> postIndexContentMap = TransMongoContent
				.producePostIndexContent(postObject, this.collectionName);

		return postIndexContentMap;
	}

	public boolean writePostIntoIndex(Map<String, Object> postIndexContentMap) {
		WriteDocIntoIndex writeDocIntoIndex = WriteDocIntoIndex
				.getWriteDocIntoIndex();

		String content = (String) postIndexContentMap.get("content");
		String collectionName = (String) postIndexContentMap
				.get("collectionName");
		String postUrlMD5 = (String) postIndexContentMap.get("postUrlMD5");

		try {
			writeDocIntoIndex.writerSinglePostIntoIndex(content,
					collectionName, postUrlMD5);

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
		List<DBObject> postObjects = this.getPostsInColl();

		for (DBObject postObject : postObjects) {
			Map<String, Object> postIndexContentMap = this
					.transPostContent(postObject);

			Boolean singleSuccFlag = this
					.writePostIntoIndex(postIndexContentMap);
			if (singleSuccFlag == false) {
				succFlag = false;
			}
		}

		return succFlag;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

}
