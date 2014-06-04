package ims.retrieval.index.test;

import ims.retrieval.index.IndexUnit;

import org.junit.Test;

public class IndexUnitTest {

	@Test
	public void testCreateStandardIndex() throws Exception {
		IndexUnit testObj = new IndexUnit();

		testObj.createStandardIndex();
	}

	@Test
	public void testCreateMmsegIndex() {
		IndexUnit testObj = new IndexUnit();

		testObj.createMmsegIndex();
	}

	@Test
	public void testCreateDiySynonymIndex() {
		IndexUnit testObj = new IndexUnit();

		testObj.createDiySynonymIndex();
	}
	
	@Test
	public void testCreateMmsegIndexByTika() {
		IndexUnit testObj = new IndexUnit();
		
		testObj.createMmsegIndexByTika();
	}

	@Test
	public void testQueryIndex() {
		IndexUnit testObj = new IndexUnit();

		testObj.queryIndex();
	}

	@Test
	public void testDeleteIndex() {
		IndexUnit testObj = new IndexUnit();

		testObj.deleteIndex();
	}

	@Test
	public void testForceMerge() {
		IndexUnit testObj = new IndexUnit();

		testObj.forceMergeIndex();
	}
}
