package adventofcode2021.day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle5_input.txt").toFile()))) {
			Map<Coordinates, Integer> coordinateCounters = new LinkedHashMap<>();
			String line;
			while((line = reader.readLine()) != null) {
				String[] splittedLine = line.split("\\s->\\s");
				String[] splittedStart = splittedLine[0].split(",");
				String[] splittedEnd = splittedLine[1].split(",");
				
				int startX = Integer.parseInt(splittedStart[0]);
				int startY = Integer.parseInt(splittedStart[1]);
				int endX = Integer.parseInt(splittedEnd[0]);
				int endY = Integer.parseInt(splittedEnd[1]);
				
				if(startX == endX) {					
					for(int i = Math.min(startY, endY); i <= Math.max(startY, endY); i++) {
						Coordinates coordinates = new Coordinates(startX, i);
						coordinateCounters.compute(coordinates, (k,v) -> v == null ? 1 : v + 1);
					}
				} else if(startY == endY) {
					for(int i = Math.min(startX, endX); i <= Math.max(startX, endX); i++) {
						Coordinates coordinates = new Coordinates(i, startY);
						coordinateCounters.compute(coordinates, (k,v) -> v == null ? 1 : v + 1);
					}
				} else if(startX < endX && startY < endY) {
					//58,85 -> 917,944
					int currentX = startX;
					int currentY = startY;
					while(currentX <= endX && currentY <= endY) {
						Coordinates coordinates = new Coordinates(currentX++, currentY++);
						coordinateCounters.compute(coordinates, (k,v) -> v == null ? 1 : v + 1);
					}
				} else if(startX < endX && startY > endY) {
					//62,963 -> 844,181
					int currentX = startX;
					int currentY = startY;
					while(currentX <= endX && currentY >= endY) {
						Coordinates coordinates = new Coordinates(currentX++, currentY--);
						coordinateCounters.compute(coordinates, (k,v) -> v == null ? 1 : v + 1);
					}
				} else if(startX > endX && startY < endY) {
					//447,360 -> 62,745
					int currentX = startX;
					int currentY = startY;
					while(currentX >= endX && currentY <= endY) {
						Coordinates coordinates = new Coordinates(currentX--, currentY++);
						coordinateCounters.compute(coordinates, (k,v) -> v == null ? 1 : v + 1);
					}
				} else if(startX > endX && startY > endY) {
					//453,125 -> 347,19
					int currentX = startX;
					int currentY = startY;
					while(currentX >= endX && currentY >= endY) {
						Coordinates coordinates = new Coordinates(currentX--, currentY--);
						coordinateCounters.compute(coordinates, (k,v) -> v == null ? 1 : v + 1);
					}
				} else {
					System.out.println("sanity check");
				}
			}
			long dangerousSpots = coordinateCounters.values().stream()
					.filter(v -> v > 1)
					.count();

			System.out.println(dangerousSpots);
		}
	}
	
	private static class Coordinates {
		private int x;
		private int y;
		
		public Coordinates(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
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
			Coordinates other = (Coordinates) obj;
			return x == other.x && y == other.y;
		}

		@Override
		public String toString() {
			return "Coordinates [x=" + x + ", y=" + y + "]";
		}
	}
}
