package adventofcode2021.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle11_input.txt").toFile()))) {
			String line;
			boolean[][] blinked = new boolean[10][10];
			int[][] squids = new int[10][10];
			int lineCounter = 0;
			AtomicInteger blinks = new AtomicInteger(0);
			while((line = reader.readLine()) != null) {
				char[] charSquids = line.toCharArray();
				for(int i = 0; i < charSquids.length; i++) {
					squids[lineCounter][i] = charSquids[i] - '0';
				}
				lineCounter++;
			}

			printSquids(squids);
			System.out.println("");
			int firstAllBlinkIteration = -1;
			int iteration = 1;
			while(firstAllBlinkIteration == -1) {
				for(int j = 0; j < 10; j++) {
					for(int k = 0; k < 10; k++) {
						blink(squids, blinked, blinks, j, k);
					}
				}
				
				boolean allBlinked = true;
				for(int j = 0; j < 10; j++) {
					for(int k = 0; k < 10; k++) {
						allBlinked = allBlinked && blinked[j][k];
					}
				}
				
				if(allBlinked && firstAllBlinkIteration == -1) {
					firstAllBlinkIteration = iteration;
				}

				// reset
				for(int j = 0; j < 10; j++) {
					Arrays.fill(blinked[j], false);
				}
				for(int j = 0; j < 10; j++) {
					for(int k = 0; k < 10; k++) {
						if(squids[j][k] > 9) {
							squids[j][k] = 0;
						}
					}
				}
				System.out.println("Iteration " + iteration++);
				printSquids(squids);
				System.out.println("");
			}

			System.out.println("Number of blinks: " + blinks);
			System.out.println("First all blink iteration: " + firstAllBlinkIteration);
		}
	}

	private static void printSquids(int[][] squids) {
		for(int i = 0; i < squids.length; i++) {
			System.out.println(Arrays.stream(squids[i])
			.mapToObj(v -> (Integer)v)
			.map(v -> v.toString())
			.collect(Collectors.joining("")));
		}
	}

	private static void blink(int[][] squids, boolean[][] blinked, AtomicInteger blinks, int x, int y) {
		if(x < 0 || x > 9 || y < 0 || y > 9) {
			return;
		}
		squids[x][y] = squids[x][y] + 1;
		if(squids[x][y] > 9 && !blinked[x][y]) {
			blinked[x][y] = true;
			blinks.incrementAndGet();
			blink(squids, blinked, blinks, x - 1, y - 1);
			blink(squids, blinked, blinks, x, y - 1);
			blink(squids, blinked, blinks, x + 1, y - 1);
			blink(squids, blinked, blinks, x - 1, y);
			blink(squids, blinked, blinks, x + 1, y);
			blink(squids, blinked, blinks, x - 1, y + 1);
			blink(squids, blinked, blinks, x, y + 1);
			blink(squids, blinked, blinks, x + 1, y + 1);
		}
	}
}
