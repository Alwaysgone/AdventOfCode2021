package adventofcode2021.day16.bits;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BitsReader2 {
	private static AtomicInteger NO_BYTE_LIMIT = new AtomicInteger(-1);
	public static BigInteger fromInputStream(InputStream is) throws IOException {
		StringBuilder currentBinaryString = new StringBuilder();
		AtomicBoolean endReached = new AtomicBoolean(false);
		List<BigInteger> values = readPacket(is, currentBinaryString, endReached, NO_BYTE_LIMIT, false);
		return values.get(0);
	}

	private static List<BigInteger> readPacket(InputStream is, StringBuilder currentBinaryString,
			AtomicBoolean endReached,
			AtomicInteger byteLimit,
			boolean readOnce) throws IOException {
		boolean checkForByteLimit = byteLimit.get() > 0;
		List<BigInteger> values = new LinkedList<>();
		if(checkForByteLimit) {
			ensureCharsAvailable(currentBinaryString, is, endReached, byteLimit.get());
		}
		do {
			ensureCharsAvailable(currentBinaryString, is, endReached, 7);
			if(endReached.get()) {
				return values;
			}
			Integer.valueOf(getSubString(currentBinaryString, 3), 2);
			byteLimit.addAndGet(-3);

			Integer type = Integer.valueOf(getSubString(currentBinaryString, 3), 2);
			byteLimit.addAndGet(-3);
			if(type == 4) {
				boolean packetEndReached = false;
				String valueString = "";
				while(!packetEndReached) {
					ensureCharsAvailable(currentBinaryString, is, endReached, 5);
					packetEndReached = getSubString(currentBinaryString, 1).equals("0");
					valueString += getSubString(currentBinaryString, 4);
					byteLimit.addAndGet(-5);
				}
				BigInteger value = new BigInteger(valueString, 2);
				values.add(value);
				if(readOnce) {
					return values;
				}
				if(checkForByteLimit && currentBinaryString.length() < 11) {
					return values;
				}
			} else {
				if(getSubString(currentBinaryString, 1).equals("0")) {
					ensureCharsAvailable(currentBinaryString, is, endReached, 15);
					if(endReached.get()) {
						return values;
					}
					byteLimit.addAndGet(-16);
					AtomicInteger expectedBitLength = new AtomicInteger(Integer.valueOf(getSubString(currentBinaryString, 15), 2));
					byteLimit.addAndGet(-expectedBitLength.get());
					List<BigInteger> expressionValues = readPacket(is, currentBinaryString, endReached, expectedBitLength, false);
					BigInteger expressionResult = getExpressionResult(expressionValues, type);
					values.add(expressionResult);
					if(readOnce) {
						return values;
					}
				} else {
					ensureCharsAvailable(currentBinaryString, is, endReached, 11);
					if(endReached.get()) {
						return values;
					}
					int expectedPackets = Integer.valueOf(getSubString(currentBinaryString, 11), 2);
					byteLimit.addAndGet(-12);
					List<BigInteger> expressionValues = new LinkedList<>();
					for(int i = 0; i < expectedPackets; i++) {
						List<BigInteger> subValues = readPacket(is, currentBinaryString, endReached, byteLimit, true);
						expressionValues.addAll(subValues);
					}
					BigInteger expressionResult = getExpressionResult(expressionValues, type);
					values.add(expressionResult);
					if(readOnce) {
						return values;
					}
				}
			}
		} while(checkForByteLimit && byteLimit.get() > 10);
		return values;
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

	private static BigInteger getExpressionResult(List<BigInteger> values, int expression) {
		switch(expression) {
		case 0:
			return values.stream().reduce(BigInteger.ZERO, (a,b) -> a.add(b));
		case 1:
			return values.stream().reduce(BigInteger.ONE, (a,b) -> a.multiply(b));
		case 2:
			BigInteger lowestNumber = null;
			for(BigInteger value :  values) {
				if(lowestNumber == null) {
					lowestNumber = value;
				} else if(value.compareTo(lowestNumber) < 0) {
					lowestNumber = value;
				}
			}
			return lowestNumber;
		case 3:
			BigInteger largestNumber = null;
			for(BigInteger value :  values) {
				if(largestNumber == null) {
					largestNumber = value;
				} else if(value.compareTo(largestNumber) > 0) {
					largestNumber = value;
				}
			}
			return largestNumber;
		case 5:
			if(values.size() != 2) {
				throw new IllegalArgumentException("Greater than expects exactly 2 arguments.");
			}
			return values.get(0).compareTo(values.get(1)) > 0 ? BigInteger.ONE : BigInteger.ZERO;
		case 6:
			if(values.size() != 2) {
				throw new IllegalArgumentException("Less than expects exactly 2 arguments.");
			}
			return values.get(0).compareTo(values.get(1)) < 0 ? BigInteger.ONE : BigInteger.ZERO;
		case 7:
			if(values.size() != 2) {
				throw new IllegalArgumentException("Equal to expecte exactly 2 arguments.");
			}
			return values.get(0).equals(values.get(1)) ? BigInteger.ONE : BigInteger.ZERO;
		default:
			throw new IllegalArgumentException("Unexpected expression type " + expression);
		}
	}
}
