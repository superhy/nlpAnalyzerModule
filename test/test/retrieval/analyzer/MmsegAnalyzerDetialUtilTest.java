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
		stopWordList.add("��");
		stopWordList.add("��");
		stopWordList.add("��");

		Analyzer a1 = new MmsegAnalyzerDetialUtil(null);
		String txt = "�л����񹲺͹�";
		AnalyzerUtils.displayToken(txt, a1);
	}
}
