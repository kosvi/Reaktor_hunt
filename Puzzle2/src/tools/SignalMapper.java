package tools;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SignalMapper {

	private static final char END_CHARACTER = ';';
	private List<SignalPiece> signalData;
	private String signal;

	public SignalMapper(String signal) {
		this.signal = signal;
		this.signalData = new ArrayList<>();
		this.calculateCharacters();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SignalPiece sp : this.signalData) {
			sb.append(sp.getCharacter());
			if (sp.getCharacter() == END_CHARACTER) {
				break;
			}
		}
		return sb.toString();
	}

	private void calculateCharacters() {
		// Using HashMap makes this easy as duplicate keys are not allowed
		Map<Character, Integer> characters = new HashMap<>();
		for (int i = 0; i < this.signal.length(); i++) {
			char character = this.signal.charAt(i);
			int amount = 1;
			if (characters.containsKey(character)) {
				amount += characters.get(character);
			}
			characters.put(character, amount);
		}
		this.storeSignalData(characters);
	}

	private void storeSignalData(Map<Character, Integer> characters) {
		// but still we cannot sort HashMap (think about is, what kind of datastructure
		// it is!)
		// Let's now generate a List that is easy to sort
		for (char c : characters.keySet()) {
			this.signalData.add(new SignalPiece(c, characters.get(c)));
		}
		Collections.sort(this.signalData);
		for (SignalPiece sp : this.signalData) {
			System.out.println(sp);
		}
	}
}
