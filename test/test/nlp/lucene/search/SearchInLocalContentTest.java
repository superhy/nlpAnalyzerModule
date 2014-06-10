package test.nlp.lucene.search;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ims.nlp.cache.IndexDirectoryLoc;
import ims.nlp.lucene.analyzer.detial.MmsegAnalyzerDetialUtil;
import ims.nlp.lucene.search.SearchInLocalContent;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;

public class SearchInLocalContentTest {

	@Test
	public void testPhraseQuerySearcher() {
		SearchInLocalContent searchInLocalContent = new SearchInLocalContent(
				IndexDirectoryLoc.LUCENE_ALL_INDEX);

		String keyValue = new Scanner(System.in).next();

		// µÃµ½·Ö´ÊÆ÷
		Analyzer analyzer = new MmsegAnalyzerDetialUtil(null);

		List<Map<String, Object>> resMaps = searchInLocalContent
				.phraseQuerySearcher(keyValue, 1000, analyzer);

		System.out.println(resMaps.size());
		for (Map<String, Object> map : resMaps) {
			System.out.println(map.toString());
		}
	}
}
