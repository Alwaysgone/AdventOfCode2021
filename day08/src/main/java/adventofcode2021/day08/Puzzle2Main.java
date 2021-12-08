package adventofcode2021.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle8_input.txt").toFile()))) {
			String line;
			int displaySum = 0;
			List<String> permutations = generatePermutations("", "abcdefg", new LinkedList<>());
			while((line = reader.readLine()) != null) {
				int display = solve(line, permutations);
				displaySum += display;
			}
			System.out.println("Sum of all displays: " + displaySum);
		}
	}

	private static final String[] digitMappings = new String[]{
			"abcefg",	// 0
			"cf",		// 1
			"acdeg",	// 2
			"acdfg",	// 3
			"bcdf",		// 4
			"abdfg",	// 5
			"abdefg",	// 6
			"acf",		// 7
			"abcdefg",	// 8
			"abcdfg"	// 9
	};

	private static int solve(String line, List<String> permutations) {

		String[] splittedLine = line.split("\\s\\|\\s");
		String[] digitsInput = splittedLine[0].split("\\s");
		String[] display = splittedLine[1].split("\\s");
		for(String permutation : permutations) {
			Map<Character, Character> mapping = new LinkedHashMap<>();
			for(int i = 0; i < permutation.length(); i++) {
				mapping.put((char)((int)'a' + i), permutation.charAt(i));
			}
			boolean foundMapping = true;
			for(String input : digitsInput) {
				if(!isNumber(input, mapping)) {
					foundMapping = false;
					break;
				}
			}
			if(foundMapping) {
				StringBuilder digitBuilder = new StringBuilder();
				for(String digit : display) {
					digitBuilder.append(getNumber(digit, mapping));
				}
				return Integer.parseInt(digitBuilder.toString());
			}
		}
		return -1;
	}


	private static List<String> generatePermutations(String prefix, String str, List<String> permutations) {
		int n = str.length();
		if (n == 0) {
			permutations.add(prefix);
		} else {
			for (int i = 0; i < n; i++)
				generatePermutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), permutations);
		}
		return permutations;
	}

	private static boolean isNumber(String input, Map<Character, Character> mapping) {
		for(String digitMapping : digitMappings) {
			if(input.length() != digitMapping.length()) {
				continue;
			}
			boolean isNumber = true;
			for(char c : input.toCharArray()) {
				Character mappedCharacter = mapping.get(c);
				if(!digitMapping.contains(mappedCharacter.toString())) {
					isNumber = false;
					break;
				}
			}
			if(isNumber) {
				return true;
			}
		}
		return false;
	}
	
	private static int getNumber(String input, Map<Character, Character> mapping) {
		StringBuilder mappedInput = new StringBuilder();
		for(char c : input.toCharArray()) {
			mappedInput.append(mapping.get(c));
		}
		char[] mappedDigit = mappedInput.toString().toCharArray();
		Arrays.sort(mappedDigit);
		for(int i = 0; i < digitMappings.length; i++) {
			char[] digitMapping = digitMappings[i].toCharArray();
			if(Arrays.equals(digitMapping, mappedDigit)) {
				return i;
			}
		}
		return -1;
	}
}
