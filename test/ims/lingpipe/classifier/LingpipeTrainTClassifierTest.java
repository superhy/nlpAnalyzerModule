package ims.lingpipe.classifier;

import ims.lingpipe.classifier.LingpipeClassifyText;
import ims.lingpipe.classifier.LingpipeTrainKnnClassifier;

import org.junit.Test;

public class LingpipeTrainTClassifierTest {

	@Test
	public void testTrainClasssifer() {
		LingpipeTrainKnnClassifier tClassifierObj = new LingpipeTrainKnnClassifier();

		tClassifierObj.trainKnnClassifier();
	}

	@Test
	public void testClassifyTest() {
		LingpipeClassifyText classifyTextObj = new LingpipeClassifyText();

		String classifierName = "KnnClassifier";
		classifyTextObj.classifyText(classifierName);
	}
}
