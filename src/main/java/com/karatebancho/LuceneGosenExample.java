package com.karatebancho;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ibm.icu.text.Transliterator;

import net.java.sen.SenFactory;
import net.java.sen.StreamTagger;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Morpheme;
import net.java.sen.dictionary.Token;

/**
 * Hello world!
 * 
 */
public class LuceneGosenExample {
	protected static Transliterator trans = Transliterator.getInstance("Katakana-Latin");

	public static void main(String[] args) throws Exception {
		LuceneGosenExample obj = new LuceneGosenExample();
		String readings = obj.getReadings(args[1]);
		String latin = trans.transliterate(readings);
		System.out.format("検索キーワード: %s(%s)%n",args[1], latin);
		Map<String, String> dic = obj.createDictionary(args);
		for (String key : dic.keySet()) {
			int distance = StringUtils.getLevenshteinDistance(
					latin, key);
			// 変換ミスなど、辞書にある言葉ベースであれば
			if (distance < readings.length() / 2) {
				System.out.format("%s:%s (%d)%n", key, dic.get(key), distance);
			}
		}

	}

	public String getReadings(String base) throws Exception {
		StringTagger stringTagger = SenFactory.getStringTagger(null);
		List<Token> tokens = new ArrayList<Token>();
		stringTagger.analyze(base, tokens);
		StringBuilder sb = new StringBuilder();
		for (Token token : tokens) {
			Morpheme morp = token.getMorpheme();
			sb.append(morp.getReadings().get(0));
		}
		return sb.toString();
	}

	public Map<String, String> createDictionary(String[] args) throws Exception {
		if (args.length == 0) {
			throw new IllegalArgumentException();
		}
		String fileName = args[0];
		StringTagger stringTagger = SenFactory.getStringTagger(null);
		Reader reader = new InputStreamReader(new FileInputStream(fileName),
				"shift_jis");
		StreamTagger streamTagger = new StreamTagger(stringTagger, reader);
		Map<String, String> dic = new HashMap<String, String>();
		while (streamTagger.hasNext()) {
			Token token = streamTagger.next();
			// System.out.println(token.getStart());
			// System.out.println(token.getSurface());
			// System.out.println(token.getCost());
			// System.out.println(token.getLength());
			Morpheme morpheme = token.getMorpheme();
			// System.out.println(morpheme.getAdditionalInformation());
			// System.out.println(morpheme.getBasicForm());
			// System.out.println(morpheme.getConjugationalForm());
			// System.out.println(morpheme.getConjugationalType());
			// System.out.println(morpheme.getPartOfSpeech());
			// System.out.println(morpheme.getPronunciations());
			// System.out.println(morpheme.getReadings());
			// System.out.println("--------------------------");
			if (isTarget(token)) {
				dic.put(trans.transliterate(morpheme.getReadings().get(0)), token.getSurface());
			}
		}
		return dic;
	}

	protected boolean isTarget(Token token) {
		Morpheme morpheme = token.getMorpheme();
		String pos = morpheme.getPartOfSpeech();
		return (pos.startsWith("名詞") || pos.startsWith("動詞"));
	}
}
