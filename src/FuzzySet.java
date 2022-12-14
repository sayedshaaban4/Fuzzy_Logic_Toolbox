import java.util.ArrayList;

public class FuzzySet {

    enum SetType {TRAPEZOIDAL, TRIANGULAR};
     String name ;
     SetType type;
     ArrayList<Integer> values;
     int centroid;
     double membershipVal;

    FuzzySet(String name, String type, ArrayList<Integer> values){
        this.name = name;
        centroid=0;
        membershipVal=0.0;
        this.type = type.equalsIgnoreCase("tri")?SetType.TRIANGULAR:SetType.TRAPEZOIDAL;
        this.values = new ArrayList<>(values);
    }
}
