package adventofcode2021.day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle4_input.txt").toFile()))) {
			List<Integer> bingoNumbers = Arrays.asList(reader.readLine().split(",")).stream()
					.map(Integer::parseInt)
					.collect(Collectors.toList());
			List<int[][]> bingoBoards = new LinkedList<>();
			String line;
			int bingoBoardRowCounter = 0;
			int[][] bingoBoard = null;
			while((line = reader.readLine()) != null) {
				if(line.isBlank()) {
					if(bingoBoard != null) {
						bingoBoards.add(bingoBoard);
					}
					bingoBoardRowCounter = 0;
					bingoBoard = new int[5][5];
					continue;
				}
				int[] bingoBoardRow = Arrays.stream(line.split("\\s+"))
						.filter(s -> !s.isBlank())
						.mapToInt(Integer::parseInt)
						.toArray();
				for(int i = 0; i < 5; i++) {
					bingoBoard[bingoBoardRowCounter][i] = bingoBoardRow[i];
				}
				bingoBoardRowCounter++;
			}
			int winnerScore = 0;
			for(int i = 1; i <= bingoNumbers.size(); i++) {
				List<Integer> chosenBingoNumbers = bingoNumbers.stream()
						.limit(i)
						.toList();
				int[][] winnerBoard = checkForWinner(chosenBingoNumbers, bingoBoards);
				if(winnerBoard != null) {
					int winningNumber = bingoNumbers.get(i - 1);
					int sumOfUncalledNumbers = IntStream.range(0, winnerBoard.length)
							.flatMap(j -> Arrays.stream(winnerBoard[j]))
							.filter(j -> {
								return !chosenBingoNumbers.contains(j);
							})
							.sum();
					winnerScore = sumOfUncalledNumbers * winningNumber;
					break;
				}
			}

			System.out.println(winnerScore);
		}
	}

	private static int[][] checkForWinner(List<Integer> chosenBingoNumbers, List<int[][]> bingoBoards) {
		for(int[][] board : bingoBoards) {
			for(int i = 0; i < board.length; i++) {
				boolean columnMatch = true;
				for(int j = 0; j < board.length; j++) {
					if(!chosenBingoNumbers.contains(board[j][i])) {
						columnMatch = false;
						break;
					}
				}
				if(columnMatch) {
					return board;
				}
				if(Arrays.stream(board[i])
						.allMatch(chosenBingoNumbers::contains)) {
					return board;
				}
			}
		}
		return null;
	}
}
