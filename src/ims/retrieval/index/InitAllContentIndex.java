package ims.retrieval.index;

import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.Set;

/**
 * ��ʼ����ԭʼ�����������ݵ�����
 * 
 * @author superhy
 * 
 */
public class InitAllContentIndex {

	// ��springע���Ӧ��mongoService
	private RetrievalMongoService retrievalMongoService;
	// mongo�е�ǰ���еļ�����
	private Set<String> collectionsName;

	public void getAllCollections() {
		setCollectionsName(this.retrievalMongoService.findAllCollectionsName());
	}

	public void execCreateIndexThread() {

	}

	public RetrievalMongoService getRetrievalMongoService() {
		return retrievalMongoService;
	}

	public void setRetrievalMongoService(
			RetrievalMongoService retrievalMongoService) {
		this.retrievalMongoService = retrievalMongoService;
	}

	public Set<String> getCollectionsName() {
		return collectionsName;
	}

	public void setCollectionsName(Set<String> collectionsName) {
		this.collectionsName = collectionsName;
	}

}
