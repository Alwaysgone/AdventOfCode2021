package adventofcode2021.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle7_input.txt").toFile()))) {
			AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
			AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
			int[] crabs = Arrays.stream(reader.readLine().split(","))
					.mapToInt(Integer::parseInt)
					.map(v -> {
						if(v < min.get()) {
							min.set(v);
						} else if(v > max.get()) {
							max.set(v);
						}
						return v;
					})
					.sorted()
					.toArray();
			int currentLowest = Integer.MAX_VALUE;
			for(int i = min.get(); i < max.get(); i++) {
				int fuelRequirement = 0;
				for(int j = 0; j < crabs.length; j++) {
					int distance = Math.abs(i - crabs[j]);
					fuelRequirement += IntStream.rangeClosed(1, distance)
					.sum();
				}
				if(fuelRequirement < currentLowest) {
					currentLowest = fuelRequirement;
				}
			}
			
			System.out.println(currentLowest);
		}
	}
}
