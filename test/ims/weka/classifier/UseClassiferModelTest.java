package ims.weka.classifier;

import org.junit.Before;
import org.junit.Test;

public class UseClassiferModelTest {

	private UseClassifierModel useClassifierModel;

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

		UseClassifierModel useClassifierModel = new UseClassifierModel(
				segmentSourceDir, segmentTargetDir, analyzedContentPath,
				dataRawPath, dataFilterPath, dataArffPath, modelPath);

		setUseClassifierModel(useClassifierModel);
	}

	@Test
	public void testCreateArffSource() {
		this.useClassifierModel.createArffSource();
	}

	public UseClassifierModel getUseClassifierModel() {
		return useClassifierModel;
	}

	public void setUseClassifierModel(UseClassifierModel useClassifierModel) {
		this.useClassifierModel = useClassifierModel;
	}

}
