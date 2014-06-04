package ims.retrieval.index;

import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.Set;

/**
 * 初始创建原始库中所有内容的索引
 * 
 * @author superhy
 * 
 */
public class InitAllContentIndex {

	// 从spring注入对应的mongoService
	private RetrievalMongoService retrievalMongoService;
	// mongo中当前所有的集合名
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
