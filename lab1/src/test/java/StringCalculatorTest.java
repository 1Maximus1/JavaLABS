import StringCalculatorTDD.StringCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest
{
	private StringCalculator stringCalculator;

	@BeforeEach
	public void setUp() throws Exception {
		stringCalculator = new StringCalculator();
	}

	@Test
	public void testEmptyString() {
		int result = stringCalculator.add("");
		assertEquals(0, result);
	}

	@Test
	public void testSingleNumber() {
		
		int result = stringCalculator.add("5");
		assertEquals(5, result);
	}

	@Test
	public void testTwoNumbers() {
		int result = stringCalculator.add("3,7");
		assertEquals(10, result);
	}

	@Test
	public void testTwoNumbersWithSpaces(){
		assertEquals(160, stringCalculator.add(" 60 , 100"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"66,8,9,4,12,25,1", "41,41\n41,2", "100, 20\n1\n1,1\n 1,1"})
	public void testManyNumbersWithSpacesShiftsCommas(String input){
		assertEquals(125, stringCalculator.add(input));
	}

	@ParameterizedTest
	@ValueSource(strings = {"1\n\n2", "1\n,2", "1,\n2", "1,,2"})
	public void throwExceptionOnTwoDelimiterInARow(String input){
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> stringCalculator.add(input));
		assertEquals("Error: Two delimiters are following each other!", exception.getMessage());
	}

	@Test
	public void testMultipleNumbers() {
		int result = stringCalculator.add("1\n2,3,4\n5");
		assertEquals(15, result);
	}

	@Test
	public void testMultipleNumbersWithDotAndCommaAndSpecifiedSeparator() {
		int result = stringCalculator.add("//;\n1;2");
		assertEquals(3, result);
	}

	@Test
	public void testMultipleNumbersWithShiftsAndSpecifiedSeparator() {
		int result = stringCalculator.add("//\n\n1\n2\n3\n50");
		assertEquals(56, result);
	}

	@Test
	public void testMultipleNumbersWithQuestionMarkAndSpecifiedDelimiter() {
		int result = stringCalculator.add("//?\n1?2?3\n50,23");
		assertEquals(79, result);
	}

	@ParameterizedTest
	@ValueSource(strings = {"//;\n;1;2", ",1\n2", "\n1,2", "1+2","/;\n1;2"})
	public void throwIllegalArgumentExceptionMultipleNumbersWithSpecifiedDelimiterAndIncorrectInput(String input){
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
	}

	@ParameterizedTest
	@ValueSource(strings = {"//;\n;1;2", "//?\n?1?2?2", "//\n\n\n\n\n1\n\n2"})
	public void throwIllegalArgumentExceptionMultipleNumbersWithSpecifiedDelimiterAndIncorrectInput_Text(String input){
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> stringCalculator.add(input));
		assertEquals("Error: Incorrect input(check template)!", exception.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"//;\n1;2;", "//?\n1?2?2?", "//A\n1A2A"})
	public void throwIllegalArgumentExceptionMultipleNumbersWithSpecifiedDelimiter_Text(String input){
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> stringCalculator.add(input));
		assertEquals("Error: Incorrect input(ends with delimiter)!", exception.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"//;;\n1;;22;;1", "//??\n1??2??2??", "//AA\n1AA2AA"})
	public void throwIllegalArgumentExceptionMultipleNumbersWithRepeatedSpecifiedDelimiter_Text(String input){
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> stringCalculator.add(input));
		assertEquals("Error: Incorrect input(check template)!", exception.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"/;\n1;22;1", "/?\n1?2?2?", "/A\n1A2A"})
	public void throwIllegalArgumentExceptionMultipleNumbersWithIncorrectInput_Text(String input){
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> stringCalculator.add(input));
		assertEquals("Error: Incorrect input!(Expression starts with \"//\" by template)", exception.getMessage());
	}

	@Test
	public void testMultipleNegativeNumbers() {
		String input = ("-1,-3,-4,-5,3,1");
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
	}

	@Test
	public void testMultipleNegativeNumbersWithShifts() {
		String input = ("1\n-3\n-4,-5,3,1");
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
	}

	@Test
	public void testMultipleNegativeNumbersAndSpecifiedSeparator() {
		String input = ("//\n\n1\n-12\n3\n-50");
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
	}

	@Test
	public void testMultipleNumbersWith1000() {
		int result = stringCalculator.add("999,1000,1001,100000");
		assertEquals(1999, result);
	}

	@Test
	public void testMultipleNumbersAndSpecifiedSeparatorWith1000() {
		int result = stringCalculator.add("//^\n1^12^3^50^1000^10012");
		assertEquals(1066, result);
	}

	@Test
	void testCustomDelimiterThatConsistOfThreeCharacters() {
		int result = stringCalculator.add("//[+++]\n5+++3+++6");
		Assertions.assertEquals(14, result);
	}

	@Test
	void testCustomDelimiterThatConsistOfThreeCharacters_2() {
		int result = stringCalculator.add("//[***]\n1***2***3");
		Assertions.assertEquals(6, result);
	}

	@Test
	void testCustomDelimiterThatConsistOfSixCharacters() {
		int result = stringCalculator.add("//[******]\n5******3******6******4");
		Assertions.assertEquals(18, result);
	}

	@Test
	void testCustomDelimiterThatConsistOfTwoCharactersWithCommas() {
		int result = stringCalculator.add("//[,,]\n10,,10,,10,,10");
		Assertions.assertEquals(40, result);
	}

	@Test
	void testCustomDelimiterThatConsistOfTwoCharactersWithShifts() {
		int result = stringCalculator.add("//[\n\n]\n10\n\n10\n\n10\n\n10");
		Assertions.assertEquals(40, result);
	}

	@Test
	void testCustomThreeDelimitersThatConsistOfTCharactersOneOrTwo() {
		int result = stringCalculator.add("//[**][,][+]\n10**10,10+10");
		Assertions.assertEquals(40, result);
	}

	@Test
	void testCustomFourDelimitersThatConsistMultipleCharacters_1() {
		int result = stringCalculator.add("//[***][\n\n][+][..]\n10***10\n\n10,10+1000..11..22\n\n11..22+1110");
		Assertions.assertEquals(1106, result);
	}

	@Test
	public void throwIllegalArgumentExceptionIfDelimiterFollowByAnotherDelimiter() {
		String input = ("//[++]\n4++18,5++4++,2");
		assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(input));
	}

	@Test
	void throwIllegalArgumentExceptionIfDelimiterFollowByAnotherDelimiter_Text() {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			stringCalculator.add("//[***][\n]\n4***18\n***5***4***2");
		});

		assertEquals("Error: Two delimiters are following each other!", exception.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"//[***][\n1,2", "//][AD]\n1,2", "//[[]]\n1,2","//[[[]]]\n1,2", "//[\n1,2,2,3", "//][[]\n1,2,2,3", "//]\n1,2,2,1,2,3"})
	public void throwCustomIllegalArgumentExceptionIfDelimiterInvalidInput(String input){
	assertThrows(IllegalArgumentException.class,() -> stringCalculator.add(input));
	}

	@Test
	public void testCustomFourDelimitersThatConsistMultipleCharacters_2(){
		assertEquals(19, stringCalculator.add("//[*][%]\n2*3%7,6\n1"));
	}

	@Test
	public void testCustomFourDelimitersThatConsistMultipleCharacters_3(){
		assertEquals(20, stringCalculator.add("//[*][%][&]\n2*3%7&1,6\n1"));
	}

	@Test
	public void testCustomFourDelimitersThatConsistMultipleCharacters_4(){
		assertEquals(41, stringCalculator.add("//[+++][%][%%][&][***]\n2***3%%7&1,6\n1%1+++20"));
	}

	@Test
	public void testCustomFourDelimitersThatConsistMultipleCharacters_5(){
		assertEquals(78, stringCalculator.add("//[????][%]\n2????3%1,6\n1,65"));
	}

	@Test
	public void testCustomFourDelimitersThatConsistMultipleCharacters_6(){
		assertEquals(1004,stringCalculator.add("//[+][+++][++]\n1+++1001+1\n1++1,1000"));
	}

}
