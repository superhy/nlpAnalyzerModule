package test.retrieval.search;

import java.util.Scanner;

import ims.retrieval.search.SearchInLocalContent;

import org.junit.Test;

public class SearchInLocalContentTest {

	@Test
	public void testPhraseQuerySearcher() {
		SearchInLocalContent searchInLocalContent = new SearchInLocalContent("lucene_all_index");
		
		String keyValue = new Scanner(System.in).next();
		searchInLocalContent.phraseQuerySearcher(keyValue, 100);
	}
}
