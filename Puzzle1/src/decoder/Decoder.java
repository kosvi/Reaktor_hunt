package decoder;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Paths;

import decoder.ChannelDataStream;

public class Decoder {

	private List<ChannelDataStream> channelDataStreams;

	public Decoder() {
		this.channelDataStreams = new ArrayList<>();
	}

	public boolean readChannelDataFromFile(String file) {
		try (Scanner channelDataReader = new Scanner(Paths.get(file))) {
			int line = 0;
			while (channelDataReader.hasNextLine()) {
				System.out.println("\nStarted decoding line " + ++line);
				this.channelDataStreams.add(new ChannelDataStream(channelDataReader.nextLine()));
			}
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public String getDataAsNumbers() {
		StringBuilder data = new StringBuilder();
		for (ChannelDataStream cds : this.channelDataStreams) {
			data.append(cds.getDecodedNumber());
		}
		return data.toString();
	}

	public String getDataAsCharacters() {
		StringBuilder data = new StringBuilder();
		for (ChannelDataStream cds : this.channelDataStreams) {
			data.append(cds.getDecodedCharacter());
		}
		return data.toString();
	}
}
