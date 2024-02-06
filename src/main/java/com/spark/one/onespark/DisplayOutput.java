package com.spark.one.onespark;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.spark.one.onespark.SolutionConstants.*;

@Component
public class DisplayOutput {

    private DisplayOutput(){

    }

    //these two lists hold question asked and out for same indexes
    //this will hold all the questions asked in the input
    public static final List<String> inputQuestions = new ArrayList<>();
    //this holds the output of the questions and special values if the question was not valid or a validation failure occurs
    public static final List<String> outputValues = new ArrayList<String>();

    /**
     * Display the output based on the lists
     */
    public static void processOutput(){

        for (int i = 0; i < inputQuestions.size(); i++) {
            StringBuilder result = new StringBuilder();

            String question = inputQuestions.get(i);
            String output = outputValues.get(i);

            if(Objects.equals(output, ReadInputAndProcess.NO_IDEA) || Double.valueOf(output).intValue() == -1){
                result.append(ReadInputAndProcess.NO_IDEA);
            }
            else {
                if (question.startsWith(HOW_MUCH_IS)) {
                    result.append(question.substring(HOW_MUCH_IS.length(), question.length()-1).trim());
                    result.append(IS);
                    result.append(Double.valueOf(output).intValue());
                }

                else if(question.startsWith(HOW_MANY_CREDITS_IS)){
                    result.append(question.substring(HOW_MANY_CREDITS_IS.length(), question.length()-1).trim());
                    result.append(IS);
                    result.append(Double.valueOf(output).intValue());
                    result.append(CREDITS);
                }
                else
                    result.append(ReadInputAndProcess.NO_IDEA);
            }

            System.out.println(result);
        }
    }
}