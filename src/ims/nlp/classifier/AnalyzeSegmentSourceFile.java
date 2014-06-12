package ims.nlp.classifier;

import ims.nlp.lucene.analyzer.detial.MmsegAnalyzerDetialUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * ���ļ����ڽ�ѵ���ļ��ִʹ�������������ļ�����;weka_step:01
 * 
 * @author superhy
 * 
 */
public class AnalyzeSegmentSourceFile {

	// ����Դ��ַ�ͻ��ַ
	private String sourceDir;
	private String targetDir;

	public AnalyzeSegmentSourceFile(String sourceDir, String targetDir) {
		super();
		this.sourceDir = sourceDir;
		this.targetDir = targetDir;
	}

	public void analyzeFileSegment() {
		segmentDir(this.sourceDir, this.targetDir);

		// TODO delete print
		System.out.println("�ִʽ���");
	}

	public void segmentDir(String source, String target) {
		// ���س�Դ�ļ�·�����������ļ�
		File[] files = (new File(source)).listFiles();

		for (File file : files) {

			// �ж����ļ����ļ�
			if (file.isFile()) {
				segmentFile(file.getAbsolutePath(), target + File.separator
						+ file.getName());
			}

			// �жϲ������ļ�����һ��Ŀ¼
			if (file.isDirectory()) {
				// �ݹ�DFSʵ��һ���ļ�����������Ŀ¼��ɨ��
				String _sourceDir = source + File.separator + file.getName();
				String _targetDir = target + File.separator + file.getName();

				(new File(_targetDir)).mkdirs();

				segmentDir(_sourceDir, _targetDir);
			}
		}
	}

	public void segmentFile(String sourceFile, String targetFile) {

		try {
			FileReader fr = new FileReader(sourceFile);
			BufferedReader br = new BufferedReader(fr);

			FileWriter fw = new FileWriter(targetFile);
			// BufferedWriter bw = new BufferedWriter(fw);

			// TODO delete print
			System.out.println("���ڷִ�:" + sourceFile);

			// ʹ��lucene�ִʲ������ֽ���
			// ʹ�õķִ�����mmseg4j
			Analyzer mmsegAnalyzer = new MmsegAnalyzerDetialUtil(null);
			TokenStream tokenStream = mmsegAnalyzer.tokenStream("", br);
			CharTermAttribute termAttribute = tokenStream
					.getAttribute(CharTermAttribute.class);

			while (tokenStream.incrementToken()) {
				fw.write(termAttribute + "\r\n");
			}

			// bw.close();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

}
