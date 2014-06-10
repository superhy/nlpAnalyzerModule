package ims.nlp.lucene.analyzer;

import java.io.Reader;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;

public class StopAnalyzerFilterFactory extends Analyzer {

	// ͣ�ôʼ���
	private Set<Object> stops;

	// �ִ���������ʵ��
	private Tokenizer tokenizer;

	public StopAnalyzerFilterFactory(List<String> stopWordList, Tokenizer tokenizer) {
		// ��ʼ��ͣ�ôʼ���
		this.stops = StopFilter.makeStopSet(Version.LUCENE_35, stopWordList,
				true);
		// ��ʼ���ִ���������ʵ��
		this.tokenizer = tokenizer;
	}

	@Override
	public TokenStream tokenStream(String arg0, Reader arg1) {

		return new StopFilter(Version.LUCENE_35, this.tokenizer, this.stops);
	}
}
