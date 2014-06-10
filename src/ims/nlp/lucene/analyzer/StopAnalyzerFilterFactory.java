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

	// 停用词集合
	private Set<Object> stops;

	// 分词器数据流实体
	private Tokenizer tokenizer;

	public StopAnalyzerFilterFactory(List<String> stopWordList, Tokenizer tokenizer) {
		// 初始化停用词集合
		this.stops = StopFilter.makeStopSet(Version.LUCENE_35, stopWordList,
				true);
		// 初始化分词器数据流实体
		this.tokenizer = tokenizer;
	}

	@Override
	public TokenStream tokenStream(String arg0, Reader arg1) {

		return new StopFilter(Version.LUCENE_35, this.tokenizer, this.stops);
	}
}
