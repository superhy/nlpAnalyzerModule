package ims.retrieval.index;

import ims.analyze.cache.ApplicationContextFactory;
import ims.analyze.mongo.service.RetrievalMongoService;
import ims.retrieval.util.TransMongoContent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.context.ApplicationContext;

import com.mongodb.DBObject;

public class AddNewContentIndexThread implements Callable<Boolean> {

	// Ҫ��ӵ����϶�Ӧ��������
	private String taskLogId;
	// ��Ӧ�ļ�������
	private String collectionName;

	// ���������ĵ�ַ��ѯ����
	private String indexAllContentPath;

	public AddNewContentIndexThread(String taskLogId, String collectionName,
			String indexAllContentPath) {
		super();
		this.taskLogId = taskLogId;
		this.collectionName = collectionName;
		this.indexAllContentPath = indexAllContentPath;
	}

	/**
	 * �õ�ĳ�����ϵ��ж�Ӧ��������еĽڵ����
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
	 * ��ÿ���ڵ����ת���ɽ���������Ҫ��ӳ�伯��
	 * 
	 * @param postObject
	 * @return
	 */
	public Map<String, Object> transPostContent(DBObject postObject) {
		Map<String, Object> postIndexContentMap = TransMongoContent
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

		// ��õ��������ж�Ӧ��������еĽڵ����
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

		// �����Ƿ�ȫ���ɹ�
		return succFlag;
	}
}
