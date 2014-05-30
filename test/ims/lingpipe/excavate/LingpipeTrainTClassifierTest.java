package ims.lingpipe.excavate;

import org.junit.Test;

public class LingpipeTrainTClassifierTest {

	@Test
	public void testTrainClasssifer() {
		LingpipeTrainTClassifier tClassifierObj = new LingpipeTrainTClassifier();

		tClassifierObj.trainKnnClassifier();
	}

	@Test
	public void testClassifyTest() {
		LingpipeTrainTClassifier tClassifierObj = new LingpipeTrainTClassifier();

		String classifierName = "KnnClassifier";
		tClassifierObj.classifyText(classifierName);
	}
}
