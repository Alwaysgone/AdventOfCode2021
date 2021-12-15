package adventofcode2021.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle14_input.txt").toFile()))) {
			String line;
			Map<String, String> polymerMap = new HashMap<>();
			String polymer = "";
			while((line = reader.readLine()) != null) {
				if(line.isBlank()) {
					continue;
				} else if(line.contains("->")) {
					String[] splittedLine = line.split("\\s->\\s");
					polymerMap.put(splittedLine[0], splittedLine[1]);
				} else {
					polymer = line;
				}				
			}
			
			for(int i = 0; i < 10; i++) {
				StringBuilder polymerBuilder = new StringBuilder();
				for(int j = 1; j < polymer.length(); j++) {
					polymerBuilder
					.append(polymer.toCharArray()[j - 1])
					.append(polymerMap.get(polymer.substring(j - 1, j + 1)));
				}
				polymerBuilder.append(polymer.toCharArray()[polymer.length() - 1]);
				polymer = polymerBuilder.toString();
			}
			Map<Integer, Integer> charCounter =  new HashMap<>();
			polymer.chars().forEach(c -> {
				Integer count = charCounter.getOrDefault(c, 0);
				charCounter.put(c, count + 1);
			});
			AtomicInteger largestCount = new AtomicInteger(Integer.MIN_VALUE);
			AtomicInteger smallestCount = new AtomicInteger(Integer.MAX_VALUE);
			charCounter.values().forEach(v -> {
				if(v > largestCount.get()) {
					largestCount.set(v);
				} else if(v < smallestCount.get()) {
					smallestCount.set(v);
				}
			});
			
			System.out.println("Subtracting smallest count " + smallestCount.get() + " from largest count " + largestCount.get() + " yields: " + (largestCount.get() - smallestCount.get()));
		}
	}
}
