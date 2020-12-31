package decoder;

public class DataByte {

	private static final int BYTE_SIZE = 8;
	private String binaryValue;
	private int numberValue;
	private String charValue;

	public DataByte(String binary) {
		this.binaryValue = binary;
		this.decodeByte(binary);
	}

	public String getBinary() {
		return this.binaryValue;
	}

	public int getNumber() {
		return this.numberValue;
	}

	public String getChar() {
		return this.charValue;
	}

	private void decodeByte(String binary) {
		// it's possible the byte isn't complete, let's first take care of that
		if (binary.length() != BYTE_SIZE) {
			this.numberValue = -1;
			this.charValue = "";
		}
		// let's give our byte a default value
		int byteValue = -1;
		try {
			// and extract the actual value if we can
			byteValue = Integer.parseInt(binary, 2);
		} catch (NumberFormatException e) {
			// this app is not for production, otherwise a better way to handle exception
			// would be needed
			System.err.println(e.getMessage());
		}
		// if we managed to extract it, let's use the value
		if (byteValue >= 0) {
			this.numberValue = byteValue;
			this.charValue = Character.toString((char) byteValue);
		}
	}
}
