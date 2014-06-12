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
 * 本文件用于将训练文件分词过后存入其他的文件夹中;weka_step:01
 * 
 * @author superhy
 * 
 */
public class AnalyzeSegmentSourceFile {

	// 设置源地址和汇地址
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
		System.out.println("分词结束");
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
			// BufferedWriter bw = new BufferedWriter(fw);

			// TODO delete print
			System.out.println("正在分词:" + sourceFile);

			// 使用lucene分词并创建字节流
			// 使用的分词器是mmseg4j
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
