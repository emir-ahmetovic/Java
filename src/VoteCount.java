import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class VoteCount {
    String title;
    int count;

    public VoteCount(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public void getCount(String fileName) {
        File file = new File(fileName);

        try{

        Scanner s = new Scanner(file);
        s.nextLine();

        while(s.hasNextLine()) {
            String line = s.nextLine();

            if(line.startsWith("\"")){
                try{
                    int endQuoteIndex = findEndingQuote(line);

                    if(endQuoteIndex != -1) {
                        String title = line.substring(1, endQuoteIndex);
                        String[] remainingFields = line.substring(endQuoteIndex + 1).split(",");
                        int votes = Integer.parseInt(remainingFields[2]);
                        if (votes > 15000) {
                            count++;
                        }
                    } else {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                String[] split = line.split(",");

                if (split.length >= 4) {
                    try {
                        int votes = Integer.parseInt(split[2]);
                        if (votes > 15000) {
                            count++;
                        }

                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {

            FileWriter fw = new FileWriter("Outputs/Tasks/task3.csv");
            fw.write("Total number of shows that have vote count greater than 15000 is: " + String.valueOf(count));
            fw.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private int findEndingQuote(String line) {
        for (int i = 1; i < line.length(); i++) {
            if (line.charAt(i) == '"') {
                if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    i++;
                    continue;
                }
                return i;
            }
        }
        return -1;
    }

}
