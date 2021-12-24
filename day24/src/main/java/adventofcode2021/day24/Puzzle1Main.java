package adventofcode2021.day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Puzzle1Main {

	public static void main(String[] args) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".")
				.toAbsolutePath()
				.resolve("puzzle24_input.txt")
				.toFile()))) {
			List<String> instructions = new LinkedList<>();
			String line;
			while((line = reader.readLine()) != null) {
				instructions.add(line);
			}
			//			Map<Long, Boolean> checkedNumbers = new HashMap<>();

			//			long lastModelNumber = 0L;
			long currentModelNumber = 99_999_999_999_999L;
			long largestAcceptedNumber = -1L;

			while(true) {
				boolean accepted = checkNumber(currentModelNumber, instructions);
				if(accepted) {
					largestAcceptedNumber = currentModelNumber;
					break;
					//					lastModelNumber = currentModelNumber;
					//					currentModelNumber = (currentModelNumber + lastModelNumber) / 2L;
				}
				currentModelNumber--;
				//				else 
				//					currentModelNumber = currentModelNumber / 2L;
			}
			System.out.println("Largest accepted model number: " + largestAcceptedNumber);
		}

		//			StringBuilder input = new StringBuilder();
		//			input
		//			.append(5)
		//			.append(System.lineSeparator())
		//			.append(1432);
		//			BufferedReader inputReader = new BufferedReader(new StringReader(input.toString()));
		//			Alu alu = new Alu(inputReader);
		//			for(String instruction : instructions) {
		//				alu.processInstruction(instruction);
		//			}
		//		alu.getVariables().forEach((k,v) -> System.out.println(k + ": " + v));
	}

	static boolean checkNumber(long number, List<String> instructions) throws NumberFormatException, IOException {
		String numberAsString = Long.toString(number);
		if(numberAsString.contains("0") || numberAsString.length() < 14) {
			return false;
		}
		StringBuilder input = new StringBuilder();
		for(int i = 0; i < numberAsString.length(); i++) {
			input
			.append(numberAsString.subSequence(i, i + 1))
			.append(System.lineSeparator());
		}
		BufferedReader inputReader = new BufferedReader(new StringReader(input.toString()));
		Alu alu = new Alu(inputReader);
		for(String instruction : instructions) {
			alu.processInstruction(instruction);
		}
		Long z = alu.getVariables().get("z");
		return z == 0;
	}
}
