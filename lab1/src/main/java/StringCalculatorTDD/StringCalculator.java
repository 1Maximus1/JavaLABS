package StringCalculatorTDD;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	public int add(String numbers) {
		ArrayList<Integer> negativeNumbers = new ArrayList<>();

		if (numbers.isEmpty()) {
			return 0;
		}

		if (numbers.startsWith("//[")){
			numbers = numbers.replaceAll("\n", ",");
		}

		String delimiter = "[,\\n]";

		if (numbers.startsWith("//"))
		{
			Matcher matcher = Pattern.compile("\\[(.*?)\\](\\n|,)").matcher(numbers);
			if (matcher.find()) {
				delimiter = Pattern.quote(matcher.group(1));
				numbers = numbers.substring(matcher.end());
			} else {
				delimiter = String.valueOf(numbers.charAt(2));
				numbers = numbers.substring(3);
			}
		}

		if (numbers.contains(",\n") || numbers.contains("\n,")) {
			throw new IllegalArgumentException("Error: Two delimiters are following each other!");
		}

		String[] numbersArray = numbers.split(delimiter);


		int sum = 0;

		for (String number : numbersArray) {
			if (!number.trim().isEmpty()) {
				int num;
				try {
					num = Integer.parseInt(number.trim());
				}catch (IllegalArgumentException e){
					throw new IllegalArgumentException("Error: Two delimiters are following each other!");
				}
				if (num < 0) {
					negativeNumbers.add(num);
				} else if (num > 1000) {
					continue;
				}
				if (negativeNumbers.isEmpty()) {
					sum += num;
				}

			}
		}

		if (negativeNumbers.isEmpty()) {
			return sum;
		} else {
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


