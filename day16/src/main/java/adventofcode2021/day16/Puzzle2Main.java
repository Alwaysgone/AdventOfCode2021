package adventofcode2021.day16;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Paths;

import adventofcode2021.day16.bits.BitsReader2;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(InputStream is = new FileInputStream(Paths.get(".").toAbsolutePath().resolve("puzzle16_input.txt").toFile())) {
			BigInteger expressionResult = BitsReader2.fromInputStream(is);
			System.out.println("Outermost packet expression results in: " + expressionResult);
		}
	}
}
