public class Main {
    public static void main(String[] args) {
        String fileName = "TMDB_tv_dataset_v3_clean.csv";

        TopRating  show = new TopRating("",0.0);
        show.processFile(fileName);

        LanguageRating language = new LanguageRating(" "," ",0.0);
        language.originalLanguage(fileName);

        VoteCount vote = new VoteCount(" ", 0);
        vote.getCount(fileName);

        DateRating date = new DateRating(" ", 0.0);
        date.dateRating(fileName);

        StillInProduction production = new StillInProduction(" ", 0.0);
        production.stillProduction(fileName);

        OneName one = new OneName(" ", 0.0);
        one.oneNamePopularity(fileName);

        GenreRating genre = new GenreRating(" ", " ", 0.0);
        genre.genreRatings(fileName);

    }
}