package adventofcode2021.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle22_input.txt").toFile()))) {
			boolean[][][] reactor = new boolean[101][101][101];

			String line;

			while((line = reader.readLine()) != null) {
				boolean turnOn = line.startsWith("on");
				line = line.substring(line.indexOf('x'));
				String[] splittedLine = line.split(",");
				String[] splittedX = splittedLine[0].substring(2).split("\\.\\.");
				int[] xCoords = IntStream.rangeClosed(Integer.parseInt(splittedX[0]), Integer.parseInt(splittedX[1]))
						.filter(v -> v >= -50 && v <= 50)
						.toArray();
				String[] splittedY = splittedLine[1].substring(2).split("\\.\\.");
				int[] yCoords = IntStream.rangeClosed(Integer.parseInt(splittedY[0]), Integer.parseInt(splittedY[1]))
						.filter(v -> v >= -50 && v <= 50)
						.toArray();
				String[] splittedZ = splittedLine[2].substring(2).split("\\.\\.");
				int[] zCoords = IntStream.rangeClosed(Integer.parseInt(splittedZ[0]), Integer.parseInt(splittedZ[1]))
						.filter(v -> v >= -50 && v <= 50)
						.toArray();

				switchCubes(turnOn, xCoords, yCoords, zCoords, reactor);
			}
			int onCounter = 0;
			for(int x = 0; x < reactor.length; x++) {
				for(int y = 0; y < reactor.length; y++) {
					for(int z = 0; z < reactor.length; z++) {
						onCounter += reactor[x][y][z] ? 1 : 0;
					}
				}
			}

			System.out.println("Number of on lights: " + onCounter);
		}
	}

	private static void switchCubes(boolean turnOn, int[] xCoords, int[] yCoords, int[] zCoords, boolean[][][] reactor) {
		for(int x = 0; x < xCoords.length; x++) {
			for(int y = 0; y < yCoords.length; y++) {
				for(int z = 0; z < zCoords.length; z++) {
					reactor[xCoords[x] + 50][yCoords[y] + 50][zCoords[z] + 50] = turnOn;
				}
			}
		}
	}
}
