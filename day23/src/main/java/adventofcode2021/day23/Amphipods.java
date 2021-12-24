package adventofcode2021.day23;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Amphipods {
	private static final Map<Character, Integer> ENERGY_USAGE = new HashMap<>(4);
	char[] hallway;
	char[] room1;
	char[] room2;
	char[] room3;
	char[] room4;
	int energyUsed;

	static {
		ENERGY_USAGE.put('A', 1);
		ENERGY_USAGE.put('B', 10);
		ENERGY_USAGE.put('C', 100);
		ENERGY_USAGE.put('D', 1000);
	}

	public Amphipods(char[] hallway, char[] room1, char[] room2, char[] room3, char[] room4, int energyUsed) {
		this.hallway = hallway;
		this.room1 = room1;
		this.room2 = room2;
		this.room3 = room3;
		this.room4 = room4;
		this.energyUsed = energyUsed;
	}

	List<Amphipods> getNextStates(int energyUpperBound) {
		List<Amphipods> amphipods = new LinkedList<>();
		if(isDone()) {
			return amphipods;
		}
		// add all moves that move amphipods out to the hallway
		for(int i = 1; i <= 4; i++) {
			char[] room;
			if(i == 1) {
				room = room1;
			} else if(i == 2) {
				room = room2;
			} else if(i == 3) {
				room = room3;
			} else {
				room = room4;
			}
			for(int j = 0; j < hallway.length; j++) {
				if(j == 2 || j == 4 || j == 6 || j == 8) {
					// illegal position
					continue;
				}

				//check if hallway is blocked
				int steps = 0;
				boolean blocked = false;
				if(j < i * 2) {
					for(int k = j; k <= i * 2; k++) {
						if(hallway[k] != '.') {
							blocked = true;
							break;
						}
						steps++;
					}
				} else {
					for(int k = i * 2; k <= j; k++) {
						if(hallway[k] != '.') {
							blocked = true;
							break;
						}
						steps++;
					}
				}
				if(blocked) {
					continue;
				}

				char[] newHallway = Arrays.copyOf(hallway, hallway.length);
				int roomIndex = -1;
				if(room[1] != '.') {
					roomIndex = 1;
				} else if(room[0] != '.') {
					roomIndex = 0;
				}
				if(roomIndex != -1) {
					int energy;
					if(roomIndex == 1) {
						energy = steps * ENERGY_USAGE.get(room[roomIndex]);
					} else {
						energy = (steps + 1) * ENERGY_USAGE.get(room[roomIndex]);
					}
					int newEnerygUsed = energyUsed + energy;
					if(newEnerygUsed <= energyUpperBound) {
						char[] newRoom = Arrays.copyOf(room, room.length);
						newHallway[j] = room[roomIndex];
						newRoom[roomIndex] = '.';
						if(i == 1) {
							amphipods.add(new Amphipods(newHallway, newRoom, room2, room3, room4, energyUsed + energy));
						} else if(i == 2) {
							amphipods.add(new Amphipods(newHallway, room1, newRoom, room3, room4, energyUsed + energy));
						} else if(i == 3) {
							amphipods.add(new Amphipods(newHallway, room1, room2, newRoom, room4, energyUsed + energy));
						} else {
							amphipods.add(new Amphipods(newHallway, room1, room2, room3, newRoom, energyUsed + energy));
						}
					}
				}
			}
		}
		// check if an amphipod can move into its room
		for(int i = 0; i < hallway.length; i++) {
			if(i == 2 || i == 4 || i == 6 || i == 8) {
				// illegal position
				continue;
			}
			char amphipod = hallway[i];
			if(amphipod != '.') {
				char[] room;
				if(amphipod == 'A') {
					room = room1;
				} else if(amphipod == 'B') {
					room = room2;
				} else if(amphipod == 'C') {
					room = room3;
				} else {
					room = room4;
				}
				
				int roomNumber = 1;
				int roomIndex = -1;
				if(room[0] == '.') {
					//room is empty
					roomIndex = 0;
				} else if(room[0] == amphipod && room[1] == '.') {
					// room already contains the same amphipod and as empty slot
					roomIndex = 1;
				}
				//		}
				if(roomIndex != -1) {
					int steps = 0;
					boolean blocked = false;
					if(i < roomNumber * 2) {
						for(int k = i + 1; k <= roomNumber * 2; k++) {
							if(hallway[k] != '.') {
								blocked = true;
								break;
							}
							steps++;
						}
					} else {
						for(int k = roomNumber * 2; k <= i - 1; k++) {
							if(hallway[k] != '.') {
								blocked = true;
								break;
							}
							steps++;
						}
					}
					if(blocked) {
						continue;
					}
					//steps counter only goes to room entrance
					if(roomIndex == 0) {
						steps += 2;
					} else {
						steps += 1;
					}
					int energy = steps * ENERGY_USAGE.get(amphipod);
					int newEnerygUsed = energyUsed + energy;
					if(newEnerygUsed <= energyUpperBound) {
						char[] newHallway = Arrays.copyOf(hallway, hallway.length);
						char[] newRoom = Arrays.copyOf(room, room.length);
						newRoom[roomIndex] = amphipod;
						newHallway[i] = '.';
						if(roomNumber == 1) {
							amphipods.add(new Amphipods(newHallway, newRoom, room2, room3, room4, energyUsed + energy));
						} else if(roomNumber == 2) {
							amphipods.add(new Amphipods(newHallway, room1, newRoom, room3, room4, energyUsed + energy));
						} else if(roomNumber == 3) {
							amphipods.add(new Amphipods(newHallway, room1, room2, newRoom, room4, energyUsed + energy));
						} else {
							amphipods.add(new Amphipods(newHallway, room1, room2, room3, newRoom, energyUsed + energy));
						}
					}
				}
			}
		}


		return amphipods;
	}

	int getSteps(int room, int hallwayPosition) {
		int steps;
		if(hallwayPosition < room * 2) {
			//hallway position is to the left of the room
			steps = 2 * room - hallwayPosition + 1;
		} else {
			steps = hallwayPosition - 2 * room + 1;
		}
		return steps;
	}

	boolean isDone() {
		return room1[0] == 'A'
				&& room1[1] == 'A'
				&& room2[0] == 'B'
				&& room2[1] == 'B'
				&& room3[0] == 'C'
				&& room3[1] == 'C'
				&& room4[0] == 'D'
				&& room4[1] == 'D'
				;
	}
}
