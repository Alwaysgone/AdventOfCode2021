package adventofcode2021.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Alu {
	/*
	 * 
    inp a - Read an input value and write it to variable a.
    add a b - Add the value of a to the value of b, then store the result in variable a.
    mul a b - Multiply the value of a by the value of b, then store the result in variable a.
    div a b - Divide the value of a by the value of b, truncate the result to an integer, then store the result in variable a. (Here, "truncate" means to round the value toward zero.)
    mod a b - Divide the value of a by the value of b, then store the remainder in variable a. (This is also called the modulo operation.)
    eql a b - If the value of a and b are equal, then store the value 1 in variable a. Otherwise, store the value 0 in variable a.

	 */

	Map<String, Long> variables = new HashMap<>();
	private BufferedReader inputReader;

	public Alu(BufferedReader inputReader) {
		variables.put("w", 0L);
		variables.put("x", 0L);
		variables.put("y", 0L);
		variables.put("z", 0L);
		this.inputReader = inputReader;
	}
	
	public Map<String, Long> getVariables() {
		return variables;
	}
	
	long getValue(String var) {
		Long value = variables.get(var);
		if(value == null) {
			return Long.parseLong(var);
		}
		return value;
	}
	
	void processInstruction(String instruction) throws NumberFormatException, IOException {
		String[] splittedInstruction = instruction.split("\\s");
		switch(splittedInstruction[0]) {
		case "inp":
			variables.put(splittedInstruction[1], Long.parseLong(inputReader.readLine()));
			break;
		case "add":
			variables.put(splittedInstruction[1], getValue(splittedInstruction[1]) + getValue(splittedInstruction[2]));
			break;
		case "mul":
			variables.put(splittedInstruction[1], getValue(splittedInstruction[1]) * getValue(splittedInstruction[2]));
			break;
		case "div":
			variables.put(splittedInstruction[1], getValue(splittedInstruction[1]) / getValue(splittedInstruction[2]));
			break;
		case "mod":
			variables.put(splittedInstruction[1], getValue(splittedInstruction[1]) % getValue(splittedInstruction[2]));
			break;
		case "eql":
			variables.put(splittedInstruction[1], getValue(splittedInstruction[1]) == getValue(splittedInstruction[2]) ? 1L : 0L);
			break;
		}
	}
}
