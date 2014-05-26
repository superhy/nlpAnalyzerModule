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
			// Ԫ�س�ջ����ȡͬ���
			String synonymStr = synonyms.pop();

			// ��ԭ״̬
			restoreState(current);
			cta.setEmpty();
			cta.append(synonymStr);

			// ���ó�ʼλ��
			pia.setPositionIncrement(0);

			return true;
		}

		if (!this.input.incrementToken()) {
			return false;
		}

		if (addSynonyms(cta.toString())) {
			// ����ͬ��ʣ����浱ǰ״̬
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
