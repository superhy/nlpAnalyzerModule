package test.retrieval.any;

import java.util.List;

import ims.nlp.mongo.MongoDbBean;

import org.junit.Test;

import com.mongodb.DBObject;

public class MongoObjectToStringTest {

	@Test
	public void testTransDBObjectToString() {
		MongoDbBean mongoDbBean = MongoDbBean.getMongoDbBean();

		List<DBObject> postoObjects = mongoDbBean.findByKeyAndRef(null, null,
				"doubangroup");
		DBObject postObject = postoObjects.get(0);
		List<DBObject> articleList = (List<DBObject>) postObject.get("article");

		for (DBObject dbObject : articleList) {
			System.out.println(dbObject.toString());
		}

	}
}
