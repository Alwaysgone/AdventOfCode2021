package adventofcode2021.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle8_input.txt").toFile()))) {
			String line;
			int[] digitsCounter = new int[10];
			while((line = reader.readLine()) != null) {
				String[] digits = line.split("\\s\\|\\s")[1].split("\\s");
				for(String digit : digits) {
					if(digit.length() == 2) {
						digitsCounter[1] = digitsCounter[1] + 1;
					} else if(digit.length() == 4) {
						digitsCounter[4] = digitsCounter[4] + 1;
					} else if(digit.length() == 3) {
						digitsCounter[7] = digitsCounter[7] + 1;
					} else if(digit.length() == 7) {
						digitsCounter[8] = digitsCounter[8] + 1;
					} 
				}
			}
			int digitCounterSum = 0;
			for(int i = 0; i < digitsCounter.length; i++) {
				digitCounterSum += digitsCounter[i];
				System.out.println(i + ":" + digitsCounter[i]);
			}
			System.out.println("Sum of all digit appearances: " + digitCounterSum);
		}
	}
}
