package adventofcode2021.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle1_input.txt").toFile()))) {
			int measureIncreases = 0;
			String line = reader.readLine();
			Integer currentValue = Integer.parseInt(line);
			while((line = reader.readLine()) != null) {
				Integer nextValue = Integer.parseInt(line);
				measureIncreases += nextValue > currentValue ? 1 : 0;
				currentValue = nextValue;
			}
			System.out.println(measureIncreases);
		}
	}
}
