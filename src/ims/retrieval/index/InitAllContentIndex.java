package ims.retrieval.index;

import ims.nlp.mongo.service.RetrievalMongoService;

/**
 * ��ʼ����ԭʼ�����������ݵ�����
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
