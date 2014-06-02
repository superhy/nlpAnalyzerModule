package ims.weka.classifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * ���ļ����ڽ�ѵ���ļ��ִʹ�������������ļ�����;step:01
 * 
 * @author superhy
 * 
 */
public class SegmentSourceFile {

	// ����Դ��ַ�ͻ��ַ
	private String sourceDir;
	private String targetDir;

	public SegmentSourceFile(String sourceDir, String targetDir) {
		super();
		this.sourceDir = sourceDir;
		this.targetDir = targetDir;
	}

	public void trainFileSegment() {
		segmentDir(this.sourceDir, this.targetDir);
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
			BufferedWriter bw = new BufferedWriter(fw);

			// ʹ��lucene�ִʲ������ֽ���
			// ʹ�õķִ�����mmseg4j
			Analyzer mmsegAnalyzer = new MMSegAnalyzer();
			TokenStream tokenStream = mmsegAnalyzer.tokenStream("", br);
			CharTermAttribute termAttribute = (CharTermAttribute) tokenStream
					.getAttribute(CharTermAttribute.class);

			while (tokenStream.incrementToken()) {
				bw.write(termAttribute.buffer());
				bw.write(' ');
			}

			fw.close();
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
