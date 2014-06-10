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
	public Map<String, Object> transPostContent(DBObject postObject) {
		Map<String, Object> postIndexContentMap = TransMongoContentForIndex
				.producePostIndexContent(postObject, this.collectionName);

		return postIndexContentMap;
	}

	/**
	 * ��ÿһ���ڵ�д���������������Ƿ�ɹ�
	 * 
	 * @param postIndexContentMap
	 * @return
	 */
	public boolean writePostIntoIndex(Map<String, Object> postIndexContentMap) {

		// ȡ����Ҫ������������ֵ
		String content = (String) postIndexContentMap.get("content");
		String collectionName = (String) postIndexContentMap
				.get("collectionName");
		String postUrlMD5 = (String) postIndexContentMap.get("postUrlMD5");

		try {
			WriteDocIntoIndex.writerSinglePostIntoIndex(content,
					collectionName, postUrlMD5, this.indexAllContentPath);

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
			Map<String, Object> postIndexContentMap = this
					.transPostContent(postObject);

			Boolean singleSuccFlag = this
					.writePostIntoIndex(postIndexContentMap);
			if (singleSuccFlag == false) {
				succFlag = false;
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
