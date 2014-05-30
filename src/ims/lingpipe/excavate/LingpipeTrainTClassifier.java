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

public class LingpipeTrainTClassifier {

	// ѵ�����ϵ��ļ���
	private static File TDIR = new File("./file/train_content");
	// �������ϵ��ļ���
	private static File CDIR = new File("./file/classifer_test");
	// ����������Ĺ̶����
	private static String[] CATEGORIES = { "computer", "diy", "phone", "other" };

	/**
	 * ѵ��
	 */
	public void trainKnnClassifier() {

		try {
			// Knn�㷨����������������kֵΪ5��
			KnnClassifier<CharSequence> classifierTrainer = new KnnClassifier<CharSequence>(
					new TokenFeatureExtractor(
							CharacterTokenizerFactory.INSTANCE), 5);
			// ��ʼѵ��
			for (int i = 0; i < CATEGORIES.length; i++) {
				File classDir = new File(TDIR, CATEGORIES[i]);
				if (!classDir.isDirectory()) {
					System.err.println("�����ҵ�Ŀ¼:" + classDir);
				}

				// ѵ�������������ļ����µ������ļ�
				for (File file : classDir.listFiles()) {
					String text = Files.readFromFile(file, "utf-8");
					System.out.println("����ѵ�� " + CATEGORIES[i] + "/"
							+ file.getName());
					Classification classification = new Classification(
							CATEGORIES[i]);

					Classified<CharSequence> classified = new Classified<CharSequence>(
							text, classification);
					classifierTrainer.handle(classified);

					// System.out.println(classification.toString());
				}
			}

			// �ѷ�����ģ��д���ļ���
			System.out.println("��ʼ���ɷ�����");
			String modelFile = "./file/lingpipe_classifier/KnnClassifier.lp";
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream(modelFile));
			classifierTrainer.compileTo(os);

			os.close();

			System.out.println("�������������");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("��ȡ�ļ������쳣");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.err.println("д���ļ������쳣");
		}
	}

	public void classifyText(String classifierName) {

		try {
			File classifierFile = new File("./file/lingpipe_classifier/"
					+ classifierName + ".lp");

			KnnClassifier<CharSequence> compiledClassifier = (KnnClassifier<CharSequence>) AbstractExternalizable
					.readObject(classifierFile);

			File classDir = this.CDIR;
			for (File file : classDir.listFiles()) {
				String text = Files.readFromFile(file, "utf-8");

				System.out.println("��������" + "/file/classifier_test/"
						+ file.getName());
				Classification classification = compiledClassifier
						.classify(text);
				String bestCategory = classification.bestCategory();

				System.out.println("��ѷ��ࣺ" + bestCategory);

				String details = classification.toString();

				System.out.println("����ϸ�ڣ�\n" + details);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
