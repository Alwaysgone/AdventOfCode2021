package adventofcode2021.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle6_input.txt").toFile()))) {
			long startTime = System.currentTimeMillis();
			int[] lanternFish = Arrays.stream(reader.readLine().split(","))
					.mapToInt(Integer::parseInt)
					.toArray();
			System.out.println("Reading values took: " + (System.currentTimeMillis() - startTime) + " ms");
			startTime = System.currentTimeMillis(); 
			List<int[]> lanternFishGens = new LinkedList<>();
			lanternFishGens.add(lanternFish);
			for(int i = 0; i < 80; i++) {
				int newFishCounter = 0;
				for(int[] currentGen : lanternFishGens) {
					for(int j = 0; j < currentGen.length; j++) {
						int fish = currentGen[j];
						if(fish == 0) {
							newFishCounter++;
							currentGen[j] = 6;
						} else {
							currentGen[j] = fish - 1;
						}
					}
				}
				int[] nextFishGen = IntStream.generate(() -> 8).limit(newFishCounter).toArray();
				if(nextFishGen.length > 0) {
					lanternFishGens.add(nextFishGen);
				}
			}
			System.out.println("Simulating generations took: " + (System.currentTimeMillis() - startTime) + " ms");
			
			int sumOfFish = lanternFishGens.stream()
					.mapToInt(f -> f.length)
					.sum();

			System.out.println(sumOfFish);
		}
	}
}
