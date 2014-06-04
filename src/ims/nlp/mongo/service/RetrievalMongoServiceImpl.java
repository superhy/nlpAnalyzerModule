package ims.nlp.mongo.service;

import ims.nlp.mongo.MongoDbBean;

import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * @author superhy
 * 
 */
public class RetrievalMongoServiceImpl {

	/**
	 * 得到所有的集合名称
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName() {

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean();
		Set<String> collectionsName = mongoDbBean.getCollectionNames();

		return collectionsName;
	}

	/**
	 * 得到指定集合中所有的post节点
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllPostInColl(String collectionName) {

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean();
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(null, null,
				collectionName);

		return postsInColl;
	}

	/**
	 * 得到指定集合中属于特定任务的post节点
	 * 
	 * @param taskLogId
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findPostInCollByTasklogId(String taskLogId,
			String collectionName) {

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean();
		// 添加查询的条件
		DBObject ref = new BasicDBObject();
		ref.put("taskLogId", taskLogId);
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		return postsInColl;
	}
}
