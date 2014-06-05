package ims.retrieval.index;

import ims.nlp.mongo.service.RetrievalMongoService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	/**
	 * ִ���߳�д������
	 */
	public boolean execCreateIndexThread() {

		// ���Ȼ�����м��ϵ�����
		this.getAllCollections();

		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();

		// Ϊÿһ�������ύһ���߳�����
		for (String collectionName : getCollectionsName()) {
			InitContentIndexThread initContentIndexThread = new InitContentIndexThread(
					collectionName);

			setThreads.add(exes.submit(initContentIndexThread));
		}

		// ����һ��ȫ���߳�����ִ�гɹ��ı��
		boolean succAllflag = true;
		// ��ȡÿ������ִ�еĽ��
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
