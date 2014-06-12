package ims.nlp.classifier.lingpipe;

import java.io.File;

import com.aliasi.classify.Classification;
import com.aliasi.classify.KnnClassifier;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Files;

public class LingpipeClassifyText {

	// �������ϵ��ļ���
	private static File CDIR = new File("./file/nlp_weka/analyzed_classifying_content/unlabeled");

	public void classifyText(String classifierName) {
	
		try {
			// �ӱ��ش��̼��ض����Ʒ������ļ�
			File classifierFile = new File("./file/nlp_lingpipe/classifier/"
					+ classifierName + ".lp");
	
			KnnClassifier<CharSequence> compiledClassifier = (KnnClassifier<CharSequence>) AbstractExternalizable
					.readObject(classifierFile);
	
			File classDir = this.CDIR;
			for (File file : classDir.listFiles()) {
				String text = Files.readFromFile(file, "utf-8");
	
				System.out.println("��������" + "/file/nlp_classifier_test/"
						+ file.getName());
				Classification classification = compiledClassifier
						.classify(text);
				// �ó���ѷ���Ľ��
				String bestCategory = classification.bestCategory();
	
				System.out.println("��ѷ��ࣺ" + bestCategory);
	
				// �ó����������ϸ��
				String details = classification.toString();
	
				System.out.println("����ϸ�ڣ�\n" + details);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
