package adventofcode2021.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle10_input.txt").toFile()))) {
			String line;
			Map<Character, Long> completionScores = new HashMap<>();
			completionScores.put(')', 1L);
			completionScores.put(']', 2L);
			completionScores.put('}', 3L);
			completionScores.put('>', 4L);
			List<Long> lineScores = new LinkedList<>();
			while((line = reader.readLine()) != null) {
				Stack<Character> charStack = new Stack<>();
				char[] rowChars = line.toCharArray();
				boolean lineCorrupted = false;
				for(char c : rowChars) {
					if(c == '('
							|| c == '['
							|| c == '{'
							|| c == '<') {
						charStack.push(c);
					} else {
						char openingChar = charStack.pop();
						if(getClosingBracket(openingChar) != c) {
							lineCorrupted = true;
							break;
						}
					}
				}
				if(!lineCorrupted) {
					long lineScore = 0;
					while(!charStack.isEmpty()) {
						char openingChar = charStack.pop();
						char closingChar = getClosingBracket(openingChar);
						lineScore *= 5;
						lineScore += completionScores.get(closingChar);
					}
					lineScores.add(lineScore);
				}
			}
			lineScores.sort(null);
			long middleScore = lineScores.get((lineScores.size() / 2));
			System.out.println("Middle line score is " + middleScore);
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
