import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LanguageRating {
    String title;
    String language;
    Double rating;

    public LanguageRating(String title, String language, Double rating) {
        this.title = title;
        this.language = language;
        this.rating = rating;
    }

    public void originalLanguage(String fileName) {
        File file = new File(fileName);

        ArrayList<LanguageRating> languageRatings = new ArrayList<>();

        try{
            Scanner s = new Scanner(file);
            s.nextLine();

            while(s.hasNextLine()) {
                String line = s.nextLine();

                if(line.startsWith("\"")){
                    try{
                        int endQuoteIndex = line.indexOf("\"", 1);
                        if(endQuoteIndex != -1) {
                            String title = line.substring(1, endQuoteIndex);

                            String[] remainingFields = line.substring(endQuoteIndex + 1).split(",");

                            String language = remainingFields[1];
                            double rating = Double.parseDouble(remainingFields[3].trim());
                            languageRatings.add(new LanguageRating(title, language, rating));

                        } else{
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    String[] split = line.split(",");

                    if (split.length >= 4) {
                        try {
                            String title = split[0];
                            String language = split[1];
                            double rating = Double.parseDouble(split[3].trim());

                            languageRatings.add(new LanguageRating(title, language, rating));

                        } catch (NumberFormatException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }



            Map<String, ArrayList<LanguageRating>> languageRatingsMap = new HashMap<>();
            for (LanguageRating l : languageRatings) {
                if (!languageRatingsMap.containsKey(l.language)) {
                    languageRatingsMap.put(l.language, new ArrayList<>());
                }
                languageRatingsMap.get(l.language).add(l);
            }

            for (Map.Entry<String, ArrayList<LanguageRating>> l : languageRatingsMap.entrySet()) {
            String language = l.getKey();
            ArrayList<LanguageRating> ratings = l.getValue();
            ratings.sort((av1, av2) -> Double.compare(av2.rating, av1.rating));

                try {

                    FileWriter fw = new FileWriter("Outputs/Languages/task2-"+language.replaceAll("[^a-zA-Z0-9]", "_")+".csv");

                    int counter = Math.min(50,ratings.size());
                    for (int i = 0; i < counter; i++) {
                        LanguageRating rating = ratings.get(i);

                        String formattedTitle = rating.title.contains(",") ? "\"" + rating.title + "\"" : rating.title;
                        fw.write(String.format("%s,%s,%.2f\n",
                                formattedTitle,
                                rating.language,
                                rating.rating));
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "LanguageRating{" +
                "title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", rating=" + rating +
                '}';
    }
}
