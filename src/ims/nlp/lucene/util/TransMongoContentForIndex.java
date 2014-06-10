package ims.nlp.lucene.util;

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

	public static Map<String, Object> producePostIndexContent(
			DBObject postObject, String collectionName) {

		// 准备返回的节点内容映射集合（为创建索引使用）
		Map<String, Object> postIndexContentMap = new HashMap<String, Object>();

		postIndexContentMap.put("collectionName", collectionName);
		postIndexContentMap.put("postUrlMD5",
				(String) postObject.get("postUrlMD5"));

		// 将节点中所有的文字内容转化成字符串
		String postAllContent = "";
		postAllContent += ((String) postObject.get("title") + "\r\n");
		// 得到这个节点中所有的article
		List<DBObject> articleList = (List<DBObject>) postObject.get("article");
		if (articleList != null) {
			for (DBObject articleObject : articleList) {
				String articleContent = (String) articleObject
						.get("articleContent");
				postAllContent += (articleContent + "\r\n");

				// 得到一个article中所有的reply元素
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
		// 将postAllContent添加入节点内容映射集合
		postIndexContentMap.put("content", postAllContent);

		return postIndexContentMap;
	}
}
