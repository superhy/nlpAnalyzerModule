package ims.nlp.lucene.analyzer.detial;

import ims.nlp.cache.AnalyzerDataPath;
import ims.nlp.lucene.analyzer.StopAnalyzerFilterFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

public class MmsegAnalyzerDetialUtil extends Analyzer {

	// ͣ�ôʶ���
	private List<String> stopWordList;

	public MmsegAnalyzerDetialUtil(List<String> stopWordList) {

		if (stopWordList == null) {
			this.stopWordList = new ArrayList<String>();
		} else {
			this.stopWordList = stopWordList;
		}
	}

	@Override
	public TokenStream tokenStream(String arg0, Reader arg1) {
		// ���شʿ���Ϣ
		Dictionary dic = Dictionary
				.getInstance(AnalyzerDataPath.MMSEG_DIC_PATH);
		// ���طִ�����������
		MMSegTokenizer mmSegTokenizer = new MMSegTokenizer(new MaxWordSeg(dic),
				arg1);

		return new StopAnalyzerFilterFactory(this.stopWordList, mmSegTokenizer)
				.tokenStream(null, null);
	}
}
