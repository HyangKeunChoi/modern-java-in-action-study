package youngsik.chapter5.quiz;

public class Trader {

    private final String name;
    private final String location;

    public Trader ( String name , String location) {
        this.name = name;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return "Trader:"+this.name + " in " + this.location;
    }


}
