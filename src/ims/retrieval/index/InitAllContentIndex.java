package ims.retrieval.index;

import ims.nlp.mongo.service.RetrievalMongoService;

/**
 * 初始创建原始库中所有内容的索引
 * 
 * @author superhy
 * 
 */
public class InitAllContentIndex {

	private RetrievalMongoService retrievalMongoService;
	
	public void execCreateIndexThread() {
		// TODO continue
	}

	public RetrievalMongoService getRetrievalMongoService() {
		return retrievalMongoService;
	}

	public void setRetrievalMongoService(
			RetrievalMongoService retrievalMongoService) {
		this.retrievalMongoService = retrievalMongoService;
	}

}
