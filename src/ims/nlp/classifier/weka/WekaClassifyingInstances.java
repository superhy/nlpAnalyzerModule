package ims.nlp.classifier.weka;

import ims.nlp.classifier.AnalyzeSegmentSourceFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class WekaClassifyingInstances {

	// ��������Ҫ��·����Ϣ

	// segment
	private String segmentSourceDir;
	private String segmentTargetDir;

	// produceArff
	private String analyzedContentPath;
	private String dataRawPath;
	private String dataFilterPath;

	// classifying instances
	private String labeledDataArffPath;
	private String unlabeledDataArffPath;
	private String modelPath;

	// TODO classifying result

	public WekaClassifyingInstances(String segmentSourceDir,
			String segmentTargetDir, String analyzedContentPath,
			String dataRawPath, String dataFilterPath,
			String labeledDataArffPath, String unlabeledDataArffPath,
			String modelPath) {
		super();
		this.segmentSourceDir = segmentSourceDir;
		this.segmentTargetDir = segmentTargetDir;
		this.analyzedContentPath = analyzedContentPath;
		this.dataRawPath = dataRawPath;
		this.dataFilterPath = dataFilterPath;
		this.labeledDataArffPath = labeledDataArffPath;
		this.unlabeledDataArffPath = unlabeledDataArffPath;
		this.modelPath = modelPath;
	}

	// TODO construction method with classifying result entity

	/**
	 * ����arff�ļ�
	 */
	public void createArffSource() {
		// ��Դ�ļ����зִʲ��洢�ڱ��ش���
		AnalyzeSegmentSourceFile segmentSourceFile = new AnalyzeSegmentSourceFile(
				this.segmentSourceDir, this.segmentTargetDir);
		segmentSourceFile.analyzeFileSegment();

		// �������ķִʴ�����Դ�ļ�����arff�ļ�
		WekaProduceArffFile produceArffFile = new WekaProduceArffFile();
		produceArffFile.produceArffFile(this.analyzedContentPath,
				this.dataRawPath, this.dataFilterPath);
	}

	/**
	 * ����arff�ļ�������½��з���
	 */
	public void classifyingInstancesWithArff() {

		try {
			// ����δ�������ݵ�arff
			Instances unlabeled = new Instances(new BufferedReader(
					new FileReader(this.unlabeledDataArffPath)));
			// ����ѵ������arff
			Instances labeled = new Instances(new BufferedReader(
					new FileReader(this.labeledDataArffPath)));
			// ����δ��������arff�����ݱ�ǩ
			unlabeled.setClassIndex(0);
			// ���÷�������arff�����ݱ�ǩ
			labeled.setClassIndex(0);

			// �������ģ��ʵ��
			Classifier classifier = WekaLoadingClassifierModel
					.getClassifierModel(this.modelPath);

			// TODO delete print
			System.out.println("����ģ����Ϣ��" + classifier);
			System.out.println("δ���ʵ������" + unlabeled.numInstances());
			System.out.println("������������" + labeled.numClasses());

			// �ó������������
			for (int i = 0; i < unlabeled.numInstances(); i++) {

				System.out.println("�����ĵ���" + i);

				// �ó����ŵĵķ�����
				double clsLabel = classifier.classifyInstance(unlabeled
						.instance(i));
				// �ó��������ķ���ֲ�
				double distLabel[] = classifier
						.distributionForInstance(unlabeled.instance(i));

				System.out.println("��������" + clsLabel + "->"
						+ labeled.classAttribute().value((int) clsLabel) + "��"
						+ distLabel[(int) clsLabel]);
				System.out.println("����ϸ�ڣ�");
				for (int j = 0; j < distLabel.length; j++) {
					System.out.println(labeled.classAttribute().value(j) + "��"
							+ distLabel[j]);
				}
				System.out.println("\n");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��û��arff�ļ�������½��з���
	 */
	public void classifyingInstancesWithoutArff() {

		this.createArffSource();

		try {
			// ����δ�������ݵ�arff
			Instances unlabeled = new Instances(new BufferedReader(
					new FileReader(this.unlabeledDataArffPath)));
			// ����ѵ������arff
			Instances labeled = new Instances(new BufferedReader(
					new FileReader(this.labeledDataArffPath)));
			// ����δ��������arff�����ݱ�ǩ
			unlabeled.setClassIndex(0);
			// ���÷�������arff�����ݱ�ǩ
			labeled.setClassIndex(0);

			// �������ģ��ʵ��
			Classifier classifier = WekaLoadingClassifierModel
					.getClassifierModel(this.modelPath);

			// TODO delete print
			System.out.println("����ģ����Ϣ��" + classifier);
			System.out.println("δ���ʵ������" + unlabeled.numInstances());
			System.out.println("������������" + labeled.numClasses());

			// �ó������������
			for (int i = 0; i < unlabeled.numInstances(); i++) {

				System.out.println("�����ĵ���" + i);

				// �ó����ŵĵķ�����
				double clsLabel = classifier.classifyInstance(unlabeled
						.instance(i));
				// �ó��������ķ���ֲ�
				double distLabel[] = classifier
						.distributionForInstance(unlabeled.instance(i));

				System.out.println("��������" + clsLabel + "->"
						+ labeled.classAttribute().value((int) clsLabel) + "��"
						+ distLabel[(int) clsLabel]);
				System.out.println("����ϸ�ڣ�");
				for (int j = 0; j < distLabel.length; j++) {
					System.out.println(labeled.classAttribute().value(j) + "��"
							+ distLabel[j]);
				}
				System.out.println("\n");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSegmentSourceDir() {
		return segmentSourceDir;
	}

	public void setSegmentSourceDir(String segmentSourceDir) {
		this.segmentSourceDir = segmentSourceDir;
	}

	public String getSegmentTargetDir() {
		return segmentTargetDir;
	}

	public void setSegmentTargetDir(String segmentTargetDir) {
		this.segmentTargetDir = segmentTargetDir;
	}

	public String getAnalyzedContentPath() {
		return analyzedContentPath;
	}

	public void setAnalyzedContentPath(String analyzedContentPath) {
		this.analyzedContentPath = analyzedContentPath;
	}

	public String getDataRawPath() {
		return dataRawPath;
	}

	public void setDataRawPath(String dataRawPath) {
		this.dataRawPath = dataRawPath;
	}

	public String getDataFilterPath() {
		return dataFilterPath;
	}

	public void setDataFilterPath(String dataFilterPath) {
		this.dataFilterPath = dataFilterPath;
	}

	public String getLabeledDataArffPath() {
		return labeledDataArffPath;
	}

	public void setLabeledDataArffPath(String labeledDataArffPath) {
		this.labeledDataArffPath = labeledDataArffPath;
	}

	public String getUnlabeledDataArffPath() {
		return unlabeledDataArffPath;
	}

	public void setUnlabeledDataArffPath(String unlabeledDataArffPath) {
		this.unlabeledDataArffPath = unlabeledDataArffPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}
