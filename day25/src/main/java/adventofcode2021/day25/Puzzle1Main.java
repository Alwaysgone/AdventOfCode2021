package adventofcode2021.day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".")
				.toAbsolutePath()
//				.resolve("small_sample_input.txt")
				.resolve("puzzle25_input.txt")
				.toFile()))) {
			List<char[]> input = new LinkedList<>();
			String line;
			while((line = reader.readLine()) != null) {
				input.add(line.toCharArray());
			}
			char[][] seaCucumbers = new char[input.size()][input.get(0).length];
			for(int i  = 0; i < input.size(); i++) {
				seaCucumbers[i] = input.get(i);
			}
			
			boolean movement = true;
			int iterations = 0;
			while(movement) {
				char[][] newSeaCucumbers = new char[seaCucumbers.length][0];
				movement = nextIteration(seaCucumbers, newSeaCucumbers);
				seaCucumbers = newSeaCucumbers;
				iterations++;
				System.out.println("Iteration " + iterations);
//				printSeaCucumbers(seaCucumbers);
			}
			System.out.println("Iteration sea cucumbers stopped at: " + iterations);
		}
	}

	private static void printSeaCucumbers(char[][] seaCucumbers) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < seaCucumbers.length; i++) {
			for(int j = 0; j < seaCucumbers[0].length; j++) {
				builder.append(seaCucumbers[i][j]);
			}
			builder.append(System.lineSeparator());
		}
		System.out.println(builder.toString());
	}

	private static boolean nextIteration(char[][] seaCucumbers, char[][] newSeaCucumbers) {
		boolean movement = false;
		//eastward sea cucumbers
		for(int i = 0; i < seaCucumbers.length; i++) {
			char[] currentLine = seaCucumbers[i];
			char[] newLine = Arrays.copyOf(currentLine, currentLine.length);
			newSeaCucumbers[i] = newLine;
			for(int j = 0; j < currentLine.length; j++) {
				if(currentLine[j] == '>') {
					int index = j < currentLine.length - 1 ? j + 1 : 0;
					if(currentLine[index] == '.') {
						movement = true;
						newLine[index] = '>';
						newLine[j] = '.';
					}
				}
			}
		}
		List<Move> moves = new LinkedList<>();
		//southward sea cucumbers
		for(int i = 0; i < newSeaCucumbers[0].length; i++) {
			if(newSeaCucumbers[newSeaCucumbers.length - 1][i] == 'v' && newSeaCucumbers[0][i] == '.') {
				moves.add(new Move(i, newSeaCucumbers.length - 1));
			}
		}
		
		for(int i = 0; i < newSeaCucumbers.length - 1; i++) {
			for(int j = 0; j < newSeaCucumbers[0].length; j++) {
				if(newSeaCucumbers[i][j] == 'v') {
					int index = i + 1;
					if(newSeaCucumbers[index][j] == '.') {
						moves.add(new Move(j, i));
					}
				}
			}
		}
		for(Move move : moves) {
			if(move.y == newSeaCucumbers.length - 1) {
				newSeaCucumbers[0][move.x] = 'v';
				newSeaCucumbers[move.y][move.x] = '.';
			} else {
				newSeaCucumbers[move.y + 1][move.x] = 'v';
				newSeaCucumbers[move.y][move.x] = '.';
			}
		}
		
		return movement || !moves.isEmpty();
	}
	
	private static class Move {
		int x;
		int y;
		
		public Move(int x, int y) {
			this.x = x;
			this.y = y;
		}		
	}
}
