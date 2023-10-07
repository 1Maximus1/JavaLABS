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
	public void testMultipleNumbersWithDotAndCommaAndSpecifiedSeparator() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("//;\n1;2");
		assertEquals(3, result);
	}

	@Test
	public void testMultipleNumbersWithSpacesAndSpecifiedSeparator() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("//\n\n1\n2\n3\n50");
		assertEquals(56, result);
	}

	@Test
	public void testMultipleNegativeNumbers() {
		StringCalculator calculator = new StringCalculator();
		String input = ("-1,-3,-4,-5,3,1");
		assertThrows(IllegalArgumentException.class, () -> calculator.add(input));
	}

	@Test
	public void testMultipleNegativeNumbersWithSpaces() {
		StringCalculator calculator = new StringCalculator();
		String input = ("1\n-3\n-4,-5,3,1");
		assertThrows(IllegalArgumentException.class, () -> calculator.add(input));
	}
	@Test
	public void testMultipleNegativeNumbersAndSpecifiedSeparator() {
		StringCalculator calculator = new StringCalculator();
		String input = ("//\n\n1\n-12\n3\n-50");
		assertThrows(IllegalArgumentException.class, () -> calculator.add(input));
	}

	@Test
	public void testMultipleNumbersWith1000() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("999,1000,1001,100000");
		assertEquals(1999, result);
	}
	@Test
	public void testMultipleNumbersAndSpecifiedSeparatorWith1000() {
		StringCalculator calculator = new StringCalculator();
		int result = calculator.add("//\n\n1\n12\n3\n50\n1000\n10012");
		assertEquals(1066, result);
	}

	@Test
	void testCustomDelimiterThatConsistOfThreeCharacters() {
		StringCalculator calculator = new StringCalculator();
		int actual = calculator.add("//[+++]\n5+++3+++6");
		Assertions.assertEquals(14, actual);
	}

	@Test
	void testCustomDelimiterThatConsistOfSixCharacters() {
		StringCalculator calculator = new StringCalculator();
		int actual = calculator.add("//[******]\n5******3******6******4");
		Assertions.assertEquals(18, actual);
	}
	@Test
	void testCustomDelimiterThatConsistOfThreeCharacters2() {
		StringCalculator calculator = new StringCalculator();
		int actual = calculator.add("//[***]\n1***2***3");
		Assertions.assertEquals(6, actual);
	}
	@Test
	void testCustomDelimiterThatConsistOfTwoCharactersWithComma() {
		StringCalculator calculator = new StringCalculator();
		int actual = calculator.add("//[,,]\n10,,10,,10,,10");
		Assertions.assertEquals(40, actual);
	}
	@Test
	void testCustomDelimiterThatConsistOfTwoCharactersWithShifts() {
		StringCalculator calculator = new StringCalculator();
		int actual = calculator.add("//[\n\n]\n10\n\n10\n\n10\n\n10");
		Assertions.assertEquals(40, actual);
	}
	@Test
	public void throwIllegalArgumentExceptionIfDelimiterFollowByAnotherDelimiter() {
		StringCalculator calculator = new StringCalculator();
		String input = ("//[++]\n4++18,5++4++,2");
		assertThrows(IllegalArgumentException.class, () -> calculator.add(input));
	}
}
