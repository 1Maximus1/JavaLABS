import StringCalculatorTDD.StringCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest
{

	@Test
	public void testEmptyString() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("");
		assertEquals(0, result);
	}

	@Test
	public void testSingleNumber() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("5");
		assertEquals(5, result);
	}

	@Test
	public void testTwoNumbers() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("3,7");
		assertEquals(10, result);
	}

	@Test
	public void testMultipleNumbers() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("1,2,3,4,5");
		assertEquals(15, result);
	}

	@Test
	public void testInvalidInput() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("1,2,3");
		assertEquals(6, result);
	}

	@Test
	public void testIgnoreNumbersGreaterThan2() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add(" 1, 2 , 3 ,40   ");
		assertEquals(46, result);
	}


}
