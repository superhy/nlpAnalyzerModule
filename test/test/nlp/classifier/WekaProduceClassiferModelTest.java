package test.nlp.classifier;

import ims.nlp.classifier.weka.WekaProduceClassifierModel;

import org.junit.Before;
import org.junit.Test;

public class WekaProduceClassiferModelTest {

	private WekaProduceClassifierModel produceClassifierModel;

	@Before
	public void loadUseClassiferModel() {
		// 设置各种路径

		// directory
		String segmentSourceDir = "./file/nlp_train_content";
		String segmentTargetDir = "./file/nlp_weka/analyzed_train_content";
		String analyzedContentPath = "./file/nlp_weka/analyzed_train_content";
		// file
		String dataRawPath = "./file/nlp_weka/train_arff/dataRaw.arff";
		String dataFilterPath = "./file/nlp_weka/train_arff/dataFilter.arff";
		String dataArffPath = "./file/nlp_weka/train_arff/dataFilter.arff";
		String modelPath = "./file/nlp_weka/model/KnnClassifier.model";

		WekaProduceClassifierModel useClassifierModel = new WekaProduceClassifierModel(
				segmentSourceDir, segmentTargetDir, analyzedContentPath,
				dataRawPath, dataFilterPath, dataArffPath, modelPath);

		setUseClassifierModel(useClassifierModel);
	}

	@Test
	public void testCreateArffSource() {
		this.produceClassifierModel.createArffSource();
	}

	@Test
	public void testTrainClassifierModelWithArff() {
		this.produceClassifierModel.trainClassifierModelWithArff();
	}

	@Test
	public void testTrainClassifierModelWithoutArff() {
		this.produceClassifierModel.trainClassifierModelWithoutArff();
	}

	public WekaProduceClassifierModel getUseClassifierModel() {
		return produceClassifierModel;
	}

	public void setUseClassifierModel(WekaProduceClassifierModel useClassifierModel) {
		this.produceClassifierModel = useClassifierModel;
	}

}
