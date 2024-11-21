public class Main {
    public static void main(String[] args) {
        String filePath = "TMDB_tv_dataset_v3_clean.csv";

        TopRating  show = new TopRating("",0.0);
        show.processFile(filePath);

        LanguageRating language = new LanguageRating(" "," ",0.0);
        language.originalLanguage("TMDB_tv_dataset_v3_clean.csv");

    }
}