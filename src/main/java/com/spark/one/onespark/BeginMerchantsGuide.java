package com.spark.one.onespark;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class BeginMerchantsGuide
{
    public static void main( String[] args ) throws IOException {
        ClassPathResource resource = new ClassPathResource("input.txt");
        InputStream inputStream = resource.getInputStream();
        try {
            ReadInputAndProcess.readFileAndProcess(inputStream);
            DisplayOutput.processOutput();
        }
        catch(Exception e){
            System.out.println("An exception has been raised, Either due to invalid input or due to reading the file, quiting the program " + e);
        }
    }
}