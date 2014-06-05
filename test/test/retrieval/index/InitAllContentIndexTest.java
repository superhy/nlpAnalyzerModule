package test.retrieval.index;

import ims.nlp.cache.ApplicationContextFactory;
import ims.retrieval.index.InitAllContentIndex;

public class InitAllContentIndexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitAllContentIndex initAllContentIndex = (InitAllContentIndex) ApplicationContextFactory.appContext
				.getBean("initAllContentIndex");

		initAllContentIndex.execCreateIndexThread();
	}

}
