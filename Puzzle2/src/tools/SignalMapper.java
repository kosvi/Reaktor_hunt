package tools;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SignalMapper {

	private static final char END_CHARACTER = ';';
	private List<SignalPiece> signalData;
	private StringBuilder baseValue;
	private String signal;

	public SignalMapper(String signal) {
		this.signal = signal;
		this.signalData = new ArrayList<>();
		this.baseValue = new StringBuilder();
	}

	@Override
	public String toString() {
		return this.baseValue.toString();
	}

	public List<SignalPiece> getSignalData() {
		return this.signalData;
	}

	// I guess it could be done like this as well:
	// https://stackoverflow.com/questions/31990103/more-effective-method-for-finding-the-most-common-character-in-a-string
	// ...and I guess it is more efficient then my method, but I don't want to
	// copy&paste here
	private Map<Character, Integer> calculateCharacters(String string) {
		// Using HashMap makes this easy as duplicate keys are not allowed
		Map<Character, Integer> characters = new HashMap<>();
		for (int i = 0; i < string.length(); i++) {
			char character = string.charAt(i);
			int amount = 1;
			if (characters.containsKey(character)) {
				amount += characters.get(character);
			}
			characters.put(character, amount);
		}
		return characters;
	}

	private char findMostCommonCharacter(Map<Character, Integer> characters) {
		// but still we cannot sort HashMap (think about is, what kind of datastructure
		// it is!)
		// Let's now generate a List that is easy to sort
		List<SignalPiece> signalData = new ArrayList<>();
		for (char c : characters.keySet()) {
			signalData.add(new SignalPiece(c, characters.get(c)));
		}
		Collections.sort(signalData);
		this.signalData = signalData;
		if (signalData.size() > 0) {
			return signalData.get(0).getCharacter();
		} else {
			return END_CHARACTER;
		}
	}

	public char addCharacterToBase() {
		// add the possibilities as a next char to a string
		StringBuilder nextCharacters = new StringBuilder();

		// if we are searching the first char, we will search the whole signal string
		if (this.baseValue.length() < 1) {
			nextCharacters.append(this.signal);
		} else {
			// else we split with the current "last" char of the base value
			char lastChar = this.baseValue.toString().charAt(this.baseValue.length() - 1);
			System.out.println("Splitting with " + lastChar);
			String[] pieces = this.signal.split(Character.toString(lastChar));
			for (String piece : pieces) {
				System.out.print(piece + " - ");
				if (piece.length() > 0) {
					char firstChar = piece.charAt(0);
					nextCharacters.append(firstChar);
				}
			}
		}
		System.out.println("\nGenerated string: (" + nextCharacters.length() + ") " + nextCharacters);

		Map<Character, Integer> characters = this.calculateCharacters(nextCharacters.toString());
		// and then we will calculate what occurred the most
		char newChar = this.findMostCommonCharacter(characters);
		this.baseValue.append(newChar);
		return newChar;
	}
}
