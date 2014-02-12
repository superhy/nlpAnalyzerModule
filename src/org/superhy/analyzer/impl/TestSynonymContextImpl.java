package org.superhy.analyzer.impl;

import java.util.HashMap;
import java.util.Map;

import org.superhy.analyzer.SynonymContext;

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
