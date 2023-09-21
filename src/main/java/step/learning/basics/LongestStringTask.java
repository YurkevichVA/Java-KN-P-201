package step.learning.basics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class LongestStringTask {
    public void run() {
        String filename = "test.txt";
        Random random = new Random();

        Scanner kbScanner = new Scanner(System.in);
        System.out.print("Enter max characters count: ");

        int maxLetterCount = Integer.parseInt(kbScanner.next());

        int leftLimit = 20;
        int rightLimit = 127;

        try (FileWriter writer = new FileWriter(filename, true)){
            for(int j = 0; j < 100; j++) {
                int targetStringLength = random.nextInt(maxLetterCount);

                StringBuilder buffer = new StringBuilder(targetStringLength);
                for (int i = 0; i < targetStringLength; i++) {
                    int randomLimitedInt = leftLimit + (int)
                            (random.nextFloat() * (rightLimit - leftLimit + 1));
                    buffer.append((char) randomLimitedInt);
                }
                String generatedString = buffer.toString();
                writer.write(generatedString + "\n");
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        String longestString = "";
        int longestStringNumber = 0;

        try(Scanner scanner = new Scanner(new File(filename))) {
            int counter = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.length() > longestString.length()) {
                    longestString = line;
                    longestStringNumber = counter;
                }
                counter++;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.printf("найдовший рядок номер %d з довжиною %d символ: %s", longestStringNumber, longestString.length(), longestString);
    }
}
