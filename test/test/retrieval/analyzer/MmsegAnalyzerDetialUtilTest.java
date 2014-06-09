package test.retrieval.analyzer;

import java.util.ArrayList;
import java.util.List;

import ims.retrieval.analyzer.detial.MmsegAnalyzerDetialUtil;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;

public class MmsegAnalyzerDetialUtilTest {

	@Test
	public void testAnalyzer() {

		List<String> stopWordList = new ArrayList<String>();
		stopWordList.add("是");
		stopWordList.add("的");
		stopWordList.add("了");

		Analyzer a1 = new MmsegAnalyzerDetialUtil(null);
		String txt = "中华人民共和国";
		AnalyzerUtils.displayToken(txt, a1);
	}
}
