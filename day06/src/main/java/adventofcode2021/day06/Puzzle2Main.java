package adventofcode2021.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle6_input.txt").toFile()))) {
			long[] fishDays = new long[9];
			long startTime = System.currentTimeMillis();
			Arrays.stream(reader.readLine().split(","))
			.mapToInt(Integer::parseInt)
			.forEach(v -> fishDays[v] = fishDays[v] + 1);
			System.out.println("Reading values took: " + (System.currentTimeMillis() - startTime) + " ms");
			startTime = System.currentTimeMillis();
			for(int i = 0; i < 256; i++) {
				long newFish = fishDays[0];
				for(int j = 1; j < fishDays.length; j++) {
					fishDays[j - 1] = fishDays[j];
				}
				fishDays[6] = fishDays[6] + newFish;
				fishDays[8] = newFish;
			}
			System.out.println("Simulating generations took: " + (System.currentTimeMillis() - startTime) + " ms");
			long sumOfFish = Arrays.stream(fishDays)
					.sum();
			System.out.println(sumOfFish);
		}
	}
}
