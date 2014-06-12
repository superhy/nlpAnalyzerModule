package test.nlp.classifier;

import ims.nlp.classifier.lingpipe.LingpipeClassifyText;
import ims.nlp.classifier.lingpipe.LingpipeTrainKnnClassifier;

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
