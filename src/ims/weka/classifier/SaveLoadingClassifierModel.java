package ims.weka.classifier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class SaveLoadingClassifierModel {

	/**
	 * 序列化模型
	 */
	public void sericalizingModel(String dateArffPath, String modelPath) {
		try {
			// 新建分类模型
			Classifier classifier = new IBk(5);

			Instances instances = new Instances(new BufferedReader(
					new FileReader(dateArffPath)));
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
	public void deserializingModel(String modelPath) {
		try {
			Classifier classifier = (Classifier) SerializationHelper
					.read(modelPath);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}
}
