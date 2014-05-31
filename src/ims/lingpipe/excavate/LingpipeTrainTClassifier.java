package ims.lingpipe.excavate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.KnnClassifier;
import com.aliasi.tokenizer.CharacterTokenizerFactory;
import com.aliasi.tokenizer.TokenFeatureExtractor;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;

/**
 * 
 * @author superhy
 * 
 */
public class LingpipeTrainTClassifier {

	// 训练语料的文件夹
	private static File TDIR = new File("./file/train_content");
	// 测试语料的文件夹
	private static File CDIR = new File("./file/classifer_test");
	// 定义分类器的固定类别
	private static String[] CATEGORIES = { "computer", "diy", "phone", "other" };

	/**
	 * 训练
	 */
	public void trainKnnClassifier() {

		try {
			// Knn算法分类器变量（设置k值为5）
			KnnClassifier<CharSequence> classifierTrainer = new KnnClassifier<CharSequence>(
					new TokenFeatureExtractor(
							CharacterTokenizerFactory.INSTANCE), 5);
			// 开始训练
			for (int i = 0; i < CATEGORIES.length; i++) {
				File classDir = new File(TDIR, CATEGORIES[i]);
				if (!classDir.isDirectory()) {
					System.err.println("不能找到目录:" + classDir);
				}

				// 训练器遍历分类文件夹下的所有文件
				for (File file : classDir.listFiles()) {
					String text = Files.readFromFile(file, "utf-8");
					System.out.println("正在训练 " + CATEGORIES[i] + "/"
							+ file.getName());
					Classification classification = new Classification(
							CATEGORIES[i]);

					Classified<CharSequence> classified = new Classified<CharSequence>(
							text, classification);
					classifierTrainer.handle(classified);

					// System.out.println(classification.toString());
				}
			}

			// 把分类器模型写到文件上
			System.out.println("开始生成分类器");
			String modelFile = "./file/lingpipe_classifier/KnnClassifier.lp";

			// 如果目录中没有对应文件，创建之
			if (!(new File(modelFile)).exists()) {
				(new File(modelFile)).createNewFile();
			}

			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream(modelFile));
			classifierTrainer.compileTo(os);

			os.close();

			System.out.println("分类器生成完成");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("读取文件发生异常");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("写入文件发生异常");
		}
	}

	public void classifyText(String classifierName) {

		try {
			// 从本地磁盘加载二进制分类器文件
			File classifierFile = new File("./file/lingpipe_classifier/"
					+ classifierName + ".lp");

			KnnClassifier<CharSequence> compiledClassifier = (KnnClassifier<CharSequence>) AbstractExternalizable
					.readObject(classifierFile);

			File classDir = this.CDIR;
			for (File file : classDir.listFiles()) {
				String text = Files.readFromFile(file, "utf-8");

				System.out.println("分类结果：" + "/file/classifier_test/"
						+ file.getName());
				Classification classification = compiledClassifier
						.classify(text);
				// 得出最佳分类的结果
				String bestCategory = classification.bestCategory();

				System.out.println("最佳分类：" + bestCategory);

				// 得出分类操作的细节
				String details = classification.toString();

				System.out.println("分类细节：\n" + details);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
