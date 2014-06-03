package ims.weka.classifier;

import weka.classifiers.Classifier;

public class UseClassifierModel {

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

	public UseClassifierModel(String segmentSourceDir, String segmentTargetDir,
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

	public void createArffSource() {
		// 对源文件进行分词并存储在本地磁盘
		SegmentSourceFile segmentSourceFile = new SegmentSourceFile(
				this.segmentSourceDir, this.segmentTargetDir);
		segmentSourceFile.analyzeFileSegment();

		// 根据中文分词处理后的源文件创建arff文件
		ProduceArffFile produceArffFile = new ProduceArffFile();
		produceArffFile.produceArffFile(this.analyzedContentPath,
				this.dataRawPath, this.dataFilterPath);
	}

	public void trainClassifierModelWithArff() {
		SaveLoadingClassifierModel saveLoadingClassifierModel = new SaveLoadingClassifierModel();
		saveLoadingClassifierModel.sericalizingModel(this.dataArffPath,
				this.modelPath);
	}

	public void trainClassifierModelWithoutArff() {

		this.createArffSource();

		SaveLoadingClassifierModel saveLoadingClassifierModel = new SaveLoadingClassifierModel();
		saveLoadingClassifierModel.sericalizingModel(this.dataArffPath,
				this.modelPath);
	}

	public Classifier getClassifierModel() {
		SaveLoadingClassifierModel saveLoadingClassifierModel = new SaveLoadingClassifierModel();
		Classifier classifier = saveLoadingClassifierModel
				.deserializingModel(this.modelPath);

		return classifier;
	}
}
