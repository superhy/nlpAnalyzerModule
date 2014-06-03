package ims.weka.classifier;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class SaveLoadingClassifierModel {

	/**
	 * 序列化模型
	 */
	public void sericalizingModel(String dataArffPath, String modelPath) {
		try {
			// 新建分类模型
			Classifier classifier = new IBk(5);

			Instances instances = new Instances(new BufferedReader(
					new FileReader(dataArffPath)));
			// PS:手动生成的arff文件类别是在第一行，下标为0
			instances.setClassIndex(0);

			// 建立一个分类器
			classifier.buildClassifier(instances);

			// 序列化写入分类器
			SerializationHelper.write(modelPath, classifier);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	/**
	 * 载入本地磁盘上的模型
	 */
	public Classifier deserializingModel(String modelPath) {
		try {
			Classifier classifier = (Classifier) SerializationHelper
					.read(modelPath);

			// 返回加在得出的classifier
			return classifier;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			return null;
		}
	}
}
