package adventofcode2021.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("small_sample_input.txt").toFile()))) {
			String line;
			List<String> instructions = new LinkedList<>();
			List<CubeOverlap> cubeOverlaps = new LinkedList<>();
			while((line = reader.readLine()) != null) {
				instructions.add(line);
//				CubeOverlap overlap = overlaps(cubeOverlaps, line);
//				if(overlap != null) {
//					cubeOverlaps.add(overlap);
//				}
			}
			
			List<Cube> cubes = new LinkedList<>();
			
			long onCounter = 0;
			for(String instruction : instructions) {
				Cube cube = toCube(instruction);
				onCounter = calculateOnCubes(onCounter, cube, cubes);
			}
			
//			for(int i = instructions.size() - 1; i >= 0; i--) {
//				List<Cube> overlappingCubes = getOverlappingCubes(cubes);
//			}

			for(CubeOverlap overlap : cubeOverlaps) {
				Cube cube = overlap.cube;
				if(cube.startX < 0) {
//					int
				}
			}

			System.out.println("Number of on lights: " + onCounter);
		}
	}

	private static long calculateOnCubes(long onCounter, Cube cube, List<Cube> cubes) {
		
		
		// TODO Auto-generated method stub
		return 0;
	}

	private static Cube toCube(String instruction) {
		boolean turnOn = instruction.startsWith("on");
		String[] splittedLine = instruction.substring(instruction.indexOf('x')).split(",");
		String[] splittedX = splittedLine[0].substring(2).split("\\.\\.");
		String[] splittedY = splittedLine[1].substring(2).split("\\.\\.");
		String[] splittedZ = splittedLine[2].substring(2).split("\\.\\.");
		Cube cube = new Cube();
		cube.turnOn = turnOn;
		cube.startX = Integer.parseInt(splittedX[0]);
		cube.endX = Integer.parseInt(splittedX[1]);
		cube.startY = Integer.parseInt(splittedY[0]);
		cube.endY = Integer.parseInt(splittedY[1]);
		cube.startZ = Integer.parseInt(splittedZ[0]);
		cube.endZ = Integer.parseInt(splittedZ[1]);
		return cube;
	}

	private static List<Cube> getOverlappingCubes(List<Cube> cubes) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void switchCubes(boolean turnOn,
			int[] xCoords, int xOffest,
			int[] yCoords, int yOffest,
			int[] zCoords, int zOffset,
			boolean[][][] reactor) {
		for(int x = 0; x < xCoords.length; x++) {
			for(int y = 0; y < yCoords.length; y++) {
				for(int z = 0; z < zCoords.length; z++) {
					reactor[xCoords[x] + xOffest][yCoords[y] + yOffest][zCoords[z] + zOffset] = turnOn;
				}
			}
		}
	}

	private static CubeOverlap overlaps(List<CubeOverlap> overlaps, String currentCube) {
		return null;
	}

	private static class Cube {
		boolean turnOn;
		int startX;
		int endX;
		int startY;
		int endY;
		int startZ;
		int endZ;
	}

	private static class CubeOverlap {
		Cube cube;
		List<Cube> overlappingCubes = new LinkedList<>();
	}
}
