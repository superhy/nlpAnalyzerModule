package ims.nlp.classifier.weka;

import ims.nlp.classifier.AnalyzeSegmentSourceFile;

/**
 * 
 * @author superhy
 * 
 */
public class WekaProduceClassifierModel {

	// 配置所需要的路径信息

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
	 * 创建arff文件
	 */
	public void createArffSource() {
		// 对源文件进行分词并存储在本地磁盘
		AnalyzeSegmentSourceFile segmentSourceFile = new AnalyzeSegmentSourceFile(
				this.segmentSourceDir, this.segmentTargetDir);
		segmentSourceFile.analyzeFileSegment();

		// 根据中文分词处理后的源文件创建arff文件
		WekaProduceArffFile produceArffFile = new WekaProduceArffFile();
		produceArffFile.produceArffFile(this.analyzedContentPath,
				this.dataRawPath, this.dataFilterPath);
	}

	/**
	 * 在有arff文件的情况下训练分类模型
	 */
	public void trainClassifierModelWithArff() {
		WekaSaveLoadingClassifierModel saveLoadingClassifierModel = new WekaSaveLoadingClassifierModel();
		saveLoadingClassifierModel.sericalizingModel(this.trainDataArffPath,
				this.modelPath);
	}

	/**
	 * 在没有arff文件的情况下训练分类模型
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
