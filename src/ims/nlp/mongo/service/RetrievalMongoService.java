package ims.nlp.mongo.service;

import java.util.List;
import java.util.Set;

import com.mongodb.DBObject;

public interface RetrievalMongoService {

	/**
	 * 得到所有的集合名称
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName();

	/**
	 * 得到指定集合中所有的post节点
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllPostInColl(String collectionName);

	/**
	 * 得到指定集合中属于特定任务的post节点
	 * 
	 * @param taskLogId
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findPostInCollByTasklogId(String taskLogId,
			String collectionName);
}
