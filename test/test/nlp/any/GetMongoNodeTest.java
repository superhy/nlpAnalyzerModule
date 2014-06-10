package test.nlp.any;

import java.util.List;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.mongo.service.RetrievalMongoService;

import org.junit.Test;

import com.mongodb.DBObject;

public class GetMongoNodeTest {

	@Test
	public void testGetMongoNode() {

		RetrievalMongoService retrievalMongoService = (RetrievalMongoService) ApplicationContextFactory.appContext
				.getBean("retrievalMongoService");

		DBObject postObject = retrievalMongoService.findAllPostInColl(
				"sinablog").get(0);

		List<DBObject> articleList = (List<DBObject>) postObject.get("article");
		for (DBObject articleObject : articleList) {
			System.out.println("article:"
					+ articleObject.toMap().get("articleContent"));
			List<DBObject> replyList = (List<DBObject>) articleObject
					.get("articleReply");
			for (DBObject replyObject : replyList) {
				System.out.println("reply:" + replyObject.toString());
			}
		}
	}
}
