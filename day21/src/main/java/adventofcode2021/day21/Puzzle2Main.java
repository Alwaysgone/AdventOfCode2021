package adventofcode2021.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Puzzle2Main {
	public static final Map<Integer,Long> DICE_RESULTS_MAP = new LinkedHashMap<>();
	public static final List<Integer> DICE_RESULTS = new ArrayList<>();
	
	static {
		// unique results of 3 dice rolls of a three sided die
		DICE_RESULTS.add(3);
		DICE_RESULTS.add(4);
		DICE_RESULTS.add(5);
		DICE_RESULTS.add(6);
		DICE_RESULTS.add(7);
		DICE_RESULTS.add(8);
		DICE_RESULTS.add(9);
		
		DICE_RESULTS_MAP.put(3, 1L);
		DICE_RESULTS_MAP.put(4, 3L);
		DICE_RESULTS_MAP.put(5, 6L);
		DICE_RESULTS_MAP.put(6, 7L);
		DICE_RESULTS_MAP.put(7, 6L);
		DICE_RESULTS_MAP.put(8, 3L);
		DICE_RESULTS_MAP.put(9, 1L);
	}
	
	
	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("sample_input.txt").toFile()))) {
			int player1Position = Integer.parseInt(reader.readLine().split(":\\s")[1]);
			int player2Position = Integer.parseInt(reader.readLine().split(":\\s")[1]);
			
//			PlayerWins wins = new PlayerWins();
//			playDiracDice(wins, player1Position, 0, player2Position, 0);
			
			PlayerWins otherWins = playDiracDice(player1Position, 0, player2Position, 0);
			
			long mostWins = Math.max(otherWins.player1Wins, otherWins.player2Wins);
			System.out.println("Most wins: " + mostWins);
		}
	}
	
	public static void playDiracDice(PlayerWins wins, int player1Position, int player1Score
			, int player2Position, int player2Score) {
		boolean decided = false;
		for(int i = 3; i < 10; i++) {
			if(decided) {
				break;
			}
			for(int j = 3; j < 10; j++) {
				int newPlayer1Position = player1Position + i;
				if(newPlayer1Position > 10) {
					newPlayer1Position -= 10;
				}
				int newPlayer1Score = player1Score + newPlayer1Position;
				if(newPlayer1Score >= 21) {
					// add all rolls of player1 that would win the game
//					long newWins = wins.player1Wins + 1L;
					for(int k = i; k < 10; k++) {
						wins.player1Wins = wins.player1Wins + DICE_RESULTS_MAP.get(k);
					}
					decided = true;
					break;
				} else {
					int newPlayer2Position = player2Position + j;
					if(newPlayer2Position > 10) {
						newPlayer2Position -= 10;
					}
					int newPlayer2Score = player2Score + newPlayer2Position;
					if(newPlayer2Score >= 21) {
						// add all rolls of player2 that would win the game
//						long newWins = wins.player2Wins + 1L;
						for(int k = j; k < 10; k++) {
							wins.player2Wins = wins.player2Wins + DICE_RESULTS_MAP.get(k);
						}
						break;
					} else {
						playDiracDice(wins, newPlayer1Position, newPlayer1Score, newPlayer2Position, newPlayer2Score);
					}
				}
			}
		}
	}
	
	public static PlayerWins playDiracDice(int player1Position, int player1Score
			, int player2Position, int player2Score) {
		PlayerWins wins = new PlayerWins();
		for(int i = 3; i < 10; i++) {
			for(int j = 3; j < 10; j++) {
				int newPlayer1Position = player1Position + i;
				if(newPlayer1Position > 10) {
					newPlayer1Position -= 10;
				}
				int newPlayer1Score = player1Score + newPlayer1Position;
				if(newPlayer1Score >= 21) {
					for(int k = i; k < 10; k++) {
						wins.player1Wins = wins.player1Wins + DICE_RESULTS_MAP.get(k);
					}
				} else {
					int newPlayer2Position = player2Position + j;
					if(newPlayer2Position > 10) {
						newPlayer2Position -= 10;
					}
					int newPlayer2Score = player2Score + newPlayer2Position;
					long factor = DICE_RESULTS_MAP.get(i);
					if(newPlayer2Score >= 21) {
						for(int k = j; k < 10; k++) {
							wins.player2Wins = wins.player2Wins + DICE_RESULTS_MAP.get(k) * factor;
						}
						break;
					} else {
						PlayerWins result = playDiracDice(newPlayer1Position, newPlayer1Score, newPlayer2Position, newPlayer2Score).multiply(factor);
						wins.add(result);						
					}
				}
			}
		}
		return wins;
	}


	public static class PlayerWins {
		long player1Wins = 0;
		long player2Wins = 0;
		
		public PlayerWins add(PlayerWins other) {
			player1Wins = this.player1Wins + other.player1Wins;
			player2Wins = this.player2Wins + other.player2Wins;
			return this;
		}
		
		public PlayerWins multiply(long factor) {
			player1Wins = player1Wins * factor;
			player2Wins = player2Wins * factor;
			return this;
		}
	}
}
