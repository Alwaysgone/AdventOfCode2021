package adventofcode2021.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle13_input.txt").toFile()))) {
			String line;
			int largestX = Integer.MIN_VALUE;
			int largestY = Integer.MIN_VALUE;
			List<Coordinates> coordinates = new LinkedList<>();
			List<String> foldInstructions = new LinkedList<>();
			while((line = reader.readLine()) != null) {
				if(line.isBlank()) {
					continue;
				} else if(line.startsWith("fold")) {
					foldInstructions.add(line.split("\\s")[2]);
				} else {
					String[] splittedLine = line.split(",");
					int x = Integer.parseInt(splittedLine[0]);
					int y = Integer.parseInt(splittedLine[1]);
					if(x > largestX) {
						largestX = x;
					}
					if(y > largestY) {
						largestY = y;
					}
					coordinates.add(new Coordinates(x, y));
				}				
			}
			boolean[][] marks = new boolean[largestY + 1][largestX + 1];
			coordinates.forEach(c -> marks[c.getY()][c.getX()] = true);
			boolean[][] currentMarks = marks;

			printMarks(currentMarks);
			String foldInstruction = foldInstructions.get(0);
			System.out.println("Executing fold instruction: " + foldInstruction);
			String[] splittedInstruction = foldInstruction.split("=");
			int coord = Integer.parseInt(splittedInstruction[1]);
			if(splittedInstruction[0].equals("y")) {
				boolean[][] shrinkedCopy = getShrinkedCopy(currentMarks, "y", coord);
				int yOffset = 1;
				for(int i = coord + 1; i < marks.length; i++) {
					for(int j = 0; j < marks[0].length; j++) {
						shrinkedCopy[i - (yOffset * 2)][j] = shrinkedCopy[i - (yOffset * 2)][j] | currentMarks[i][j];
					}
					yOffset++;
				}
				currentMarks = shrinkedCopy;
			} else {
				boolean[][] shrinkedCopy = getShrinkedCopy(currentMarks, "x", coord);
				for(int i = 0; i < shrinkedCopy.length; i++) {
					int xOffset = 1;
					for(int j = coord + 1; j < marks[0].length; j++) {
						shrinkedCopy[i][j - (xOffset * 2)] = shrinkedCopy[i][j - (xOffset * 2)] | currentMarks[i][j];
						xOffset++;
					}
				}
				currentMarks = shrinkedCopy;
			}
			printMarks(currentMarks);
			
			int marksCounter = 0;
			for(int i = 0; i < currentMarks.length; i++) {
				for(int j = 0; j < currentMarks[0].length; j++) {
					marksCounter = marksCounter + (currentMarks[i][j] ? 1 : 0);
				}
			}
			System.out.println("Marks count after 1 fold: " + marksCounter);
		}
	}

	private static void printMarks(boolean[][] marks) {
		StringBuilder marksBuilder = new StringBuilder();
		for(int i = 0; i < marks.length; i++) {
			for(int j = 0; j < marks[0].length; j++) {
				marksBuilder.append(marks[i][j] ? '#' : '.');
			}
			marksBuilder.append(System.lineSeparator());
		}
		System.out.println(marksBuilder.toString());
	}

	private static boolean[][] getShrinkedCopy(boolean[][] marks, String axis, int coord) {
		boolean[][] shrinkedMarks;
		if(axis.equals("x")) {
			shrinkedMarks = new boolean[marks.length][coord];
		} else {
			shrinkedMarks = new boolean[coord][marks[0].length];
		}
		copyMarks(marks, shrinkedMarks);
		return shrinkedMarks;
	}

	private static void copyMarks(boolean[][] source, boolean[][] target) {
		for(int i = 0; i < target.length; i++) {
			for(int j = 0; j < target[0].length; j++) {
				target[i][j] = source[i][j];
			}
		}
	}


	private static class Coordinates {
		private final int x;
		private final int y;

		public Coordinates(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}
}
