package ims.nlp.classifier;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class WekaSaveLoadingClassifierModel {

	/**
	 * ���л�ģ��
	 */
	public void sericalizingModel(String dataArffPath, String modelPath) {
		try {

			// TODO delete print
			System.out.println("���ڽ�������ģ��");

			// �½�����ģ��
			Classifier classifier = new IBk(5);

			Instances instances = new Instances(new BufferedReader(
					new FileReader(dataArffPath)));
			// PS:�ֶ����ɵ�arff�ļ�������ڵ�һ�У��±�Ϊ0
			instances.setClassIndex(0);

			// ����һ��������
			classifier.buildClassifier(instances);

			// ���л�д�������
			SerializationHelper.write(modelPath, classifier);

			// TODO delete print
			System.out.println("��������ģ�ͳɹ�");

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}

	/**
	 * ���뱾�ش����ϵ�ģ��
	 */
	public Classifier deserializingModel(String modelPath) {
		try {

			// TODO delete print
			System.out.println("���ڼ��ط���ģ��");

			Classifier classifier = (Classifier) SerializationHelper
					.read(modelPath);

			// TODO delete print
			System.out.println("��������ģ�ͳɹ�");

			// ���ؼ��ڵó���classifier
			return classifier;
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();

			return null;
		}
	}
}
