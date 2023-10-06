package StringCalculatorTDD;


public class StringCalculator
{
	public int add(String numbers)
	{
		if(numbers.isEmpty()){
			return 0;
		}
		String[] numbers1 = numbers.split(",");
		int sum = 0;

		if (numbers1.length <= 2)
		{
			for (String number : numbers1) {
					int num = Integer.parseInt(number.trim());
					sum += num;
			}
		}else{
			return -1;
		}

		return sum;

	}
}
