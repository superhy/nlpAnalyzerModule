package ims.nlp.lucene.index;

import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author superhy
 * 
 */
public class AddNewContentIndex {

	// 从spring注入对应的mongoService
	private RetrievalMongoService retrievalMongoService;

	// mongo中当前所有的集合名
	private Set<String> collectionsName;

	public void getAllCollections() {
		setCollectionsName(this.retrievalMongoService.findAllCollectionsName());
	}

	/**
	 * 执行线程向索引中添加文档(传入对应的任务编号和索引的地址)
	 * 
	 * @return
	 */
	public boolean execAddContentIndexThread(String taskLogId,
			String indexAllContentPath) {

		// 首先获得所有集合的名称
		this.getAllCollections();

		// 建立线程池
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();

		// 为每一个集合提交一个线程任务
		for (String collectionName : getCollectionsName()) {
			AddNewContentIndexThread addNewContentIndexThread = new AddNewContentIndexThread(
					taskLogId, collectionName, indexAllContentPath);

			setThreads.add(exes.submit(addNewContentIndexThread));
		}

		// 设置一个全部线程任务执行成功的标记
		boolean succAllflag = true;
		// 获取每个任务执行的结果
		for (Future<Boolean> future : setThreads) {

			try {
				boolean succThreadflag = future.get();
				if (succThreadflag == false) {
					succAllflag = false;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				succAllflag = false;
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				succAllflag = false;
			}
		}

		return succAllflag;
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
