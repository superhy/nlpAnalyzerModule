package test.nlp.mongo.service;

import java.util.Set;

import ims.crawler.cache.ApplicationContextFactory;
import ims.nlp.mongo.service.RetrievalMongoService;

import org.junit.Test;

public class RetrievalMongoServiceTest {

	@Test
	public void testFindAllCollectionsName() {
		RetrievalMongoService retrievalMongoService = (RetrievalMongoService) ApplicationContextFactory.appContext
				.getBean("retrievalMongoService");

		Set<String> res = retrievalMongoService.findAllCollectionsName();

		for (String string : res) {
			System.out.println(string);
		}
	}
}
