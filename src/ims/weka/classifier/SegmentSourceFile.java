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
 * 本文件用于将训练文件分词过后存入其他的文件夹中;step:01
 * 
 * @author superhy
 * 
 */
public class SegmentSourceFile {

	// 设置源地址和汇地址
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
		// 加载出源文件路径中所有子文件
		File[] files = (new File(source)).listFiles();

		for (File file : files) {
			// 判断子文件是文件
			if (file.isFile()) {
				segmentFile(file.getAbsolutePath(), target + File.separator
						+ file.getName());
			}

			// 判断不是子文件而是一个目录
			if (file.isDirectory()) {
				// 递归DFS实现一个文件夹中所有子目录的扫描
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

			// 使用lucene分词并创建字节流
			// 使用的分词器是mmseg4j
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
