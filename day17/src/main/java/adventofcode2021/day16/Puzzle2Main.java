package adventofcode2021.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle17_input.txt").toFile()))) {
			String target = reader.readLine().split(":\\s")[1];
			String[] ranges = target.split(",\\s");
			String[] splittedX = ranges[0].split("\\.\\.");
			List<Integer> targetXArea = IntStream.rangeClosed(Integer.valueOf(splittedX[0].split("=")[1]), Integer.valueOf(splittedX[1]))
					.boxed()
					.collect(Collectors.toList());
			String[] splittedY = ranges[1].split("\\.\\.");
			List<Integer> targetYArea = IntStream.rangeClosed(Integer.valueOf(splittedY[0].split("=")[1]), Integer.valueOf(splittedY[1]))
					.boxed()
					.collect(Collectors.toList());
			System.out.println(String.format("Target %s", target));
			int distinctTargetVelocities = getDistinctTargetVelocities(targetYArea, targetXArea);
			System.out.println("Number of distinct initial velocities: " + distinctTargetVelocities);
		}
	}

	private static int getDistinctTargetVelocities(List<Integer> targetYArea, List<Integer> targetXArea) {
		Set<InitialVelocities> initialVelocities = new HashSet<>();
		for(int xVelocity = 1; xVelocity < 1_000; xVelocity++) {
			final int xVol = xVelocity;
			IntStream.rangeClosed(1, 1_000).forEach(steps -> {
				int currentVelocity = xVol;
				int targetX = 0;
				for(int l = 0; l < steps && currentVelocity > 0; l++) {
					targetX += currentVelocity--;
				}
				if(targetXArea.contains(targetX)) {
					IntStream.rangeClosed(-1_000, 1_000).forEach(yVelocity -> {
						int targetY = yVelocity;
						for(int k = 1; k < steps; k++) {
							targetY += yVelocity - k;
						}
						if(targetYArea.contains(targetY)) {
							initialVelocities.add(new InitialVelocities(xVol, yVelocity, steps));
						}
					});
				}
			});
		}
		return initialVelocities.size();
	}

	
	private static class InitialVelocities {
		private final int xVelocity;
		private final int yVelocity;
		private final int steps;

		public InitialVelocities(int xVelocity, int yVelocity, int steps) {
			this.xVelocity = xVelocity;
			this.yVelocity = yVelocity;
			this.steps = steps;
		}

		@Override
		public int hashCode() {
			return Objects.hash(xVelocity, yVelocity);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			InitialVelocities other = (InitialVelocities) obj;
			return xVelocity == other.xVelocity && yVelocity == other.yVelocity;
		}

		@Override
		public String toString() {
			return "InitialVelocities [xVelocity=" + xVelocity + ", yVelocity=" + yVelocity + ", steps=" + steps + "]";
		}
	}
}
