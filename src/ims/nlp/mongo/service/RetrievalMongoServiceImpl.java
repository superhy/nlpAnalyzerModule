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
	 * �õ����еļ�������
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName() {

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean();
		Set<String> collectionsName = mongoDbBean.getCollectionNames();

		return collectionsName;
	}

	/**
	 * �õ�ָ�����������е�post�ڵ�
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
	 * �õ�ָ�������������ض������post�ڵ�
	 * 
	 * @param taskLogId
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findPostInCollByTasklogId(String taskLogId,
			String collectionName) {

		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean();
		// ��Ӳ�ѯ������
		DBObject ref = new BasicDBObject();
		ref.put("taskLogId", taskLogId);
		List<DBObject> postsInColl = mongoDbBean.findByKeyAndRef(ref, null,
				collectionName);

		return postsInColl;
	}
}
