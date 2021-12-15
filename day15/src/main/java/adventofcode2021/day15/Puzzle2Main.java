package adventofcode2021.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.psjava.algo.graph.shortestpath.DijkstraAlgorithm;
import org.psjava.algo.graph.shortestpath.SingleSourceShortestPathResult;
import org.psjava.ds.graph.DirectedWeightedEdge;
import org.psjava.ds.graph.MutableDirectedWeightedGraph;
import org.psjava.ds.numbersystrem.IntegerNumberSystem;
import org.psjava.goods.GoodDijkstraAlgorithm;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle15_input.txt").toFile()))) {
			String line = reader.readLine();
			int[][] smallChitons = new int[line.length()][line.length()];
			int[] firstLine = line.chars().map(v -> v - (int)'0').toArray();
			smallChitons[0] = firstLine;
			int chitonIndex = 1;
			while((line = reader.readLine()) != null) {
				smallChitons[chitonIndex++] = line.chars().map(v -> v - (int)'0').toArray();			
			}

			int[][] chitons = new int[smallChitons.length * 5][smallChitons.length * 5]; 
			
			for(int i = 0; i < smallChitons.length; i++) {
				for(int j = 0; j < smallChitons.length; j++) {
					chitons[i][j] = smallChitons[i][j];
				}
			}
			
			// fill vertical expansions first so all the others have a leftmost reference
			for(int i = 1; i < 5; i++) {
				int offSet = i * smallChitons.length;
				for(int j = offSet; j < offSet + smallChitons.length; j++) {
					for(int k = 0; k < smallChitons.length; k++) {
						int chiton = chitons[j - smallChitons.length][k];
						if(chiton + 1 > 9) {
							chiton = 1;
						} else {
							chiton++;
						}
						chitons[j][k] = chiton;
					}
				}
			}

			// fill chiton map from left to right for each row now that there is a leftmost filled chiton map
			for(int j = 0; j < chitons.length; j++) {
				for(int i = 1; i < 5; i++) {
					int offSet = i * smallChitons.length;
					for(int k = offSet; k < offSet + smallChitons.length; k++) {
						int chiton = chitons[j][k - smallChitons.length];
						if(chiton + 1 > 9) {
							chiton = 1;
						} else {
							chiton++;
						}
						chitons[j][k] = chiton;
					}
				}
			}


			MutableDirectedWeightedGraph<String, Integer> graph = MutableDirectedWeightedGraph.create();

			//add vertices
			for(int i = 0; i < chitons.length; i++) {
				for(int j = 0; j < chitons.length; j++) {
					graph.insertVertex(i + "," + j);
				}
			}

			//add edges
			for(int i = 0; i < chitons.length; i++) {
				for(int j = 0; j < chitons.length; j++) {
					addEdge(graph, i + "," + j, i, j + 1, chitons);
					addEdge(graph, i + "," + j, i, j - 1, chitons);
					addEdge(graph, i + "," + j, i - 1, j, chitons);
					addEdge(graph, i + "," + j, i + 1, j, chitons);
				}
			}
			IntegerNumberSystem numberSystem = IntegerNumberSystem.getInstance();
			DijkstraAlgorithm dijkstra = GoodDijkstraAlgorithm.getInstance();
			SingleSourceShortestPathResult<String, Integer, DirectedWeightedEdge<String, Integer>> result = dijkstra.calc(graph, "0,0", numberSystem);
			Integer distance = result.getDistance((chitons.length - 1) + "," + (chitons.length - 1));

			System.out.println("Distance to end: " + distance);
		}
	}

	private static void addEdge(MutableDirectedWeightedGraph<String, Integer> graph, String vertex, int i, int j, int[][] chitons) {
		if(i >= 0 && i < chitons.length && j >= 0 && j < chitons.length) {
			graph.addEdge(vertex, i + "," + j, chitons[i][j]);
		}
	}
}
