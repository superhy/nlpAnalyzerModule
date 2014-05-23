package org.superhy.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

/**
 * ����ͬ��ʹ��ܵķִ���
 * 
 * @author superhy
 * 
 */
public class DiyAnalyzerWithSynonym extends Analyzer {

	private SynonymContext synonymContext;

	public DiyAnalyzerWithSynonym(SynonymContext sc) {
		synonymContext = sc;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		Dictionary dic = Dictionary.getInstance();
		// ʹ�����ķִ�����ͬ���ʵ���ഴ���Զ���filter
		TokenFilter diyFilter = new DiyTokenFilterWithSynonym(
				new MMSegTokenizer(new MaxWordSeg(dic), reader),
				this.synonymContext);

		return diyFilter;
	}
}
