package com.karatebancho;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

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

	public static void main(String[] args) throws Exception {
		LuceneGosenExample obj = new LuceneGosenExample();
		Map<String,String>dic = obj.createDictionary(args);
		for (String key : dic.keySet()) {
			System.out.format("%s:%s%n", key, dic.get(key));
		}
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
				dic.put(morpheme.getReadings().get(0), token.getSurface());
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
