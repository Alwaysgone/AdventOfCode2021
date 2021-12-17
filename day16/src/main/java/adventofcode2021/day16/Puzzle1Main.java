package adventofcode2021.day16;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import adventofcode2021.day16.bits.BitsReader;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(InputStream is = new FileInputStream(Paths.get(".").toAbsolutePath().resolve("puzzle16_input.txt").toFile())) {
			int versionSum = BitsReader.fromInputStream(is);
			// 137 too low
			System.out.println("Sum of all versions is: " + versionSum);
		}
	}
}
