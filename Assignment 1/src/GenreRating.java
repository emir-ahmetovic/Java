import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GenreRating {
    String title;
    String genre;
    Double rating;

    public GenreRating(String title, String genre, Double rating) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    public void genreRatings(String fileName) {
        File file = new File(fileName);
        ArrayList<GenreRating> genreRatings = new ArrayList<>();

        try {
            Scanner s = new Scanner(file);
            s.nextLine();

            while (s.hasNextLine()) {
                String line = s.nextLine();

                if (line.startsWith("\"")) {
                    int endQuoteIndex = findEndingQuote(line);
                    if (endQuoteIndex != -1) {
                        String title = line.substring(1, endQuoteIndex);
                        String[] remainingFields = line.substring(endQuoteIndex + 1).split(",");

                        double rating = Double.parseDouble(remainingFields[3].trim());

                        String[] genres = remainingFields[7].replaceAll("\"", "").split(",");

                        processGenreList(title, genres, rating, genreRatings);
                    }
                } else {
                    String[] split = line.split(",");
                    if (split.length >= 9) {
                        String title = split[0];

                        double rating = Double.parseDouble(split[3].trim());

                        String[] genres = split[8].replaceAll("\"", "").split(",");

                        processGenreList(title, genres, rating, genreRatings);
                    }
                }
            }

            Map<String, ArrayList<GenreRating>> genreRatingsMap = new HashMap<>();
            for (GenreRating g : genreRatings) {
                if (!genreRatingsMap.containsKey(g.genre)) {
                    genreRatingsMap.put(g.genre, new ArrayList<>());
                }
                genreRatingsMap.get(g.genre).add(g);
            }

            for (Map.Entry<String, ArrayList<GenreRating>> entry : genreRatingsMap.entrySet()) {
                String genre = entry.getKey();
                ArrayList<GenreRating> ratings = entry.getValue();

                if (!isValidGenre(genre)) continue;

                ratings.sort((g1, g2) -> {
                    if (g1.rating == null && g2.rating == null) return 0;
                    if (g1.rating == null) return 1;
                    if (g2.rating == null) return -1;
                    return Double.compare(g2.rating, g1.rating);
                });

                try {
                    FileWriter fw = new FileWriter("Outputs/Genres/task7-" + genre.trim() + ".csv");

                    int counter = Math.min(50, ratings.size());
                    for (int i = 0; i < counter; i++) {
                        GenreRating r = ratings.get(i);
                        fw.write(r.toString() + "\n");
                    }
                    fw.close();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processGenreList(String title, String[] genres, double rating, ArrayList<GenreRating> genreRatings) {
        for (String genre : genres) {
            String cleanedGenre = genre.trim();
            if (isValidGenre(cleanedGenre)) {
                genreRatings.add(new GenreRating(title, cleanedGenre, rating));
            }
        }
    }

    private boolean isValidGenre(String genre) {

        if (genre.isEmpty()) return false;

        if (genre.matches("\\d+")) return false;

        if (!genre.matches(".*[a-zA-Z]+.*")) return false;

        return true;
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
        return "Title = " + title + ", popularity = " + rating;
    }
}