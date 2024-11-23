import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OneName {
    String title;
    Double popularity;

    public OneName(String title, double popularity) {
        this.title = title;
        this.popularity = popularity;
    }

    public void oneNamePopularity(String fileName) {
        File file = new File(fileName);

        ArrayList<OneName> popularityList = new ArrayList<>();

        try {
            Scanner s = new Scanner(file);
            s.nextLine();

            while (s.hasNextLine()) {
                String line = s.nextLine();

                if (line.startsWith("\"")) {
                    try {
                        int endQuoteIndex = findEndingQuote(line);

                        if (endQuoteIndex != -1) {
                            String title = line.substring(1, endQuoteIndex);
                            String[] remainingFields = line.substring(endQuoteIndex + 1).split(",");

                            double popularity = Double.parseDouble(remainingFields[7]);

                            if(!title.trim().contains(" ")){
                                popularityList.add(new OneName(title, popularity));
                            }

                        } else {
                            throw new Exception();
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    String[] split = line.split(",");

                    if (split.length >= 8) {
                        try {
                            String title = split[0];
                            double popularity = Double.parseDouble(split[7]);

                            if(!title.trim().contains(" ")){
                                popularityList.add(new OneName(title, popularity));
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

        popularityList.sort((av1, av2) -> Double.compare(av2.popularity, av1.popularity));

        List<OneName> top50 = popularityList.subList(0, 50);

        try{
        FileWriter fw = new FileWriter("Outputs/Tasks/task6.csv");
        for (OneName oneName : top50) {
            fw.write(oneName.toString() + "\n");
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
        return "Title = " + title + ", popularity = " + popularity;
    }
}
