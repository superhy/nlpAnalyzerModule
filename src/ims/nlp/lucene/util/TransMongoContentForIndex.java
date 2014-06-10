package ims.nlp.lucene.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

/**
 * 将DBObject对象转化成创建索引所需要的映射集合
 * 
 * @author superhy
 * 
 */
public class TransMongoContentForIndex {

	public static List<Map<String, Object>> producePostIndexContent(
			DBObject postObject, String collectionNameStr) {

		// 准备返回的数据
		List<Map<String, Object>> nodeMapList = new ArrayList<Map<String, Object>>();

		// 获取节点的各种基本信息
		String collectionName = collectionNameStr;
		String postUrlMD5 = (String) postObject.get("postUrlMD5");

		// 标题算入每个article的信息
		String title = (String) postObject.get("title");

		List<DBObject> articleList = (List<DBObject>) postObject.get("article");
		for (DBObject articleObject : articleList) {

			if (articleObject.get("articleContent") == null) {
				continue;
			}

			// article语料点信息
			String articleNodeFlag = "article";
			String articleNodeId = (String) articleObject.get("articleId");
			String articleNodeContent = title
					+ (String) articleObject.get("articleContent");

			// 将article语料点信息装入map容器
			Map<String, Object> articleNodeMap = new HashMap<String, Object>();
			articleNodeMap.put("collectionName", collectionName);
			articleNodeMap.put("postUrlMD5", postUrlMD5);
			articleNodeMap.put("nodeFlag", articleNodeFlag);
			articleNodeMap.put("nodeId", articleNodeId);
			articleNodeMap.put("nodeContent", articleNodeContent);

			nodeMapList.add(articleNodeMap);

			// 获得article下面所有的reply语料点信息
			List<DBObject> replyList = articleObject.get("articleReply") != null ? (List<DBObject>) articleObject
					.get("articleReply")
					: new ArrayList<DBObject>();
			for (DBObject replyObject : replyList) {

				if (replyObject.get("replyContent") == null) {
					continue;
				}

				// reply语料点信息
				String replyNodeFlag = "reply";
				String replyNodeId = (String) replyObject.get("replyId");
				String replyNodeContent = (String) replyObject
						.get("replyContent");

				// 将reply语料点信息装入map容器
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
