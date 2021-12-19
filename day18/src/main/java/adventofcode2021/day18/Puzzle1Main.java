package adventofcode2021.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle1Main {
	private static final Pattern SINGLE_NUMBER_PATTERN = Pattern.compile("\\d+");
	private static final Pattern MULTI_DIGIT_NUMBER_PATTERN = Pattern.compile("\\d\\d+");
	private static final Pattern REGULAR_PAIR_PATTERN = Pattern.compile("\\[\\d,\\d\\]");

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle18_input.txt").toFile()))) {
			String currentSum = reader.readLine();
			currentSum = reduceSum(currentSum);
			String line;
			while((line = reader.readLine()) != null) {
				currentSum = '[' + currentSum + ',' + line + ']';
				currentSum = reduceSum(currentSum);
			}
			long magnitude = calculateMagnitude(currentSum);
			System.out.println("Magnitude of sum is: " + magnitude);
		}
	}

	private static String reduceSum(String currentSum) {
		String previousSum;
		String newSum = currentSum;
		boolean changed;
		do {
			changed = false;
			do {
				previousSum = newSum;
				newSum = explodeSum(previousSum);
				if(!newSum.equals(previousSum)) {
					StringBuilder output = new StringBuilder();
					output.append("Exploded ")
					.append(System.lineSeparator())
					.append(previousSum)
					.append(System.lineSeparator())
					.append("into")
					.append(System.lineSeparator())
					.append(newSum);
					System.out.println(output.toString());
				}
				changed = changed | !newSum.equals(previousSum);
			} while(!newSum.equals(previousSum));

			previousSum = newSum;
			newSum = splitSum(previousSum);
			if(!newSum.equals(previousSum)) {
				StringBuilder output = new StringBuilder();
				output.append("Split ")
				.append(System.lineSeparator())
				.append(previousSum)
				.append(System.lineSeparator())
				.append("into")
				.append(System.lineSeparator())
				.append(newSum);
				System.out.println(output.toString());
			}
			changed = changed || !newSum.equals(previousSum);
		} while(changed);
		return newSum;
	}

	private static String explodeSum(String currentSum) {
		StringBuilder newSum = new StringBuilder();
		int leftRegularNumberIndex = -1;
		int bracketCounter = 0;
		for(int i = 0; i < currentSum.length(); i++) {
			char currentChar = currentSum.charAt(i);
			if(currentChar == '[') {
				bracketCounter++;
			} else if(currentChar == ']') {
				bracketCounter--;
			} else if(currentChar != ',' && leftRegularNumberIndex != i + 1) {
				leftRegularNumberIndex = i;
			}
			if(bracketCounter > 4) {
				// explode
				int currentLeft = getNumberAtIndex(currentSum, i + 1);
				int digits = Integer.toString(currentLeft).length();
				int currentRight = getNumberAtIndex(currentSum, i + 2 + digits);
				int nextClosingBracketIndex = findNextClosingBracketIndex(currentSum, i);
				int pairWidth = nextClosingBracketIndex - i + 1;
				int stringLengthDiff = pairWidth - 1;
				if(leftRegularNumberIndex == -1) {
					newSum.append(currentSum.substring(0, i))
					.append(0);
				} else {
					int leftRegularNumber = getNumberAtIndex(currentSum, leftRegularNumberIndex);
					int sum = leftRegularNumber + currentLeft;
					digits = Integer.toString(leftRegularNumber).length();
					stringLengthDiff -= Integer.toString(sum).length() - digits;
					newSum.append(currentSum.substring(0, leftRegularNumberIndex))
					.append(sum)
					.append(currentSum.substring(leftRegularNumberIndex + digits, i))
					.append(0);
				}

				int rightRegularNumberIndex = findRightRegularNumberIndex(currentSum, newSum.length() + pairWidth - 1);
				if(rightRegularNumberIndex == -1) {
					newSum.append(currentSum.substring(nextClosingBracketIndex + 1, currentSum.length()));
				} else {
					newSum.append(currentSum.substring(nextClosingBracketIndex + 1, rightRegularNumberIndex));
					int rightRegularNumber = getNumberAtIndex(currentSum, rightRegularNumberIndex);
					int sum = rightRegularNumber + currentRight;
					digits = Integer.toString(rightRegularNumber).length();
					stringLengthDiff -= Integer.toString(sum).length() - digits;
					newSum.append(sum)
					.append(currentSum.substring(rightRegularNumberIndex + digits, currentSum.length()));
				}
				if(currentSum.length() - stringLengthDiff != newSum.length()) {
					System.out.println("Wrong length");
				}
				return newSum.toString();
			}
		}

		return currentSum;
	}

	private static String splitSum(String currentSum) {
		Matcher matcher = MULTI_DIGIT_NUMBER_PATTERN.matcher(currentSum);
		if(matcher.find()) {
			int number = getNumberAtIndex(currentSum, matcher.start());
			boolean even = number % 2 == 0;
			StringBuilder newSum = new StringBuilder();
			int digits = Integer.toString(number).length();
			if(digits != 2) {
				System.out.println("Woot");
			}
			int halfNumber = number / 2;
			newSum.append(currentSum.substring(0, matcher.start()))
			.append('[')
			.append(halfNumber)
			.append(',')
			.append(even ? halfNumber : halfNumber + 1)
			.append(']')
			.append(currentSum.substring(matcher.start() + digits))
			;
			return newSum.toString();
		}
		return currentSum;
	}

	private static int findNextClosingBracketIndex(String currentSum, int start) {
		for(int i = start; i < currentSum.length(); i++) {
			if(currentSum.charAt(i) == ']') {
				return i;
			}
		}
		return -1;
	}

	private static int findRightRegularNumberIndex(String currentSum, int start) {
		for(int i = start; i < currentSum.length(); i++) {
			int currentChar = (int)currentSum.charAt(i);
			if(currentChar >= (int)'0' && currentChar <= (int)'9') {
				return i;
			}
		}
		return -1;
	}

	private static int getNumberAtIndex(String sum, int index) {
		int number;
		int numberLength = 1;
		char nextChar = sum.charAt(index + 1);
		while(nextChar != '[' && nextChar != ']' && nextChar != ',') {
			numberLength++;
			nextChar = sum.charAt(index + numberLength);
		}
		number = Integer.parseInt(sum.substring(index, index + numberLength));
		return number;
	}

	private static long calculateMagnitude(String sum) {
		if(REGULAR_PAIR_PATTERN.matcher(sum).matches()) {
			String[] splittedSum = sum.split(",");
			return Long.parseLong(splittedSum[0].substring(1)) * 3L + Long.parseLong(splittedSum[1].substring(0, 1)) * 2L;
		}
		String[] splitSum = splitPair(sum);
		String leftPair = splitSum[0];
		String rightPair = splitSum[1];
		long leftSum;
		if(SINGLE_NUMBER_PATTERN.matcher(leftPair).matches()) {
			leftSum = Long.parseLong(leftPair) * 3L;
		} else {
			leftSum = calculateMagnitude(leftPair) * 3L;
		}
		long righSum;
		if(SINGLE_NUMBER_PATTERN.matcher(rightPair).matches()) {
			righSum = Long.parseLong(rightPair) * 2L;
		} else {
			righSum = calculateMagnitude(rightPair) * 2L;
		}
		return leftSum + righSum;
	}

	private static String[] splitPair(String pair) {
		int indexOfComma = -1;
		int bracketCounter = 0;
		for(int i = 0; i < pair.length(); i++) {
			char currentChar = pair.charAt(i);
			if(currentChar == '[') {
				bracketCounter++;
			} else if(currentChar == ']') {
				bracketCounter--;
			} else if(currentChar == ',' && bracketCounter == 1) {
				indexOfComma = i;
				break;
			}
		}
		String[] splittedPair = new String[2];
		splittedPair[0] = pair.substring(1, indexOfComma);
		splittedPair[1] = pair.substring(indexOfComma + 1, pair.length() - 1);
		return splittedPair;
	}
}
