package ims.nlp.classifier;

import weka.classifiers.Classifier;

public class WekaUseClassifierModel {

	// 配置所需要的路径信息

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
	 * 创建arff文件
	 */
	public void createArffSource() {
		// 对源文件进行分词并存储在本地磁盘
		WekaSegmentSourceFile segmentSourceFile = new WekaSegmentSourceFile(
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
		saveLoadingClassifierModel.sericalizingModel(this.dataArffPath,
				this.modelPath);
	}

	/**
	 * 在没有arff文件的情况下训练分类模型
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
