import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TopRating {

    String title;
    Double rating;

    public TopRating(String title ,double rating) {
        this.title = title;
        this.rating = rating;
    }

    public void processFile(String fileName) throws RuntimeException {
        File f = new File(fileName);
        ArrayList<TopRating> topAverage = new ArrayList<>();
        ArrayList<String> invalidEntries = new ArrayList<>();

        try{
            Scanner s = new Scanner(f);
            s.nextLine();

            while(s.hasNextLine()) {
                String line = s.nextLine();

                if (line.startsWith("\"")) {
                    try {
                        int endQuoteIndex = line.indexOf("\"", 1);
                        if (endQuoteIndex != -1) {

                            String title = line.substring(1, endQuoteIndex);

                            String[] remainingFields = line.substring(endQuoteIndex + 1).split(",");

                            double rating = Double.parseDouble(remainingFields[3].trim());
                            topAverage.add(new TopRating(title, rating));
                        } else {
                            invalidEntries.add(line);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    String[] split = line.split(",");
                    if (split.length >= 4) {
                        try {
                            String title = split[0];
                            double rating = Double.parseDouble(split[3].trim());
                            topAverage.add(new TopRating(title, rating));
                        } catch (NumberFormatException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            topAverage.sort((av1, av2) -> Double.compare(av2.rating, av1.rating));

            List<TopRating> top50 = topAverage.subList(0, 50);

            try{
                FileWriter rating = new FileWriter("Outputs/Tasks/task1.csv");
                for (TopRating r : top50) {
                    rating.write(r.toString() + "\n");
                }
                rating.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return "Title = " + title + ", rating = " + rating;
    }

}
