package ims.nlp.classifier;

import weka.classifiers.Classifier;

public class WekaUseClassifierModel {

	// ��������Ҫ��·����Ϣ

	// segment
	private String segmentSourceDir;
	private String segmentTargetDir;

	// produceArff
	private String analyzedContentPath;
	private String dataRawPath;
	private String dataFilterPath;

	// saveLoadingClassifier
	private String dataArffPath;
	private String modelPath;

	public WekaUseClassifierModel(String segmentSourceDir, String segmentTargetDir,
			String analyzedContentPath, String dataRawPath,
			String dataFilterPath, String dataArffPath, String modelPath) {
		super();
		this.segmentSourceDir = segmentSourceDir;
		this.segmentTargetDir = segmentTargetDir;
		this.analyzedContentPath = analyzedContentPath;
		this.dataRawPath = dataRawPath;
		this.dataFilterPath = dataFilterPath;
		this.dataArffPath = dataArffPath;
		this.modelPath = modelPath;
	}

	/**
	 * ����arff�ļ�
	 */
	public void createArffSource() {
		// ��Դ�ļ����зִʲ��洢�ڱ��ش���
		WekaSegmentSourceFile segmentSourceFile = new WekaSegmentSourceFile(
				this.segmentSourceDir, this.segmentTargetDir);
		segmentSourceFile.analyzeFileSegment();

		// �������ķִʴ�����Դ�ļ�����arff�ļ�
		WekaProduceArffFile produceArffFile = new WekaProduceArffFile();
		produceArffFile.produceArffFile(this.analyzedContentPath,
				this.dataRawPath, this.dataFilterPath);
	}

	/**
	 * ����arff�ļ��������ѵ������ģ��
	 */
	public void trainClassifierModelWithArff() {
		WekaSaveLoadingClassifierModel saveLoadingClassifierModel = new WekaSaveLoadingClassifierModel();
		saveLoadingClassifierModel.sericalizingModel(this.dataArffPath,
				this.modelPath);
	}

	/**
	 * ��û��arff�ļ��������ѵ������ģ��
	 */
	public void trainClassifierModelWithoutArff() {

		this.createArffSource();

		WekaSaveLoadingClassifierModel saveLoadingClassifierModel = new WekaSaveLoadingClassifierModel();
		saveLoadingClassifierModel.sericalizingModel(this.dataArffPath,
				this.modelPath);
	}

	public Classifier getClassifierModel() {
		WekaSaveLoadingClassifierModel saveLoadingClassifierModel = new WekaSaveLoadingClassifierModel();
		Classifier classifier = saveLoadingClassifierModel
				.deserializingModel(this.modelPath);

		return classifier;
	}
}
