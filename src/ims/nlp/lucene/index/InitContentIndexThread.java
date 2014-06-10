package ims.nlp.lucene.index;

import ims.nlp.cache.ApplicationContextFactory;
import ims.nlp.lucene.util.TransMongoContentForIndex;
import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.context.ApplicationContext;

import com.mongodb.DBObject;

/**
 * ȫ���ĵ���ʼ���������߳�
 * 
 * @author superhy
 * 
 */
public class InitContentIndexThread implements Callable<Boolean> {

	// ����߳���ִ�д�������������ļ�������
	private String collectionName;
	// ���������ĵ�ַ��ѯ����
	private String indexAllContentPath;

	public InitContentIndexThread(String collectionName,
			String indexAllContentPath) {
		super();
		this.collectionName = collectionName;
		this.indexAllContentPath = indexAllContentPath;
	}

	/**
	 * �õ�ĳ�����ϵ������еĽڵ����
	 * 
	 * @return
	 */
	public List<DBObject> getPostsInColl() {

		ApplicationContext appContext = ApplicationContextFactory.appContext;
		RetrievalMongoService retrievalMongoService = (RetrievalMongoService) appContext
				.getBean("retrievalMongoService");

		List<DBObject> postObjects = retrievalMongoService
				.findAllPostInColl(this.collectionName);

		return postObjects;
	}

	/**
	 * ��ÿ���ڵ����ת���ɽ���������Ҫ��ӳ�伯��
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
	 * ��ÿһ���ڵ�д���������������Ƿ�ɹ�
	 * 
	 * @param nodeIndexContentMap
	 * @return
	 */
	public boolean writePostIntoIndex(Map<String, Object> nodeIndexContentMap) {

		// ȡ����Ҫ������������ֵ
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
		// �ж��Ƿ�ȫ���ɹ�д�������ı��
		Boolean succFlag = true;

		// ��õ������������еĽڵ����
		List<DBObject> postObjects = this.getPostsInColl();

		for (DBObject postObject : postObjects) {
			List<Map<String, Object>> postIndexContentMapList = this
					.transPostContent(postObject);

			// Ϊÿһ��post�ڵ��е�ÿһ�����ϵ�(article or reply)��������
			for (Map<String, Object> nodeIndexContentMap : postIndexContentMapList) {
				Boolean singleSuccFlag = this
						.writePostIntoIndex(nodeIndexContentMap);
				if (singleSuccFlag == false) {
					succFlag = false;
				}
			}
		}

		// �����Ƿ�ȫ���ɹ�
		return succFlag;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getLuceneAllIndexPath() {
		return indexAllContentPath;
	}

	public void setLuceneAllIndexPath(String luceneAllIndexPath) {
		this.indexAllContentPath = luceneAllIndexPath;
	}

}
