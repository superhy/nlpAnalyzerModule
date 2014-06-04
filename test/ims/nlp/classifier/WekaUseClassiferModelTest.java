package ims.nlp.classifier;

import ims.nlp.classifier.WekaUseClassifierModel;

import org.junit.Before;
import org.junit.Test;

public class WekaUseClassiferModelTest {

	private WekaUseClassifierModel useClassifierModel;

	@Before
	public void loadUseClassiferModel() {
		// 设置各种路径

		// directory
		String segmentSourceDir = "./file/train_content";
		String segmentTargetDir = "./file/weka/analyzed_content";
		String analyzedContentPath = "./file/weka/analyzed_content";
		// file
		String dataRawPath = "./file/weka/arff/dataRaw.arff";
		String dataFilterPath = "./file/weka/arff/dataFilter.arff";
		String dataArffPath = "./file/weka/arff/dataFilter.arff";
		String modelPath = "./file/weka/model/KnnClassifier.model";

		WekaUseClassifierModel useClassifierModel = new WekaUseClassifierModel(
				segmentSourceDir, segmentTargetDir, analyzedContentPath,
				dataRawPath, dataFilterPath, dataArffPath, modelPath);

		setUseClassifierModel(useClassifierModel);
	}

	@Test
	public void testCreateArffSource() {
		this.useClassifierModel.createArffSource();
	}

	@Test
	public void testTrainClassifierModelWithArff() {
		this.useClassifierModel.trainClassifierModelWithArff();
	}

	@Test
	public void testTrainClassifierModelWithoutArff() {
		this.useClassifierModel.trainClassifierModelWithoutArff();
	}

	public WekaUseClassifierModel getUseClassifierModel() {
		return useClassifierModel;
	}

	public void setUseClassifierModel(WekaUseClassifierModel useClassifierModel) {
		this.useClassifierModel = useClassifierModel;
	}

}
