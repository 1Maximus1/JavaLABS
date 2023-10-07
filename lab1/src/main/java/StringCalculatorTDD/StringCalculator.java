package StringCalculatorTDD;


public class StringCalculator
{
	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		}


		String delimiter = "";
		if (numbers.startsWith("//")){
			delimiter = numbers.substring(2,3);
			numbers = numbers.substring(numbers.indexOf("\n") + 1);

		}else {
			delimiter="[,\\n]";
		}

		if (numbers.contains(",\n") || numbers.contains("\n,") || numbers.contains(",,") || numbers.contains("\n\n")) {
			throw new IllegalArgumentException("Error: Two delimiters are following each other!");
		}

		String[] numbersArray = numbers.split(delimiter);

		int sum = 0;

		for (String number : numbersArray) {
			if (!number.trim().isEmpty()) {
				int num = Integer.parseInt(number.trim());
				sum += num;
			}
		}


		return sum;
		}
	}


