package adventofcode2021.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle1Main {

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
			Set<Velocity> xVelocities = getXVelocities(targetXArea);
			int highestYPosition = getHighestYPosition(targetYArea, targetXArea, xVelocities);
			System.out.println("Highest y position: " + highestYPosition);
		}
	}

	private static Set<Velocity> getXVelocities(List<Integer> targetXArea) {
		int largestX = targetXArea.get(targetXArea.size()-1);
		Set<Velocity> xVelocities = new LinkedHashSet<>();

		for(int i = 1; i <= largestX; i++) {
			int velocity = (i * (i + 1)) / 2;
			for(int j = 0; j < i; j ++) {
				int missingEnd = (j * (j + 1)) / 2;
				int partVelocity = velocity - missingEnd;
				if(targetXArea.contains(partVelocity)) {
					xVelocities.add(new Velocity(i, i - j));
				}
			}
		}
		return xVelocities;
	}

	private static int getHighestYPosition(List<Integer> targetYArea, List<Integer> targetXArea, Set<Velocity> xVelocities) {
		Set<Velocity> yVelocities = new LinkedHashSet<>();
		int highestFoundY = 0;
		int lowestTargetY = targetYArea.get(0);
		for(Velocity xVelocity : xVelocities) {
			int restXVelocity = xVelocity.getVelocity() - xVelocity.getStepsToReachTarget();
			int restXPart = ((restXVelocity * (restXVelocity + 1)) / 2);
			int targetX = ((xVelocity.getVelocity() * (xVelocity.getVelocity() + 1)) / 2) + restXPart;
			boolean staysInTargetXArea = targetXArea.contains(targetX);

			int stepsToReachTarget = xVelocity.getStepsToReachTarget();
			for(int i = 1; i <= 1_000; i++) {
				if(i > stepsToReachTarget && !staysInTargetXArea) {
					break;
				}
				int highestY = (i * (i + 1)) / 2;
				for(int j = 1; j <= 1_000; j++) {
					int downWardYMovement = (j * (j + 1)) / 2;
					int targetY = highestY - downWardYMovement;
					if(targetY < lowestTargetY) {
						break;
					}
					if(targetYArea.contains(targetY)) {
						// additional +1 because position stays the same once when yVelocity is 0
						yVelocities.add(new Velocity(i + j + 1, xVelocity.getStepsToReachTarget()));
						if(highestFoundY < highestY) {
							highestFoundY = highestY;
						}
					}
				}
			}
		}
		return highestFoundY;
	}

	private static class Velocity {
		private final int velocity;
		private final int stepsToReachTarget;

		public Velocity(int velocity, int stepsToReachTarget) {
			this.velocity = velocity;
			this.stepsToReachTarget = stepsToReachTarget;
		}

		public int getVelocity() {
			return velocity;
		}

		public int getStepsToReachTarget() {
			return stepsToReachTarget;
		}

		@Override
		public int hashCode() {
			return Objects.hash(stepsToReachTarget, velocity);
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
			Velocity other = (Velocity) obj;
			return stepsToReachTarget == other.stepsToReachTarget && velocity == other.velocity;
		}

		@Override
		public String toString() {
			return "Velocity [velocity=" + velocity + ", stepsToReachTarget=" + stepsToReachTarget + "]";
		}
	}
}
