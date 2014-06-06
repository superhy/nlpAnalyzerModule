package test.retrieval.index;

import ims.analyze.cache.ApplicationContextFactory;
import ims.retrieval.index.InitAllContentIndex;

public class InitAllContentIndexTest {

	public static String formatDuring(long mss) {
		long hours = mss / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return (hours / 10 == 0 ? "0" : "") + hours + ":"
				+ (minutes / 10 == 0 ? "0" : "") + minutes + ":"
				+ (seconds / 10 == 0 ? "0" : "") + seconds;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitAllContentIndex initAllContentIndex = (InitAllContentIndex) ApplicationContextFactory.appContext
				.getBean("initAllContentIndex");

		// 记录开始时间
		long startTime = System.currentTimeMillis();

		initAllContentIndex.execCreateIndexThread();

		// 记录结束时间
		long endTime = System.currentTimeMillis();

		String costTime = formatDuring(endTime - startTime);
		System.out.println(costTime);
	}

}
