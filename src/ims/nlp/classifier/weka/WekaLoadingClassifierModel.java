package ims.nlp.classifier.weka;

import weka.classifiers.Classifier;

/**
 * 
 * @author superhy
 * 
 */
public class WekaLoadingClassifierModel {

	/**
	 * 从本地磁盘载入分类模型
	 * 
	 * @param modelPath
	 * @return
	 */
	public static Classifier getClassifierModel(String modelPath) {
		WekaSaveLoadingClassifierModel saveLoadingClassifierModel = new WekaSaveLoadingClassifierModel();
		Classifier classifier = saveLoadingClassifierModel
				.deserializingModel(modelPath);

		return classifier;
	}
}
