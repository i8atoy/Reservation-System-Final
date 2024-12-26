import java.util.*;


public class Band {
    private ArrayList<Musician> musicians;
    private static final int bandRatingMin = 20;
    private static final int bandNumberMax = 5;


    public Band(ArrayList<Musician> musicians) {
        this.musicians = musicians;
    }

    public Band(){
        musicians = new ArrayList<>();
    }

    public int getBandRating(){
        int bandRating = 0;
        for (Musician musician : musicians) {
            bandRating += musician.getRating();
        }
        return bandRating;
    }

    private static boolean isValidBandRating(int bandRating){
        if(bandRating < bandRatingMin) return false;
        return true;
    }

    private static boolean isValidBandNumber(List<Musician> musicians){
        if(musicians.size() > bandNumberMax) return false;
        return true;
    }

    public boolean isValidBand(int bandRating, List<Musician> musicians){
        if(isValidBandRating(bandRating) && isValidBandNumber(musicians)) return true;
        return false;
    }

    public void addMusician(Musician musician){
        musicians.add(musician);
    }

    public void deleteMusician(Musician musician){
        musicians.remove(musician);
    }

    public ArrayList<Musician> getMusicians(){
        return musicians;
    }

}

