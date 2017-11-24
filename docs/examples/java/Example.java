import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Example {
	
	public static void main(String[] args) {
		
		log("Program started.");
			
		log("Basic Numbering Example:");
		for(int i = 0; i < 5; i++) {
			log("  The number is: " + i);
		}
		
		log("Filtering Example:");
		List<String> food = new ArrayList<>(Arrays.asList("Pizza", "Ice Cream", "Pineapple", "Sandwich"));
		List<String> pFood = food.stream().filter(f -> f.startsWith("P")).collect(Collectors.toList());
		pFood.forEach(f -> log("  " + f));
		
		log("Your arguments:");
		for(String argument : args) {
			log("  " + argument);
		}
		
		log("Program ended.");
		
	}
	
	private static void log(String message) {
		System.out.println(message);
	}
	
}