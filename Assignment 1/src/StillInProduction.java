import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StillInProduction {
    String title;
    Double rating;

    public StillInProduction(String title, double rating) {
        this.title = title;
        this.rating = rating;
    }

    public void stillProduction(String fileName) {
        File file = new File(fileName);

        ArrayList<StillInProduction> still = new ArrayList<>();

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
                            rating = Double.parseDouble(remainingFields[3]);
                            boolean production = Boolean.parseBoolean(remainingFields[6]);

                             if(production) {
                                 still.add(new StillInProduction(title, rating));
                             }

                        } else {
                            throw new Exception();
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    String[] split = line.split(",");

                    if (split.length >= 7) {
                        try {
                            String title = split[0];

                            rating = Double.parseDouble(split[3]);

                            boolean production = Boolean.parseBoolean(split[6]);

                            if(production) {
                                still.add(new StillInProduction(title, rating));
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

        still.sort((av1, av2) -> Double.compare(av2.rating, av1.rating));

        List<StillInProduction> top50 = still.subList(0, 50);

        try {
            FileWriter fw = new FileWriter("Outputs/Tasks/task5.csv");
            for(StillInProduction s : top50) {
                fw.write(s.toString() + "\n");
            }
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

    @Override
    public String toString() {
        return "Title = " + title + " , Rating = " + rating;
    }
}
