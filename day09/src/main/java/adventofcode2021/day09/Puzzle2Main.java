package adventofcode2021.day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle2Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle9_input.txt").toFile()))) {
			List<int[]> map = new ArrayList<int[]>();
			String line;
			while((line = reader.readLine()) != null) {
				char[] rowChars = line.toCharArray();
				int[] row = new int[rowChars.length];
				for(int i = 0; i < rowChars.length; i++) {
					row[i] = rowChars[i] - '0';
				}
				map.add(row);	
			}
			List<Coordinates> lowPoints = new LinkedList<>();

			for(int i = 0; i < map.size(); i++) {
				int[] row = map.get(i);
				for(int j = 0; j < row.length; j++) {
					int currentValue = row[j];
					Coordinates currentCoords = new Coordinates(i, j);
					if(!getNeighborCoordinates(map, currentCoords).stream()
							.map(c -> map.get(c.getX())[c.getY()])
							.filter(v -> v <= currentValue)
							.findAny()
							.isPresent()) {
						lowPoints.add(currentCoords);
					}
				}
			}

			Set<Set<Coordinates>> largestBasins = lowPoints.stream()
					.map(lp -> findBasin(map, lp, new LinkedHashSet<>()))
					.sorted((s1, s2) -> -Integer.compare(s1.size(), s2.size()))
					.limit(3L)
					.collect(Collectors.toSet());

			Optional<Integer> basinSizeProduct = largestBasins.stream()
					.map(Set::size)
					.reduce((a,b) -> a * b);

			System.out.println("Product of largest 3 basin size is " + basinSizeProduct.get());
		}
	}

	private static Set<Coordinates> findBasin(List<int[]> map, Coordinates coords, Set<Coordinates> basin) {
		basin.add(coords);
		Set<Coordinates> neighborCoordinates = getNeighborCoordinates(map, coords);
		for(Coordinates neighborCoords : neighborCoordinates) {
			if(basin.add(neighborCoords)) {
				findBasin(map, neighborCoords, basin);
			}
		}
		return basin;
	}

	private static Set<Coordinates> getNeighborCoordinates(List<int[]> map, Coordinates coords) {
		int x = coords.getX();
		int y = coords.getY();
		int rowSize = map.get(0).length;
		List<Coordinates> neighborCoordinates = new ArrayList<>(4);

		if(x - 1 >= 0 && map.get(x - 1)[y] != 9) {
			neighborCoordinates.add(new Coordinates(x - 1, y));
		}
		if(x + 1 < map.size() && map.get(x + 1)[y] != 9) {
			neighborCoordinates.add(new Coordinates(x + 1, y));	
		}
		if(y - 1 >= 0 && map.get(x)[y - 1] != 9) {
			neighborCoordinates.add(new Coordinates(x, y - 1));	
		}
		if(y + 1 < rowSize && map.get(x)[y + 1] != 9) {
			neighborCoordinates.add(new Coordinates(x, y + 1));	
		}
		return neighborCoordinates.stream().collect(Collectors.toSet());
	}

	private static class Coordinates {
		private int x;
		private int y;

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
	}
}
