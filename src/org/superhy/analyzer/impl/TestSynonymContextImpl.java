package org.superhy.analyzer.impl;

import java.util.HashMap;
import java.util.Map;

import org.superhy.analyzer.SynonymContext;

public class TestSynonymContextImpl implements SynonymContext {

	Map<String, String[]> synonymMaps = new HashMap<String, String[]>();

	public TestSynonymContextImpl() {
		synonymMaps.put("电脑", new String[] { "机子", "主机" });
		synonymMaps.put("日本", new String[] { "岛国", "鬼子" });
	}

	public String[] getSynonym(String originalWord) {
		
		return synonymMaps.get(originalWord);
	}
}
