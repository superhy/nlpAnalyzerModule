package test.retrieval.search;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ims.analyze.cache.IndexDirectoryLoc;
import ims.retrieval.analyzer.detial.MmsegAnalyzerDetialUtil;
import ims.retrieval.search.SearchInLocalContent;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;

public class SearchInLocalContentTest {

	@Test
	public void testPhraseQuerySearcher() {
		SearchInLocalContent searchInLocalContent = new SearchInLocalContent(
				IndexDirectoryLoc.LUCENE_ALL_INDEX);

		String keyValue = new Scanner(System.in).next();

		// �õ��ִ���
		Analyzer analyzer = new MmsegAnalyzerDetialUtil(null);

		List<Map<String, Object>> resMaps = searchInLocalContent
				.phraseQuerySearcher(keyValue, 1000, analyzer);

		System.out.println(resMaps.size());
		for (Map<String, Object> map : resMaps) {
			System.out.println(map.toString());
		}
	}
}
