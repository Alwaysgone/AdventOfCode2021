package adventofcode2021.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Puzzle2Main {

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

			List<PolymerCount> polymerCounts = new ArrayList<>();
			for(int j = 1; j < polymer.length(); j++) {
				polymerCounts.add(new PolymerCount(polymer.substring(j - 1, j + 1), BigInteger.ONE));
			}

			for(int i = 0; i < 40; i++) {
				Map<String, List<PolymerCount>> groupedPolys = polymerCounts.stream()
						.flatMap(p -> {
							String poly = p.getPolymer();
							String insertChar = polymerMap.get(poly);
							return Stream.of(new PolymerCount(String.valueOf(poly.charAt(0)) + insertChar, p.getCount())
									, new PolymerCount(insertChar + String.valueOf(poly.charAt(1)), p.getCount()));
						}).collect(Collectors.groupingBy(p -> p.getPolymer()));
				polymerCounts = groupedPolys.entrySet().stream()
				.map(e -> new PolymerCount(e.getKey(), e.getValue().stream()
						.map(PolymerCount::getCount)
						.reduce((c1, c2) -> c1.add(c2)).get())
						)
				.collect(Collectors.toList());
			}
			Map<Character, BigInteger> charCounter = new HashMap<>();
			
			polymerCounts.stream()
			.forEach(pc -> {
				BigInteger count = charCounter.getOrDefault(pc.getPolymer().charAt(0), BigInteger.ZERO);
				charCounter.put(pc.getPolymer().charAt(0), count.add(pc.getCount()));
			});
			charCounter.put(polymer.charAt(polymer.length() - 1),
					charCounter.get(polymer.charAt(polymer.length() - 1)).add(BigInteger.ONE));
			BigInteger min = charCounter.values().stream()
					.min(BigInteger::compareTo)
					.get();
			BigInteger max = charCounter.values().stream()
					.max(BigInteger::compareTo)
					.get();

			System.out.println("Subtracting smallest count " + min + " from largest count " + max + " yields: " + max.subtract(min));
		}
	}

	private static class PolymerCount {
		private final String polymer;
		private final BigInteger count;

		public PolymerCount(String polymer, BigInteger count) {
			this.polymer = polymer;
			this.count = count;
		}

		public String getPolymer() {
			return polymer;
		}

		public BigInteger getCount() {
			return count;
		}

		@Override
		public String toString() {
			return "PC[" + polymer + ", " + count + "]";
		}
	}
}
