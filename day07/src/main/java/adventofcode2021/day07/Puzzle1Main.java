package adventofcode2021.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle7_input.txt").toFile()))) {
			AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
			AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
			int[] crabs = Arrays.stream(reader.readLine().split(","))
					.mapToInt(Integer::parseInt)
					.sorted()
					.toArray();
			Arrays.stream(crabs)
			.forEach(v -> {
				if(v < min.get()) {
					min.set(v);
				} else if(v > max.get()) {
					max.set(v);
				}
			});
			int fuelRequired = Arrays.stream(crabs)
					.map(v -> v - min.get())
					.sum();
			int currentLevel = min.get();
			int crabIndex = 0;
			for(int i = min.get(); i < max.get(); i++) {
				while(currentLevel == crabs[crabIndex]) {
					crabIndex++;
					continue;
				}
				currentLevel++;
				int oldFuelRequired = fuelRequired;
				fuelRequired += crabIndex;
				fuelRequired -= crabs.length - crabIndex;
				if(fuelRequired > oldFuelRequired) {
					fuelRequired = oldFuelRequired;
					break;
				}
			}
			System.out.println(fuelRequired);
		}
	}
}
