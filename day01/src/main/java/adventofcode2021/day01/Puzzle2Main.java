package adventofcode2021.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		CircularFifoQueue<Integer> valueQueue = new CircularFifoQueue<Integer>(3);
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle1_input.txt").toFile()))) {
			int measureIncreases = 0;
			int currentSlidingSum = 0;
			String line;
			for(int i = 0; i <= 2; i++) {
				line = reader.readLine();
				int currentValue = Integer.parseInt(line);
				currentSlidingSum += currentValue;
				valueQueue.add(currentValue);
			}
			int nextSlidingSum = currentSlidingSum;
			while((line = reader.readLine()) != null) {
				Integer nextValue = Integer.parseInt(line);
				nextSlidingSum -= valueQueue.get(0);
				nextSlidingSum += nextValue;
				measureIncreases += nextSlidingSum > currentSlidingSum ? 1 : 0;
				valueQueue.add(nextValue);
				currentSlidingSum = nextSlidingSum;
			}
			System.out.println(measureIncreases);
		}
	}
}
