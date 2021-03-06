package adventofcode2021.day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("sample_input.txt").toFile()))) {
			char[] hallway = new char[11];
			for(int i = 0; i < hallway.length; i++) {
				hallway[i] = '.';
			}
			String line;

			char[] room1 = new char[2];
			char[] room2 = new char[2];
			char[] room3 = new char[2];
			char[] room4 = new char[2];

			int roomIndex = 1;
			while((line = reader.readLine()) != null) {
				if((line.startsWith("###") || line.startsWith("  #")) && line.charAt(3) != '#') {
					line = line.substring(3, line.length());
					String[] splittedLine = line.split("#");
					room1[roomIndex] = splittedLine[0].toCharArray()[0];
					room2[roomIndex] = splittedLine[1].toCharArray()[0];
					room3[roomIndex] = splittedLine[2].toCharArray()[0];
					room4[roomIndex] = splittedLine[3].toCharArray()[0];
					roomIndex--;
				}
			}
			Amphipods amphipods = new Amphipods(hallway, room1, room2, room3, room4, 0);

			List<Amphipods> solutions = new LinkedList<>();
			List<Amphipods> states = amphipods.getNextStates(15170);
			int iteration = 1;
			while(!states.isEmpty()) {
				List<Amphipods> nextStates = new LinkedList<>();
				long start = System.currentTimeMillis();
				for(Amphipods state : states) {
					//					nextStates.addAll(
					//15170
					for(Amphipods nextState : state.getNextStates(12700)) {
						if(nextState.isDone()) {
							System.out.println("Found solution with energy used: " + nextState.energyUsed);
							solutions.add(nextState);
						} else {
							nextStates.add(nextState);
						}
					}
					
					//							);
				}
				System.out.println("At iteration " + iteration++ + " took " + (System.currentTimeMillis() - start) + " ms");
				states = nextStates;
			}
			
			int lowestEnergy = 20_000;
			for(Amphipods solution : solutions) {
				if(solution.energyUsed < lowestEnergy) {
					lowestEnergy = solution.energyUsed;
				}
			}
			
			System.out.println("Found " + solutions.size() + " solutions with lowest energy " + lowestEnergy);
		}
	}
}
