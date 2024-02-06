package com.spark.one.onespark;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spark.one.onespark.SolutionConstants.*;

public class ReadInputAndProcess {

    public static final String NO_IDEA = "I have no idea what you are talking about";

    private static Map<String, RomanNumbers.Roman> interGalacticRomanMapping = new HashMap<>();
    private static Map<String, Double> objectSoldPerUnitValue = new HashMap<>();

    @Value("#{${OBJECTS_SOLD_PER_UNIT_MAP}}")
    private Map<String, String> objectsSoldPerUnitMap;

    /**
     * reads and process the input file
     * @param inputStream
     */
    public static void readFileAndProcess(InputStream inputStream) throws Exception{

        try(BufferedReader br = new BufferedReader(new BufferedReader(new InputStreamReader(inputStream)))) {

            String line;

            while((line = br.readLine()) != null)
            {
                processFile(line);
            }

        } catch (IOException e) {
            System.out.println("File not found exception " + e);
        }
    }

    /**
     * Parses the input line by line and decides the type of request and appropriately forwards the request
     * @param line
     * @throws Exception
     */
    private static void processFile(String line) throws Exception{
        //split by whitespace
        line = line.toLowerCase();
        String[] inputs = line.split("\\s+");
        List<String> inputQuestions = DisplayOutput.inputQuestions;
        List<String> outputValues = DisplayOutput.outputValues;

        if(line.startsWith(HOW_MANY_CREDITS_IS)){
            inputQuestions.add(line);
            outputValues.add(String.valueOf(generateCreditValue(Arrays.copyOfRange(inputs, 4, inputs.length-1))));
        }
        else if(line.startsWith(HOW_MUCH_IS)){
            inputQuestions.add(line);
            outputValues.add(String.valueOf(generateGalacticUnitToNumericValue(Arrays.copyOfRange(inputs, 3, inputs.length-1))));
        }
        else if(line.contains(IS) && !line.contains(CREDITS))
            mapInterGalacticToRomanUnits(inputs);
        else if(line.contains(IS) && line.contains(CREDITS))
            generateObjectSoldPerUnitMap(inputs);
        else{
            inputQuestions.add(line);
            outputValues.add(NO_IDEA);
        }
    }

    private static void mapInterGalacticToRomanUnits(String[] arr){
        try{
            interGalacticRomanMapping.put(arr[0], RomanNumbers.Roman.valueOf(arr[2]));
        }
        catch(IllegalArgumentException e){
            System.out.println("This type of Roman is not defined, exiting the program " + e);
        }
    }

    private static int generateObjectSoldPerUnitMap(String[] arr) throws Exception{
        StringBuilder romanNumeral = new StringBuilder();
        int i;
        for(i = 0; i <arr.length; i++){
            RomanNumbers.Roman roman = interGalacticRomanMapping.get(arr[i]);
            if(roman != null){
                romanNumeral.append(roman.getDisplayValue());
            }
            else
                break;
        }

        int value = RomanValidator.validateRoman(romanNumeral.toString());
        if(value == -1)
            return -1;
        String objectName = arr[i];
        int credit = Integer.parseInt(arr[i + 2]);

        objectSoldPerUnitValue.put(objectName, (double)credit/value);
        return 1;
    }

    /**
     * Receives input of the form "pish tegj glob glob" for questions like "how much is pish tegj glob glob ?"
     * returns -1 in case of validation exception
     */
    private static int generateGalacticUnitToNumericValue(String[] arr){

        try {
            String romanNumeral = generateRomanFromGalacticUnit(arr);

            if(romanNumeral == null)
                return -1;

            return  RomanValidator.validateRoman(romanNumeral.toString());
        } catch (Exception e) {
            return -1;
        }

    }


    /**
     * Receives input of the form "glob prok Silver" for questions like "how many Credits is glob prok Silver ?"
     * returns -1 in case of validation exception
     * @param arr
     * @return
     * @throws Exception
     */
    private static Double generateCreditValue(String[] arr){
        int currentValue = generateGalacticUnitToNumericValue(Arrays.copyOfRange(arr, 0, arr.length-1));

        if(currentValue == -1)
            return (double)-1;

        return currentValue * objectSoldPerUnitValue.get(arr[arr.length-1]);
    }

    /**
     * a utils method to generate Roman string from Alien input
     * @param arr
     * @return
     */
    private static String generateRomanFromGalacticUnit(String[] arr){
        StringBuilder romanNumeral = new StringBuilder();
        int i;
        for(i = 0; i< arr.length; i++){
            RomanNumbers.Roman roman = interGalacticRomanMapping.get(arr[i]);
            if(roman != null){
                romanNumeral.append(roman.getDisplayValue());
            }
            else
                break;
        }

        if(i != arr.length)
            return null;

        return romanNumeral.toString();
    }
}