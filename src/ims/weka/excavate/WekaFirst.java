package ims.weka.excavate;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class WekaFirst {

	public void wekaUse() throws Throwable {
		Classifier m_classifier = new J48();

		// ѵ�������ļ�
		File inputFile = new File(
				"D://Weka-3-6//data//cpu.with.vendor.arff");
		ArffLoader atf = new ArffLoader();
		atf.setFile(inputFile);

		// ����ѵ���ļ�
		Instances instancesTrain = atf.getDataSet();
		// ���������ļ�
		inputFile = new File(
				"D://Weka-3-6//data//cpu.with.vendor.arff");
		atf.setFile(inputFile);

		// ��������ļ�
		Instances instancesTest = atf.getDataSet();
		instancesTest.setClassIndex(0); // ���÷������������кţ���һ��Ϊ0�ţ���instancesTest.numAttributes()����ȡ����������
		double sum = instancesTest.numInstances(), // ��������ʵ����
		right = 0.0f;
		instancesTrain.setClassIndex(0);

		m_classifier.buildClassifier(instancesTrain); // ѵ��
		for (int i = 0; i < sum; i++)// ���Է�����
		{
			if (m_classifier.classifyInstance(instancesTest.instance(i)) == instancesTest
					.instance(i).classValue())// ���Ԥ��ֵ�ʹ�ֵ��ȣ����������еķ������ṩ����Ϊ��ȷ�𰸣�����������壩
			{
				right++;// ��ȷֵ��1
			}
		}
		System.out.println("J48 classification precision:" + (right / sum));
	}
}
