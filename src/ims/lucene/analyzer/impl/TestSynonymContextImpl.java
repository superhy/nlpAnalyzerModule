package ims.lucene.analyzer.impl;

import ims.lucene.analyzer.SynonymContext;

import java.util.HashMap;
import java.util.Map;


public class TestSynonymContextImpl implements SynonymContext {

	Map<String, String[]> synonymMaps = new HashMap<String, String[]>();

	public TestSynonymContextImpl() {
		synonymMaps.put("����", new String[] { "����", "����" });
		synonymMaps.put("�ձ�", new String[] { "����", "����" });
	}

	public String[] getSynonym(String originalWord) {
		
		return synonymMaps.get(originalWord);
	}
}
