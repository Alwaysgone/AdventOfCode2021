package adventofcode2021.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle9_input.txt").toFile()))) {
			List<int[]> map = new ArrayList<int[]>();
			String line;
			int rowSize = 0;
			while((line = reader.readLine()) != null) {
				char[] rowChars = line.toCharArray();
				int[] row = new int[rowChars.length];
				for(int i = 0; i < rowChars.length; i++) {
					row[i] = rowChars[i] - '0';
				}
				rowSize = row.length;
				map.add(row);	
			}
			List<Integer> lowPoints = new LinkedList<>();

			// corners
			// left top
			if(map.get(0)[0] < map.get(0)[1]
					&& map.get(0)[0] < map.get(1)[0]) {
				lowPoints.add(map.get(0)[0]);
			}
			// right top
			if(map.get(0)[rowSize - 1] < map.get(0)[rowSize - 2]
					&& map.get(0)[rowSize - 1] < map.get(1)[rowSize - 1]) {
				lowPoints.add(map.get(0)[rowSize - 1]);
			}
			// left bottom
			if(map.get(map.size() - 1)[0] < map.get(map.size() - 2)[0]
					&& map.get(map.size() - 1)[0] < map.get(map.size() - 1)[1]) {
				lowPoints.add(map.get(map.size() - 1)[0]);
			}
			// right bottom
			if(map.get(map.size() - 1)[rowSize - 1] < map.get(map.size() - 1)[rowSize - 2]
					&& map.get(map.size() - 1)[rowSize - 1] < map.get(map.size() - 2)[rowSize - 1]) {
				lowPoints.add(map.get(map.size() - 1)[rowSize - 1]);
			}

			// borders
			// top row
			int[] topRow = map.get(0);
			for(int i = 1; i < rowSize - 1; i++) {
				if(topRow[i] < topRow[i - 1]
						&& topRow[i] < topRow[i + 1]
								&& topRow[i] < map.get(1)[i]) {
					lowPoints.add(topRow[i]);
				}
			}

			// bottom row
			int[] bottomRow = map.get(map.size() - 1);
			for(int i = 1; i < rowSize - 1; i++) {
				if(bottomRow[i] < bottomRow[i - 1]
						&& bottomRow[i] < bottomRow[i + 1]
								&& bottomRow[i] < map.get(map.size() - 2)[i]) {
					lowPoints.add(bottomRow[i]);
				}
			}

			// left column
			for(int i = 1; i < map.size() - 1; i++) {
				if(map.get(i)[0] < map.get(i - 1)[0]
						&& map.get(i)[0] < map.get(i)[1]
								&& map.get(i)[0] < map.get(i + 1)[0]) {
					lowPoints.add(map.get(i)[0]);
				}
			}

			// right column
			for(int i = 1; i < map.size() - 1; i++) {
				if(map.get(i)[rowSize - 1] < map.get(i - 1)[rowSize - 1]
						&& map.get(i)[rowSize - 1] < map.get(i)[rowSize - 2]
								&& map.get(i)[rowSize - 1] < map.get(i + 1)[rowSize - 1]) {
					lowPoints.add(map.get(i)[rowSize - 1]);
				}
			}

			// everything else

			for(int i = 1; i < map.size() - 1; i++) {
				int[] row = map.get(i);
				for(int j = 1; j < row.length - 1; j++) {
					int currentDigit = row[j];
					if(currentDigit < map.get(i)[j - 1]
							&& currentDigit < map.get(i)[j + 1]
									&& currentDigit < map.get(i - 1)[j]
											&& currentDigit < map.get(i + 1)[j]) {
						lowPoints.add(currentDigit);
					}
				}
			}

			int sumOfLowPoints = lowPoints.stream()
					.mapToInt(v -> v + 1)
					.sum();
						System.out.println("Sum of all low points: " + sumOfLowPoints);
		}
	}
}
