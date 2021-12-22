package adventofcode2021.day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle21_input.txt").toFile()))) {
			int player1Position = Integer.parseInt(reader.readLine().split(":\\s")[1]);
			int player2Position = Integer.parseInt(reader.readLine().split(":\\s")[1]);
			int player1Score = 0;
			int player2Score = 0;
			int diceRolls = 0;
			int dice = 1;
			
			while(player2Score < 1000) {
				for(int i = 0; i < 3; i++) {
					player1Position += dice++;
					if(dice == 101) {
						dice = 1;
					}
				}
				diceRolls += 3;
				player1Position = player1Position % 10;
				if(player1Position == 0) {
					player1Position = 10;
				}
				player1Score += player1Position;
				
				if(player1Score >= 1000) {
					break;
				}
				
				for(int i = 0; i < 3; i++) {
					player2Position += dice++;
					if(dice == 101) {
						dice = 1;
					}
				}
				diceRolls += 3;
				player2Position = player2Position % 10;
				if(player2Position == 0) {
					player2Position = 10;
				}
				player2Score += player2Position;
			}
			int losingScore = player1Score < 1000 ? player1Score : player2Score;
			System.out.println("Result: " + losingScore * diceRolls);
		}
	}
}
