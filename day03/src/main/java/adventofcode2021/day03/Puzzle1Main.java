package adventofcode2021.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle3_input.txt").toFile()))) {
			int numberOfEntries = 0;
			int[] digitSums = new int[12];
			String line;
			while((line = reader.readLine()) != null) {
				numberOfEntries++;
				for(int i = 0; i < line.length(); i++) {
					digitSums[i] += line.charAt(i) == '1' ? 1 : 0;
				}
			}
			StringBuilder gammaRate = new StringBuilder();
			StringBuilder epsilonRate = new StringBuilder();
			int halfOfEntries = numberOfEntries / 2;
			for(int digitSum : digitSums) {
				gammaRate.append(digitSum > halfOfEntries ? '1' : '0');
				epsilonRate.append(digitSum < halfOfEntries ? '1' : '0');
			}
			
			System.out.println(Integer.parseInt(gammaRate.toString(), 2) * Integer.parseInt(epsilonRate.toString(), 2));
		}
	}
}
