package tools;

public class SignalPiece implements Comparable<SignalPiece> {

	private char character;
	private int amount;

	public SignalPiece(char character) {
		this.character = character;
	}

	public SignalPiece(char character, int amount) {
		this(character);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public char getCharacter() {
		return character;
	}

	@Override
	public String toString() {
		return this.character + ": " + this.amount;
	}

	@Override
	public int compareTo(SignalPiece o) {
		// We want to have the highest amount first,
		// so that is why we compare "wrong way"
		if (this.amount == o.getAmount()) {
			return Character.compare(o.getCharacter(), this.character);
		}
		return Integer.compare(o.getAmount(), this.amount);
	}

}
