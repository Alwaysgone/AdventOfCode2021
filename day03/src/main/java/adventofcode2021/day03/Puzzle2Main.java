package adventofcode2021.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
			List<String> lines = Files.readAllLines(Paths.get(".").toAbsolutePath().resolve("puzzle3_input.txt"));
			String oxygenRating = getRating(lines, 0, (d, h) -> d >= h);
			String c02ScrubberRating = getRating(lines, 0, (d, h) -> d < h);

			System.out.println(Integer.parseInt(oxygenRating, 2) * Integer.parseInt(c02ScrubberRating, 2));
	}

	private static String getRating(List<String> lines, int index, BiPredicate<Integer, Integer> sumChecker) {
		int numberOfEntries = 0;
		int digitSum = 0;
		for(String line : lines) {
			numberOfEntries++;
			digitSum += line.charAt(index) == '1' ? 1 : 0;
		}
		int halfOfEntries = numberOfEntries / 2;
		char charToLookFor = sumChecker.test(digitSum, halfOfEntries) ? '1' : '0';

		List<String> filteredLines = lines.stream()
				.filter(l -> l.charAt(index) == charToLookFor)
				.collect(Collectors.toList());
		if(filteredLines.size() == 1) {
			return filteredLines.get(0);
		}
		return getRating(filteredLines, index + 1, sumChecker);
	}
}
