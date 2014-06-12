package ims.nlp.classifier.weka;

import ims.nlp.classifier.AnalyzeSegmentSourceFile;

/**
 * 
 * @author superhy
 * 
 */
public class WekaProduceClassifierModel {

	// ��������Ҫ��·����Ϣ

	// segment
	private String segmentSourceDir;
	private String segmentTargetDir;

	// produceArff
	private String analyzedContentPath;
	private String dataRawPath;
	private String dataFilterPath;

	// saveLoadingClassifier
	private String trainDataArffPath;
	private String modelPath;

	public WekaProduceClassifierModel(String segmentSourceDir,
			String segmentTargetDir, String analyzedContentPath,
			String dataRawPath, String dataFilterPath,
			String trainDataArffPath, String modelPath) {
		super();
		this.segmentSourceDir = segmentSourceDir;
		this.segmentTargetDir = segmentTargetDir;
		this.analyzedContentPath = analyzedContentPath;
		this.dataRawPath = dataRawPath;
		this.dataFilterPath = dataFilterPath;
		this.trainDataArffPath = trainDataArffPath;
		this.modelPath = modelPath;
	}

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
	 * ����arff�ļ��������ѵ������ģ��
	 */
	public void trainClassifierModelWithArff() {
		WekaSaveLoadingClassifierModel saveLoadingClassifierModel = new WekaSaveLoadingClassifierModel();
		saveLoadingClassifierModel.sericalizingModel(this.trainDataArffPath,
				this.modelPath);
	}

	/**
	 * ��û��arff�ļ��������ѵ������ģ��
	 */
	public void trainClassifierModelWithoutArff() {

		this.createArffSource();

		WekaSaveLoadingClassifierModel saveLoadingClassifierModel = new WekaSaveLoadingClassifierModel();
		saveLoadingClassifierModel.sericalizingModel(this.trainDataArffPath,
				this.modelPath);
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

	public String getTrainDataArffPath() {
		return trainDataArffPath;
	}

	public void setTrainDataArffPath(String trainDataArffPath) {
		this.trainDataArffPath = trainDataArffPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}
