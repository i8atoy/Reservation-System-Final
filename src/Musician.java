public class Musician {
    private String name;
    private String instrument;
    private int rating;

    public Musician(String name, String instrument, int rating) {
        this.name = name;
        this.instrument = instrument;
        this.rating = rating;
    }

    public Musician(String name, int rating) {
        this.name = name;
        this.rating = rating;
        instrument = "Vocals";
    }

    public int getRating() {
        return rating;
    }

    public String getInstrument() {
        return instrument;
    }
    public String getName() {
        return name;
    }

}
