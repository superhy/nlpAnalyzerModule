package ims.nlp.lucene.util;

import java.util.ArrayList;
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

	public static List<Map<String, Object>> producePostIndexContent(
			DBObject postObject, String collectionNameStr) {

		// ׼�����ص�����
		List<Map<String, Object>> nodeMapList = new ArrayList<Map<String, Object>>();

		// ��ȡ�ڵ�ĸ��ֻ�����Ϣ
		String collectionName = collectionNameStr;
		String postUrlMD5 = (String) postObject.get("postUrlMD5");

		// ��������ÿ��article����Ϣ
		String title = (String) postObject.get("title");

		List<DBObject> articleList = (List<DBObject>) postObject.get("article");
		for (DBObject articleObject : articleList) {

			if (articleObject.get("articleContent") == null) {
				continue;
			}

			// article���ϵ���Ϣ
			String articleNodeFlag = "article";
			String articleNodeId = (String) articleObject.get("articleId");
			String articleNodeContent = title
					+ (String) articleObject.get("articleContent");

			// ��article���ϵ���Ϣװ��map����
			Map<String, Object> articleNodeMap = new HashMap<String, Object>();
			articleNodeMap.put("collectionName", collectionName);
			articleNodeMap.put("postUrlMD5", postUrlMD5);
			articleNodeMap.put("nodeFlag", articleNodeFlag);
			articleNodeMap.put("nodeId", articleNodeId);
			articleNodeMap.put("nodeContent", articleNodeContent);

			nodeMapList.add(articleNodeMap);

			// ���article�������е�reply���ϵ���Ϣ
			List<DBObject> replyList = articleObject.get("articleReply") != null ? (List<DBObject>) articleObject
					.get("articleReply")
					: new ArrayList<DBObject>();
			for (DBObject replyObject : replyList) {

				if (replyObject.get("replyContent") == null) {
					continue;
				}

				// reply���ϵ���Ϣ
				String replyNodeFlag = "reply";
				String replyNodeId = (String) replyObject.get("replyId");
				String replyNodeContent = (String) replyObject
						.get("replyContent");

				// ��reply���ϵ���Ϣװ��map����
				Map<String, Object> replyNodeMap = new HashMap<String, Object>();
				replyNodeMap.put("collectionName", collectionName);
				replyNodeMap.put("postUrlMD5", postUrlMD5);
				replyNodeMap.put("nodeFlag", replyNodeFlag);
				replyNodeMap.put("nodeId", replyNodeId);
				replyNodeMap.put("nodeContent", replyNodeContent);

				nodeMapList.add(replyNodeMap);
			}
		}

		return nodeMapList;
	}
}
