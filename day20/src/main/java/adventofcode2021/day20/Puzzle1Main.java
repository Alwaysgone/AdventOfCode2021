package adventofcode2021.day20;

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
		try(BufferedReader reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().resolve("puzzle20_input.txt").toFile()))) {
			String enhancementAlgo = reader.readLine();
			String line;
			List<char[]> lines = new LinkedList<>();
			while((line = reader.readLine()) != null) {
				if(line.isBlank()) {
					continue;
				}
				lines.add(line.toCharArray());
			}
			char[][] image = new char[lines.size()][lines.get(0).length];
			for(int i = 0; i < lines.size(); i++) {
				image[i] = lines.get(i);
			}
			
			int iterations = 2;
			for(int i = 0; i < iterations; i++) {
				boolean outOfBoundsLit = enhancementAlgo.charAt(0) == '#' && i % 2 != 0;
				image = addBorders(image, outOfBoundsLit);
				image = enhanceImage(enhancementAlgo, image, outOfBoundsLit);
			}
			int dimension = image.length;
			final char[][] finalImage = image;
			int lightPixelSum = IntStream.range(0, dimension)
			.flatMap(i -> IntStream.range(0, dimension)
					.map(j -> finalImage[i][j] == '#' ? 1 : 0))
			.sum();
			System.out.println("Number of light pixels: " + lightPixelSum);
		}
	}

	private static char[][] addBorders(char[][] image, boolean outOfBoundsLit) {
		char[][] imageWithBorders = new char[image.length + 2][image.length + 2];
		char[] firstLine = IntStream.range(0, imageWithBorders.length)
		.mapToObj(i -> outOfBoundsLit ? "#" : ".")
		.collect(Collectors.joining())
		.toCharArray();
		
		imageWithBorders[0] = firstLine;
		
		for(int i = 1; i < image.length + 1; i++) {
			char[] imageLine = image[i - 1];
			char[] imageLineWithBorder = new char[imageLine.length + 2];
			System.arraycopy(imageLine, 0, imageLineWithBorder, 1, imageLine.length);
			imageLineWithBorder[0] = outOfBoundsLit ? '#' : '.';
			imageLineWithBorder[imageLineWithBorder.length - 1] = outOfBoundsLit ? '#' : '.';
			imageWithBorders[i] = imageLineWithBorder;
		}
		
		char[] lastLine = Arrays.copyOf(firstLine, firstLine.length);
		imageWithBorders[imageWithBorders.length - 1] = lastLine;
		return imageWithBorders;
	}
	
	private static char[][] enhanceImage(String enhancementAlgo, char[][] image, boolean outOfBoundsLit) {
		char[][] enhancedImage = new char[image.length][image.length];
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image.length; j++) {
				enhancedImage[i][j] = getEnhancedPixel(image, enhancementAlgo, i, j, outOfBoundsLit);
			}
		}
		return enhancedImage;
	}

	private static char getEnhancedPixel(char[][] image, String enhancementAlgo, int i, int j, boolean outOfBoundsLit) {
		StringBuilder indexBuilder = new StringBuilder()
		.append(getValueAt(image, i - 1, j - 1, outOfBoundsLit))
		.append(getValueAt(image, i - 1, j, outOfBoundsLit))
		.append(getValueAt(image, i - 1, j + 1, outOfBoundsLit))
		.append(getValueAt(image, i, j - 1, outOfBoundsLit))
		.append(getValueAt(image, i, j, outOfBoundsLit))
		.append(getValueAt(image, i, j + 1, outOfBoundsLit))
		.append(getValueAt(image, i + 1, j - 1, outOfBoundsLit))
		.append(getValueAt(image, i + 1, j, outOfBoundsLit))
		.append(getValueAt(image, i + 1, j + 1, outOfBoundsLit))
		;
		if(indexBuilder.toString().length() != 9) {
			System.out.println("here");
		}
		int enhacementIndex = Integer.parseInt(indexBuilder.toString(), 2);
		return enhancementAlgo.charAt(enhacementIndex);
	}
	
	private static int getValueAt(char[][] image, int i, int j, boolean outOfBoundsLit) {
		if(i < 0 || i >= image.length || j < 0 || j >= image.length) {
			return outOfBoundsLit ? 1 : 0;
		}
		return image[i][j] == '#' ? 1 : 0;
	}
}
