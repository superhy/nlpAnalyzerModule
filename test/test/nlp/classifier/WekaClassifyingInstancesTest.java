package test.nlp.classifier;

import ims.nlp.classifier.weka.WekaClassifyingInstances;

import org.junit.Before;
import org.junit.Test;

public class WekaClassifyingInstancesTest {

	private WekaClassifyingInstances classifyingInstances;

	@Before
	public void loadUseClassiferModel() {
		// 设置各种路径

		// directory
		String segmentSourceDir = "./file/nlp_classifer_test";
		String segmentTargetDir = "./file/nlp_weka/analyzed_classifying_content";
		String analyzedContentPath = "./file/nlp_weka/analyzed_classifying_content";
		// file
		String dataRawPath = "./file/nlp_weka/classifying_arff/dataRaw.arff";
		String dataFilterPath = "./file/nlp_weka/classifying_arff/dataFilter.arff";
		String labeledDataArffPath = "./file/nlp_weka/train_arff/dataFilter.arff";
		String unlabeledDataArffPath = "./file/nlp_weka/classifying_arff/dataFilter.arff";
		String modelPath = "./file/nlp_weka/model/KnnClassifier.model";

		WekaClassifyingInstances wekaClassifyingInstances = new WekaClassifyingInstances(
				segmentSourceDir, segmentTargetDir, analyzedContentPath,
				dataRawPath, dataFilterPath, labeledDataArffPath,
				unlabeledDataArffPath, modelPath);

		setClassifyingInstances(wekaClassifyingInstances);
	}

	@Test
	public void testCreateArffSource() {
		this.classifyingInstances.createArffSource();
	}

	@Test
	public void testClassifyingInstancesWithArff() {
		this.classifyingInstances.classifyingInstancesWithArff();
	}

	@Test
	public void testClassifyingInstancesWithoutArff() {
		this.classifyingInstances.classifyingInstancesWithoutArff();
	}

	public WekaClassifyingInstances getClassifyingInstances() {
		return classifyingInstances;
	}

	public void setClassifyingInstances(
			WekaClassifyingInstances classifyingInstances) {
		this.classifyingInstances = classifyingInstances;
	}

}
