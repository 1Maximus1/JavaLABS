package StringCalculatorTDD;


public class StringCalculator
{
	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		}

		if (numbers.contains(",\n") || numbers.contains("\n,")) {
			return -1;
		}

		String[] numbersArray = numbers.split("[,\n]");
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


