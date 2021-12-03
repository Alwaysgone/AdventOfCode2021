package adventofcode2021.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle2_input.txt").toFile()))) {
			int horizontalPosition = 0;
			int depth = 0;
			String line;
			while((line = reader.readLine()) != null) {
				String[] splittedLine = line.split("\\s");
				int value = Integer.parseInt(splittedLine[1]);
				switch(splittedLine[0]) {
				case "forward":
					horizontalPosition += value;
					break;
				case "down":
					depth += value;
					break;
				case "up":
					depth -= value;
					break;
				}
			}
			System.out.println(horizontalPosition * depth);
		}
	}
}
