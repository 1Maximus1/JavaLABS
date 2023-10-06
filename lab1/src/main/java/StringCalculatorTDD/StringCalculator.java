package StringCalculatorTDD;


public class StringCalculator
{
	public int add(String numbers)
	{
		if(numbers.isEmpty()){
			return 0;
		}
		String numbersWithoutSpaces = numbers.replaceAll("\\s", "");
		String[] numbers1 = numbers.split(",");
		int sum = 0;

			for (String number : numbers1) {
					int num = Integer.parseInt(number.trim());
					sum += num;
			}

		return sum;

	}
}
