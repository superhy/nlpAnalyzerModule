package ims.nlp.mongo.service;

import java.util.List;
import java.util.Set;

import com.mongodb.DBObject;

public interface RetrievalMongoService {

	/**
	 * �õ����еļ�������
	 * 
	 * @return
	 */
	public Set<String> findAllCollectionsName();
	
	/**
	 * �õ�ָ�����������е�post�ڵ�
	 * 
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> findAllPostInColl(String collectionName);
}
