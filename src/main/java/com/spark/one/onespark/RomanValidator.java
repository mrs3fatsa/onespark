package com.spark.one.onespark;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class RomanValidator {

    private RomanValidator(){

    }

    private static final Set<Character> THREE_TIMES_REPEATED_CHARACTERS = new HashSet<>();
    private static final Set<Character> NOT_SUBTRACTED_CHARACTERS = new HashSet<>();

    @Value("#{${ROMAN_SYMBOLS}}")
    private Map<String, String> romanSymbols;

    static {
        THREE_TIMES_REPEATED_CHARACTERS.add('i');
        THREE_TIMES_REPEATED_CHARACTERS.add('x');
        THREE_TIMES_REPEATED_CHARACTERS.add('c');
        THREE_TIMES_REPEATED_CHARACTERS.add('m');

        NOT_SUBTRACTED_CHARACTERS.add('v');
        NOT_SUBTRACTED_CHARACTERS.add('l');
        NOT_SUBTRACTED_CHARACTERS.add('d');
        NOT_SUBTRACTED_CHARACTERS.add('m');
    }

    /**
     * Validate the input RomanNumber and return it's integer value if valid, else return -1
     */
    public static int validateRoman(String romanNumber) throws Exception {
        char[] charArray = romanNumber.toCharArray();
        char previousChar = ' ';

        int characterRepeatCount = 1;
        int total = 0;
        int previousCharacterOrdinal = Integer.MAX_VALUE;
        int currentCharacterOrdinal;

        for (char currentChar : charArray) {
            int currentRomanCharNumericValue = RomanNumbers.Roman.valueOf(String.valueOf(currentChar)).getValue();

            if (previousChar != ' ') {
                previousCharacterOrdinal = RomanNumbers.Roman.valueOf(String.valueOf(previousChar)).ordinal();
            }
            currentCharacterOrdinal = RomanNumbers.Roman.valueOf(String.valueOf(currentChar)).ordinal();

            if (currentChar == previousChar && ++characterRepeatCount < 4 && THREE_TIMES_REPEATED_CHARACTERS.contains(currentChar)) {
                total += currentRomanCharNumericValue;
            } else if (characterRepeatCount > 3) {
                total = -1;
            } else if (currentChar == previousChar && !THREE_TIMES_REPEATED_CHARACTERS.contains(currentChar)) {
                total = -1;
            } else if (previousCharacterOrdinal < currentCharacterOrdinal && !NOT_SUBTRACTED_CHARACTERS.contains(previousChar)) {
                int previousRomanCharNumericValue = RomanNumbers.Roman.valueOf(String.valueOf(previousChar)).getValue();
                if (previousCharacterOrdinal + 2 >= currentCharacterOrdinal) {
                    total += currentRomanCharNumericValue - (2 * previousRomanCharNumericValue);
                    characterRepeatCount = 1;
                } else {
                    total = -1;
                }
            } else if (previousCharacterOrdinal < currentCharacterOrdinal && NOT_SUBTRACTED_CHARACTERS.contains(previousChar)) {
                total = -1;
            } else {
                characterRepeatCount = 1;
                total += currentRomanCharNumericValue;
            }
            previousChar = currentChar;
            if (total == -1)
                break;
        }
        return total;
    }
}