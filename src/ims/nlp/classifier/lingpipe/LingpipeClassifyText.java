package ims.nlp.classifier.lingpipe;

import java.io.File;

import com.aliasi.classify.Classification;
import com.aliasi.classify.KnnClassifier;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;

public class LingpipeClassifyText {

	// 测试语料的文件夹
	private static File CDIR = new File("./file/nlp_weka/analyzed_classifying_content/unlabeled");

	public void classifyText(String classifierName) {
	
		try {
			// 从本地磁盘加载二进制分类器文件
			File classifierFile = new File("./file/nlp_lingpipe/classifier/"
					+ classifierName + ".lp");
	
			KnnClassifier<CharSequence> compiledClassifier = (KnnClassifier<CharSequence>) AbstractExternalizable
					.readObject(classifierFile);
	
			File classDir = this.CDIR;
			for (File file : classDir.listFiles()) {
				String text = Files.readFromFile(file, "utf-8");
	
				System.out.println("分类结果：" + "/file/nlp_classifier_test/"
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
