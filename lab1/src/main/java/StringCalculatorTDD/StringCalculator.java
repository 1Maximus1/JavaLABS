package StringCalculatorTDD;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	public int add(String numbers) {
		ArrayList<Integer> negativeNumbers = new ArrayList<>();
		ArrayList<String> allDelimiters = new ArrayList<>();
		String delimiter = "[,\\n]";

		if (numbers.isEmpty()) {
			return 0;
		}

		checkCharactersStartAndEnd(numbers);
		throwCustomDelimeterInvalidInputException(numbers);
		if (numbers.startsWith("//["))
		{
			numbers = numbers.replaceAll("\n", ",");
		} else if (numbers.startsWith("//]")) {
			throw new IllegalArgumentException("Error: Incorrect custom delimiter!");
		}


		Matcher matcher = Pattern.compile("\\[(.*?)\\]").matcher(numbers);

		String[] numbersArrayFin;
		if (checkCharactersStartAndEnd(numbers))
		{


			while (matcher.find())
			{
				String delimiter2 = Pattern.quote(matcher.group(1));
				allDelimiters.add(delimiter2);
			}

			if (!allDelimiters.isEmpty()){
				Collections.sort(allDelimiters, new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if(o1.length()>o2.length()){
							return -1;
						}
						if (o1.length()<o2.length()){
							return 1;
						}
						return 0;
					}
				});
			}



			Matcher matcher0 = Pattern.compile("\\[(.*?)\\](\\n|,)").matcher(numbers);
			if (matcher0.find()) {
				numbers = numbers.substring(matcher0.end());
			}


			if (!allDelimiters.isEmpty() && allDelimiters.toArray().length > 1)
			{
				for (String allDelimiter : allDelimiters) {
					numbers = numbers.replaceAll(allDelimiter, ",");
				}

				numbersArrayFin = numbers.split(",");
				checkDuplicateOfCommas(numbers);
				int sum = sumOfStrings(numbersArrayFin,negativeNumbers);
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



			} else if (allDelimiters.toArray().length == 1) {

				for (String allDelimiter : allDelimiters) {
					numbers = numbers.replaceAll(allDelimiter, ",");
				}

			}else{
				String delimiter2 = Pattern.quote(String.valueOf(numbers.charAt(2)));
				numbers = numbers.substring(4);
				checkCharactersStartAndEnd(numbers);
				numbers = numbers.replaceAll("\n",",");
				numbers = numbers.replaceAll(delimiter2,",");
				checkDuplicateOfCommas(numbers);
			}
		}

		if (numbers.contains(",\n") || numbers.contains("\n,") || numbers.contains(",,") || numbers.contains("\n\n")) {
			throw new IllegalArgumentException("Error: Two delimiters are following each other!");
		}

		checkCharactersStartAndEnd(numbers);
		String[] numbersArray = numbers.split(delimiter);

		int sum = sumOfStrings(numbersArray,negativeNumbers);

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

	public static int sumOfStrings(String[] stringNumbersArray, ArrayList<Integer> negativeNumbers){
		int sum = 0;
		for (String number : stringNumbersArray) {
			if (!number.trim().isEmpty()) {
				int num;
				try {
					num = Integer.parseInt(number.trim());
				}catch (IllegalArgumentException e){
					throw new IllegalArgumentException("Error: No specified delimiter exists!");
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
		return sum;
	}
	public static void checkDuplicateOfCommas(String numbers) {
		int counterOfDuplicate = 0;
		for (int i = 0; i < numbers.length(); i++) {
			if(numbers.charAt(i) != ','){
				counterOfDuplicate = 0;
			}else{
				counterOfDuplicate++;
			}
			if (counterOfDuplicate >= 2) {
				throw new IllegalArgumentException ("Error: Two delimiters are following each other!");
			}
		}

	}

	public static boolean checkCharactersStartAndEnd(String numbers) {
		if (numbers.startsWith("/")) {
			if (numbers.startsWith("//")){
				return true;
			}else {
				throw new IllegalArgumentException("Error: Incorrect input!(Expression starts with \"//\" by template)");
			}
		}

		if (!numbers.startsWith("//")) {
			if ((!Character.isDigit(numbers.charAt(0)) && !Character.isWhitespace(numbers.charAt(0))) || numbers.charAt(0) == '\n') {
				throw new IllegalArgumentException("Error: Incorrect input(check template)!");
			}

			char lastChar = numbers.charAt(numbers.length() - 1);
			if ((!Character.isDigit(lastChar) && !Character.isWhitespace(lastChar)) || lastChar == '\n') {
				throw new IllegalArgumentException("Error: Incorrect input(ends with delimiter)!");
			}
		}else{
			if ((!Character.isDigit(numbers.charAt(0)) && !Character.isWhitespace(numbers.charAt(0))) || numbers.charAt(0) == '\n') {
				throw new IllegalArgumentException("Error: Incorrect input(starts with delimiter)!");
			}
			char lastChar = numbers.charAt(numbers.length() - 1);
			if ((!Character.isDigit(lastChar) && !Character.isWhitespace(lastChar)) || lastChar == '\n') {
				throw new IllegalArgumentException("Error: Incorrect input(ends with delimiter)!");
			}
		}

		return false;
	}
	public void throwCustomDelimeterInvalidInputException(String input) {
		if (input.startsWith("//[")) {
			Pattern patternT1 = Pattern.compile("\\[([^\\]]+)\\]");
			Matcher matcherT1 = patternT1.matcher(input);
			Pattern patternT2 = Pattern.compile("\\[([^\\]]+)\\]");
			Matcher matcherT2 = patternT2.matcher(input);
			while (matcherT1.find()) {
				String delimiterT = matcherT1.group(1);
				if (delimiterT.contains("[") || delimiterT.contains("]")) {
					throw new IllegalArgumentException("Error: Invalid delimiter format");
				}
			}
			if(!matcherT2.find()){
				throw new IllegalArgumentException("Error: Invalid delimiter format");
			}

		}
	}


}