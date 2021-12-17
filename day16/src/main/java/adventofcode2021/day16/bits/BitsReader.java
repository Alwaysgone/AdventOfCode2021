package adventofcode2021.day16.bits;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BitsReader {
	private static AtomicInteger NO_BYTE_LIMIT = new AtomicInteger(-1);
	public static int fromInputStream(InputStream is) throws IOException {
		StringBuilder currentBinaryString = new StringBuilder();
		AtomicInteger versionSum = new AtomicInteger(0);
		AtomicBoolean endReached = new AtomicBoolean(false);
		while(!endReached.get()) {
			readPacket(is, currentBinaryString, versionSum, endReached, NO_BYTE_LIMIT, false);
		}
		return versionSum.get();
	}

	private static void readPacket(InputStream is, StringBuilder currentBinaryString,
			AtomicInteger versionSum,
			AtomicBoolean endReached,
			AtomicInteger byteLimit,
			boolean readOnce) throws IOException {
		boolean checkForByteLimit = byteLimit.get() > 0;
		if(checkForByteLimit) {
			ensureCharsAvailable(currentBinaryString, is, endReached, byteLimit.get());
		}
		do {
			ensureCharsAvailable(currentBinaryString, is, endReached, 7);
			if(endReached.get()) {
				return;
			}
			Integer version = Integer.valueOf(getSubString(currentBinaryString, 3), 2);
			versionSum.addAndGet(version);
			byteLimit.addAndGet(-3);

			Integer type = Integer.valueOf(getSubString(currentBinaryString, 3), 2);
			byteLimit.addAndGet(-3);
			if(type == 4) {
				boolean packetEndReached = false;
				String value = "";
				while(!packetEndReached) {
					ensureCharsAvailable(currentBinaryString, is, endReached, 5);
					packetEndReached = getSubString(currentBinaryString, 1).equals("0");
					value += getSubString(currentBinaryString, 4);
					byteLimit.addAndGet(-5);
				}
//				Integer literalVersion = Integer.valueOf(value, 2);
//				versionSum.addAndGet(literalVersion);
				if(readOnce) {
					return;
				}
				if(checkForByteLimit && currentBinaryString.length() < 12) {
					return;
				}
			} else {
				if(getSubString(currentBinaryString, 1).equals("0")) {
					ensureCharsAvailable(currentBinaryString, is, endReached, 15);
					if(endReached.get()) {
						return;
					}
					byteLimit.addAndGet(-16);
					AtomicInteger expectedBitLength = new AtomicInteger(Integer.valueOf(getSubString(currentBinaryString, 15), 2));
					readPacket(is, currentBinaryString, versionSum, endReached, expectedBitLength, false);
				} else {
					ensureCharsAvailable(currentBinaryString, is, endReached, 11);
					if(endReached.get()) {
						return;
					}
					int expectedPackets = Integer.valueOf(getSubString(currentBinaryString, 11), 2);
					byteLimit.addAndGet(-12);
					for(int i = 0; i < expectedPackets; i++) {
						readPacket(is, currentBinaryString, versionSum, endReached, byteLimit, true);
					}
				}
			}
		} while(checkForByteLimit);
	}

	private static String readNextHex(InputStream is, AtomicBoolean endReached) throws IOException {
		int read = is.read();
		if(read == -1 || read == (int)'\n') {
			endReached.set(true);
			return "";
		}
		char character = (char)read;
		String rawBinaryString = Integer.toBinaryString(Integer.valueOf(String.valueOf(character), 16));
		return String.format("%4s", rawBinaryString).replace(' ', '0');
	}

	private static void ensureCharsAvailable(StringBuilder currentBinaryString, InputStream is, AtomicBoolean endReached, int availableChars) throws IOException {
		while(!endReached.get() && currentBinaryString.length() < availableChars) {
			currentBinaryString.append(readNextHex(is, endReached));
		}
	}

	private static String getSubString(StringBuilder currentBinaryString, int charCount) {
		String substring = currentBinaryString.substring(0, charCount);
		currentBinaryString.delete(0, charCount);
		return substring;
	}
}
