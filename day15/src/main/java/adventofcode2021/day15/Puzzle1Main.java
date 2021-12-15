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

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle15_input.txt").toFile()))) {
			String line = reader.readLine();
			int[][] chitons = new int[line.length()][line.length()];
			int[] firstLine = line.chars().map(v -> v - (int)'0').toArray();
			chitons[0] = firstLine;
			int chitonIndex = 1;
			while((line = reader.readLine()) != null) {
				chitons[chitonIndex++] = line.chars().map(v -> v - (int)'0').toArray();			
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
