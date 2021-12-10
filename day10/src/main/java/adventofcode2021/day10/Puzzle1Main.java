package adventofcode2021.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle10_input.txt").toFile()))) {
			String line;
			Map<Character, Integer> syntaxErrors = new HashMap<>();
			while((line = reader.readLine()) != null) {
				Stack<Character> charStack = new Stack<>();
				char[] rowChars = line.toCharArray();
				for(char c : rowChars) {
					if(c == '('
							|| c == '['
							|| c == '{'
							|| c == '<') {
						charStack.push(c);
					} else {
						char openingChar = charStack.pop();
						if(getClosingBracket(openingChar) != c) {
							syntaxErrors.compute(c, (k,v) -> {
								if(v == null) {
									return 1;
								} else {
									return v + 1;
								}
							});
						}
					}
				}
			}
			Map<Character, Integer> syntaxErrorScores = new HashMap<>();
			syntaxErrorScores.put(')', 3);
			syntaxErrorScores.put(']', 57);
			syntaxErrorScores.put('}', 1197);
			syntaxErrorScores.put('>', 25137);
			int syntaxErrorScore = syntaxErrors.entrySet().stream()
			.mapToInt(e -> syntaxErrorScores.get(e.getKey()) * e.getValue())
			.sum();
			System.out.println("Syntax error score is " + syntaxErrorScore);
		}
	}
	
	private static char getClosingBracket(char c) {
		if(c == '(') {
			return ')';
		} else if(c == '[') {
			return ']';
		} else if(c == '{') {
			return '}';
		} else {
			return '>';
		}
	}
}
