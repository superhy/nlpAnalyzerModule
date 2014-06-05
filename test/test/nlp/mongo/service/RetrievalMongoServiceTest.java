package test.nlp.mongo.service;

import java.util.Set;

import ims.analyze.cache.ApplicationContextFactory;
import ims.analyze.mongo.service.RetrievalMongoService;

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
