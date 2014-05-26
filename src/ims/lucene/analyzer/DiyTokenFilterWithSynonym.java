package ims.lucene.analyzer;

import java.io.IOException;
import java.util.Stack;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

public class DiyTokenFilterWithSynonym extends TokenFilter {

	private CharTermAttribute cta = null;
	private PositionIncrementAttribute pia = null;
	private State current;
	private Stack<String> synonyms = null;
	private SynonymContext synonymContext;

	protected DiyTokenFilterWithSynonym(TokenStream input,
			SynonymContext synonymContext) {

		super(input);
		cta = this.addAttribute(CharTermAttribute.class);
		pia = this.addAttribute(PositionIncrementAttribute.class);
		synonyms = new Stack<String>();
		this.synonymContext = synonymContext;
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (synonyms.size() > 0) {
			// 元素出栈，获取同义词
			String synonymStr = synonyms.pop();

			// 还原状态
			restoreState(current);
			cta.setEmpty();
			cta.append(synonymStr);

			// 设置初始位置
			pia.setPositionIncrement(0);

			return true;
		}

		if (!this.input.incrementToken()) {
			return false;
		}

		if (addSynonyms(cta.toString())) {
			// 发现同义词，保存当前状态
			current = captureState();
		}
		
		return true;
	}

	private boolean addSynonyms(String originalWord) {

		String[] synonymWords = synonymContext.getSynonym(originalWord);

		if (synonymWords != null) {
			for (String synonymWord : synonymWords) {
				synonyms.push(synonymWord);
			}

			return true;
		}

		return false;
	}
}
