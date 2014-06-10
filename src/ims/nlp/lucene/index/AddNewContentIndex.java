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

	// ��springע���Ӧ��mongoService
	private RetrievalMongoService retrievalMongoService;

	// mongo�е�ǰ���еļ�����
	private Set<String> collectionsName;

	public void getAllCollections() {
		setCollectionsName(this.retrievalMongoService.findAllCollectionsName());
	}

	/**
	 * ִ���߳�������������ĵ�(�����Ӧ�������ź������ĵ�ַ)
	 * 
	 * @return
	 */
	public boolean execAddContentIndexThread(String taskLogId,
			String indexAllContentPath) {

		// ���Ȼ�����м��ϵ�����
		this.getAllCollections();

		// �����̳߳�
		ExecutorService exes = Executors.newCachedThreadPool();
		Set<Future<Boolean>> setThreads = new HashSet<Future<Boolean>>();

		// Ϊÿһ�������ύһ���߳�����
		for (String collectionName : getCollectionsName()) {
			AddNewContentIndexThread addNewContentIndexThread = new AddNewContentIndexThread(
					taskLogId, collectionName, indexAllContentPath);

			setThreads.add(exes.submit(addNewContentIndexThread));
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
