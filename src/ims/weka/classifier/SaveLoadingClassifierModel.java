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
	 * ���л�ģ��
	 */
	public void sericalizingModel(String dateArffPath, String modelPath) {
		try {
			// �½�����ģ��
			Classifier classifier = new IBk(5);

			Instances instances = new Instances(new BufferedReader(
					new FileReader(dateArffPath)));
			// PS:�ֶ����ɵ�arff�ļ�������ڵ�һ�У��±�Ϊ0
			instances.setClassIndex(0);

			// ����һ��������
			classifier.buildClassifier(instances);

			// ���л�д�������
			SerializationHelper.write(modelPath, classifier);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	/**
	 * ���뱾�ش����ϵ�ģ��
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
