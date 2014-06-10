package ims.nlp.lucene.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

/**
 * ��DBObject����ת���ɴ�����������Ҫ��ӳ�伯��
 * 
 * @author superhy
 * 
 */
public class TransMongoContentForIndex {

	public static Map<String, Object> producePostIndexContent(
			DBObject postObject, String collectionName) {

		// ׼�����صĽڵ�����ӳ�伯�ϣ�Ϊ��������ʹ�ã�
		Map<String, Object> postIndexContentMap = new HashMap<String, Object>();

		postIndexContentMap.put("collectionName", collectionName);
		postIndexContentMap.put("postUrlMD5",
				(String) postObject.get("postUrlMD5"));

		// ���ڵ������е���������ת�����ַ���
		String postAllContent = "";
		postAllContent += ((String) postObject.get("title") + "\r\n");
		// �õ�����ڵ������е�article
		List<DBObject> articleList = (List<DBObject>) postObject.get("article");
		if (articleList != null) {
			for (DBObject articleObject : articleList) {
				String articleContent = (String) articleObject
						.get("articleContent");
				postAllContent += (articleContent + "\r\n");

				// �õ�һ��article�����е�replyԪ��
				List<DBObject> replyList = (List<DBObject>) articleObject
						.get("articleReply");
				if (replyList == null) {
					continue;
				}
				for (DBObject replyObject : replyList) {
					String replyContent = (String) replyObject
							.get("replyContent");
					postAllContent += (replyContent + "r\\n");
				}
			}
		}
		// ��postAllContent�����ڵ�����ӳ�伯��
		postIndexContentMap.put("content", postAllContent);

		return postIndexContentMap;
	}
}
