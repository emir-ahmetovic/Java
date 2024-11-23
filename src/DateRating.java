import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DateRating {

    String title;
    Double rating;

    public DateRating(String title, double rating) {
        this.title = title;
        this.rating = rating;
    }

    public void dateRating(String fileName) {
        File f = new File(fileName);

        ArrayList<DateRating> top = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[M/d/yyyy][MM/dd/yyyy]");

        LocalDate startDate = LocalDate.of(2010, 1, 1);
        LocalDate endDate = LocalDate.of(2019, 12, 31);

        try {
            Scanner s = new Scanner(f);
            s.nextLine();

            while (s.hasNextLine()) {
                String line = s.nextLine();

                if (line.startsWith("\"")) {
                    try {
                        int endQuoteIndex = findEndingQuote(line);

                        if (endQuoteIndex != -1) {
                            String title = line.substring(1, endQuoteIndex);

                            String[] remainingFields = line.substring(endQuoteIndex + 1).split(",");

                            if (remainingFields.length > 3) {
                                rating = Double.parseDouble(remainingFields[3]);
                                LocalDate firstAir = LocalDate.parse(remainingFields[4].trim(), formatter);
                                LocalDate lastAir = LocalDate.parse(remainingFields[5].trim(), formatter);

                                if (isInDateRange(firstAir, lastAir, startDate, endDate)) {
                                    top.add(new DateRating(title, rating));
                                }
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                } else {
                    String[] split = line.split(",");

                    if (split.length >= 6) {
                        try {
                            String title = split[0];
                            double rating = Double.parseDouble(split[3]);
                            LocalDate firstAir = LocalDate.parse(split[4].trim(), formatter);
                            LocalDate lastAir = LocalDate.parse(split[5].trim(), formatter);

                            if (isInDateRange(firstAir, lastAir, startDate, endDate)) {
                                top.add(new DateRating(title, rating));
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
            }

            top.sort((av1, av2) -> Double.compare(av2.rating, av1.rating));

            int topSize = Math.min(50, top.size());
            List<DateRating> top50 = top.subList(0, topSize);

            try {
                FileWriter rating = new FileWriter("Outputs/Tasks/task4.csv");
                for (DateRating r : top50) {
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

    private boolean isInDateRange(LocalDate firstAir, LocalDate lastAir,
                                  LocalDate startDate, LocalDate endDate) {
        return !firstAir.isBefore(startDate) && !lastAir.isAfter(endDate);
    }

    @Override
    public String toString() {
        return "Title = " + title + ", rating = " + rating;
    }
}