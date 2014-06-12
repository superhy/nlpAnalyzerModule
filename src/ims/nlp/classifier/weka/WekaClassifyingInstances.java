package ims.nlp.classifier.weka;

import ims.nlp.classifier.AnalyzeSegmentSourceFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class WekaClassifyingInstances {

	// 配置所需要的路径信息

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
	 * 在有arff文件的情况下进行分类
	 */
	public void classifyingInstancesWithArff() {

		try {
			// 载入未分类数据的arff
			Instances unlabeled = new Instances(new BufferedReader(
					new FileReader(this.unlabeledDataArffPath)));
			// 载入训练数据arff
			Instances labeled = new Instances(new BufferedReader(
					new FileReader(this.labeledDataArffPath)));
			// 设置未分类数据arff的数据标签
			unlabeled.setClassIndex(0);
			// 设置分类数据arff的数据标签
			labeled.setClassIndex(0);

			// 载入分类模型实体
			Classifier classifier = WekaLoadingClassifierModel
					.getClassifierModel(this.modelPath);

			// TODO delete print
			System.out.println("分类模型信息：" + classifier);
			System.out.println("未标记实体数：" + unlabeled.numInstances());
			System.out.println("分类主题数：" + labeled.numClasses());

			// 得出并输出分类结果
			for (int i = 0; i < unlabeled.numInstances(); i++) {

				System.out.println("测试文档：" + i);

				// 得出最优的的分类结果
				double clsLabel = classifier.classifyInstance(unlabeled
						.instance(i));
				// 得出所有类别的分类分布
				double distLabel[] = classifier
						.distributionForInstance(unlabeled.instance(i));

				System.out.println("分类结果：" + clsLabel + "->"
						+ labeled.classAttribute().value((int) clsLabel) + "："
						+ distLabel[(int) clsLabel]);
				System.out.println("分类细节：");
				for (int j = 0; j < distLabel.length; j++) {
					System.out.println(labeled.classAttribute().value(j) + "："
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
	 * 在没有arff文件的情况下进行分类
	 */
	public void classifyingInstancesWithoutArff() {

		this.createArffSource();

		try {
			// 载入未分类数据的arff
			Instances unlabeled = new Instances(new BufferedReader(
					new FileReader(this.unlabeledDataArffPath)));
			// 载入训练数据arff
			Instances labeled = new Instances(new BufferedReader(
					new FileReader(this.labeledDataArffPath)));
			// 设置未分类数据arff的数据标签
			unlabeled.setClassIndex(0);
			// 设置分类数据arff的数据标签
			labeled.setClassIndex(0);

			// 载入分类模型实体
			Classifier classifier = WekaLoadingClassifierModel
					.getClassifierModel(this.modelPath);

			// TODO delete print
			System.out.println("分类模型信息：" + classifier);
			System.out.println("未标记实体数：" + unlabeled.numInstances());
			System.out.println("分类主题数：" + labeled.numClasses());

			// 得出并输出分类结果
			for (int i = 0; i < unlabeled.numInstances(); i++) {

				System.out.println("测试文档：" + i);

				// 得出最优的的分类结果
				double clsLabel = classifier.classifyInstance(unlabeled
						.instance(i));
				// 得出所有类别的分类分布
				double distLabel[] = classifier
						.distributionForInstance(unlabeled.instance(i));

				System.out.println("分类结果：" + clsLabel + "->"
						+ labeled.classAttribute().value((int) clsLabel) + "："
						+ distLabel[(int) clsLabel]);
				System.out.println("分类细节：");
				for (int j = 0; j < distLabel.length; j++) {
					System.out.println(labeled.classAttribute().value(j) + "："
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
