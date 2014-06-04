package ims.retrieval.analyzer.impl;

import ims.retrieval.analyzer.SynonymContext;

import java.util.HashMap;
import java.util.Map;


public class SimpleSynonymContextImpl implements SynonymContext {

	Map<String, String[]> synonymMaps = new HashMap<String, String[]>();

	public SimpleSynonymContextImpl() {
		synonymMaps.put("����", new String[] { "����", "����" });
		synonymMaps.put("�ձ�", new String[] { "����", "����" });
	}

	public String[] getSynonym(String originalWord) {
		
		return synonymMaps.get(originalWord);
	}
}
