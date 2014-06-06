package test.retrieval.search;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ims.retrieval.search.SearchInLocalContent;

import org.junit.Test;

public class SearchInLocalContentTest {

	@Test
	public void testPhraseQuerySearcher() {
		SearchInLocalContent searchInLocalContent = new SearchInLocalContent(
				"lucene_all_index");

		String keyValue = new Scanner(System.in).next();
		List<Map<String, Object>> resMaps = searchInLocalContent
				.phraseQuerySearcher(keyValue, 1000);

		System.out.println(resMaps.size());
		for (Map<String, Object> map : resMaps) {
			System.out.println(map.toString());
		}
	}
}
