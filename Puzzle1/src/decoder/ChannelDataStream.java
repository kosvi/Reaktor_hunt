package decoder;

import java.util.List;
import java.util.ArrayList;
import decoder.DataByte;

public class ChannelDataStream {

	// Hardcode byte size, but avoid magic numbers :)
	private static final int BYTE_SIZE = 8;
	private List<DataByte> dataBytes;
	private int decodedNumber;
	private String decodedCharacter;

	public ChannelDataStream() {
		this.dataBytes = new ArrayList<>();
		this.decodedNumber = -1;
		this.decodedCharacter = "";
	}

	// Just in case it's easier to add the stream already in constructor
	public ChannelDataStream(String stream) {
		this();
		this.splitStreamToBytes(stream);
	}

	public int getDecodedNumber() {
		return this.decodedNumber;
	}

	public String getDecodedCharacter() {
		return this.decodedCharacter;
	}

	public void splitStreamToBytes(String stream) {
		// loop stream until i becomes bigger then stream length
		for (int i = 0; i < stream.length(); i = i + BYTE_SIZE) {
			// add bytes to list, final byte might not be a whole byte,
			// so we have to handle that
			DataByte newByte = new DataByte(stream.substring(i, Math.min(i + BYTE_SIZE, stream.length())));
			// if getNumber return -1 the byte was incomplete
			if (newByte.getNumber() >= 0) {
				this.dataBytes.add(newByte);
			}
		}
		System.out.println("Line has a total of " + this.dataBytes.size() + " bytes");
		this.calculateStreamValue();
	}

	private void calculateStreamValue() {
		System.out.print("Decoding... ");
		int firstValidByte = 0;
		for (DataByte db : this.dataBytes) {
			if (db.getNumber() <= this.dataBytes.size()) {
				// this is first valid byte
				break;
			}
			firstValidByte++;
		}
		System.out.print(firstValidByte);
		/*
		 * if (true) { return; }
		 */
		// now we know where to start our search
		DataByte current = this.dataBytes.get(firstValidByte);
		while (true) {
			System.out.print(" " + current.getNumber());
			if (current.getNumber() <= this.dataBytes.size()) {
				// we are within the stream
				current = this.dataBytes.get(current.getNumber());
			} else {
				// now we are no longer pointing inside the stream
				this.decodedNumber = current.getNumber();
				this.decodedCharacter = current.getChar();
				break;
			}
		}
	}
}
