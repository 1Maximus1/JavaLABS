package StringCalculatorTDD;


import java.util.ArrayList;

public class StringCalculator {
	public int add(String numbers) {
		ArrayList<Integer> negativeNumbers = new ArrayList<>();

		if (numbers.isEmpty()) {
			return 0;
		}

		String delimiter = "";
		if (numbers.startsWith("//")) {
			delimiter = numbers.substring(2, 3);
			numbers = numbers.substring(numbers.indexOf("\n") + 1);

		} else {
			delimiter = "[,\\n]";
		}

		if (numbers.contains(",\n") || numbers.contains("\n,") || numbers.contains(",,") || numbers.contains("\n\n")) {
			throw new IllegalArgumentException("Error: Two delimiters are following each other!");
		}

		String[] numbersArray = numbers.split(delimiter);

		int sum = 0;

		for (String number : numbersArray) {
			if (!number.trim().isEmpty()) {
				int num = Integer.parseInt(number.trim());
				if (num < 0) {
					negativeNumbers.add(num);
				} else if (num>1000){
					continue;
				}
				if (negativeNumbers.isEmpty()){
					sum += num;
				}

			}
		}

		if (negativeNumbers.isEmpty()) {
			return sum;
		}
		else {
			StringBuilder message = new StringBuilder("Error: negative numbers are not allowed: ");
			for (int i = 0; i < negativeNumbers.size(); i++) {
				message.append(negativeNumbers.get(i));
				if (i < negativeNumbers.size() - 1) {
					message.append(", ");
				}
			}
			throw new IllegalArgumentException(message.toString());
		}
	}
}


