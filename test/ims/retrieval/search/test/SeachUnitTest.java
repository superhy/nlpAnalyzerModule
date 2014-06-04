package ims.retrieval.search.test;

import ims.retrieval.analyzer.DiyAnalyzerWithSynonym;
import ims.retrieval.analyzer.impl.SimpleSynonymContextImpl;
import ims.retrieval.search.SearchUnit;

import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class SeachUnitTest {

	@Test
	public void testFirstSeacher() {
		SearchUnit testObj = new SearchUnit();

		Scanner cin = new Scanner(System.in);
		String keyWord = cin.next();

		testObj.firstSearcher(keyWord);
	}

	@Test
	public void testTermSearcher() {
		SearchUnit testObj = new SearchUnit();

		Scanner cin = new Scanner(System.in);
		String field = cin.next();
		String name = cin.next();
		int num = cin.nextInt();

		testObj.termSearcher(field, name + ".txt", num);
	}

	@Test
	public void testTermRangeSearcher() {
		SearchUnit testObj = new SearchUnit();

		Scanner cin = new Scanner(System.in);
		String field = cin.next();
		String start = cin.next();
		String end = cin.next();
		int num = cin.nextInt();

		testObj.termRangeSearcher(field, start + ".txt", end + ".txt", num);
	}

	@Test
	public void testPrefixSearcher() {
		SearchUnit testObj = new SearchUnit();

		Scanner cin = new Scanner(System.in);
		String field = cin.next();
		String value = cin.next();
		int num = cin.nextInt();

		testObj.prefixSearcher(field, value, num);
	}

	@Test
	public void testWildcardSearcher() {
		SearchUnit testObj = new SearchUnit();

		Scanner cin = new Scanner(System.in);
		String field = cin.next();
		String value = cin.next();
		int num = cin.nextInt();

		testObj.wildcardSearcher(field, value, num);
	}

	@Test
	public void testFuzzySearcher() {
		SearchUnit testObj = new SearchUnit();

		Scanner cin = new Scanner(System.in);
		String field = cin.next();
		String value = cin.next();
		int num = cin.nextInt();

		testObj.fuzzySearcher(field, value, num);
	}

	@Test
	public void testQueryPaserSearcher() {
		SearchUnit testObj = new SearchUnit();

		try {

			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new StandardAnalyzer(Version.LUCENE_35));

			Scanner cin = new Scanner(System.in);
			String keyWord = cin.next();
			int num = cin.nextInt();

			Query query = parser.parse(keyWord);

			testObj.queryParserSearcher(query, num);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryPaserSearcherByMmsegIndex() {
		SearchUnit testObj = new SearchUnit();

		try {

			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new MMSegAnalyzer());

			Scanner cin = new Scanner(System.in);
			String keyWord = cin.next();
			int num = cin.nextInt();

			Query query = parser.parse(keyWord);

			testObj.queryParserSearcher(query, num);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryPaserSearcherByDiySynonymIndex() {
		SearchUnit testObj = new SearchUnit();

		try {

			QueryParser parser = new QueryParser(Version.LUCENE_35, "content",
					new DiyAnalyzerWithSynonym(new SimpleSynonymContextImpl()));

			Scanner cin = new Scanner(System.in);
			String keyWord = cin.next();
			int num = cin.nextInt();

			Query query = parser.parse(keyWord);

			testObj.queryParserSearcher(query, num);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
