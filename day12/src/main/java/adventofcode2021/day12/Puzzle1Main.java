package adventofcode2021.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle12_input.txt").toFile()))) {
			String line;
			Set<String> startCaves = new LinkedHashSet<>();
			Set<String> endCaves = new LinkedHashSet<>();
			Map<String, Set<String>> connectedCaves = new HashMap<>();
			while((line = reader.readLine()) != null) {
				String[] splittedLine = line.split("-");
				// have to check both sides for start and end token because input format is not consistent
				if(splittedLine[0].equals("start")) {
					startCaves.add(splittedLine[1]);
				} else if(splittedLine[1].equals("start")) {
					startCaves.add(splittedLine[0]);
				} else if(splittedLine[0].equals("end")) {
					endCaves.add(splittedLine[1]);
				} else if(splittedLine[1].equals("end")) {
					endCaves.add(splittedLine[0]);
				} else {
					connectedCaves.compute(splittedLine[0], (k,v) -> {
						if(v == null) {
							Set<String> otherCaves = new LinkedHashSet<>();
							otherCaves.add(splittedLine[1]);
							return otherCaves;
						} else {
							v.add(splittedLine[1]);
							return v;
						}
					});
					connectedCaves.compute(splittedLine[1], (k,v) -> {
						if(v == null) {
							Set<String> otherCaves = new LinkedHashSet<>();
							otherCaves.add(splittedLine[0]);
							return otherCaves;
						} else {
							v.add(splittedLine[0]);
							return v;
						}
					});
				}
			}
			
			Set<String> paths = startCaves.stream()
			.flatMap(sc -> {
				Map<String, Boolean> visitedCaves = new HashMap<>();
				visitedCaves.put(sc, true);
				return getPaths(sc, "start," + sc, connectedCaves, endCaves, visitedCaves, new HashSet<>()).stream();
			})
			.collect(Collectors.toSet());
			
			System.out.println("Number of paths: " + paths.size());
		}
	}
	
	private static Set<String> getPaths(String currentCave, String currentPath, Map<String,
			Set<String>> connectedCaves,
			Set<String> endCaves,
			Map<String, Boolean> visitedCaves,
			Set<String> paths) {
		Set<String> newPaths = new HashSet<>();
		Set<String> caves = connectedCaves.get(currentCave);
		for(String cave : caves) {
			if(!visitedCaves.getOrDefault(cave, false) || Character.isUpperCase(cave.toCharArray()[0])) {
				Map<String, Boolean> newVisitedCaves = new HashMap<>(visitedCaves);
				newVisitedCaves.put(cave, true);
				Set<String> subPaths = getPaths(cave, currentPath + "," + cave, connectedCaves, endCaves, newVisitedCaves, paths);
				newPaths.addAll(subPaths);
			}
		}
		if(endCaves.contains(currentCave)) {
			newPaths.add(currentPath + ",end");
		}
		paths.addAll(newPaths);
		return paths;
	}
}
