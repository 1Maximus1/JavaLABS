import StringCalculatorTDD.StringCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		int result = calculator.add("3,7,4,5");
		assertEquals(19, result);
	}


	@Test
	public void testIllegalArgumentException() {
		StringCalculator calculator = new StringCalculator();
		String input = "1,\n2";
		assertThrows(IllegalArgumentException.class, () -> calculator.add(input));
	}

	@Test
	public void testMultipleNumbers() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("1\n2,3,4\n5");
		assertEquals(15, result);
	}

	@Test
	public void testMultipleNumbersWithDotAndComma() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("//;\n1;2");
		assertEquals(3, result);
	}

	@Test
	public void testMultipleNumbersWithSpaces() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("//\n\n1\n2\n3\n50");
		assertEquals(56, result);
	}

}
