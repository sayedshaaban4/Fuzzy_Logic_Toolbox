import java.util.ArrayList;

public class Variable {
    enum VarType {IN, OUT};
    String name ;
    VarType type;
    ArrayList<FuzzySet> fuzzySets;
    int crispValue;
    int lowRange;
    int highRange;

    public Variable(String name, String type, int lowRange, int highRange) {
        this.name = name;
        this.type = type.equalsIgnoreCase("in")?VarType.IN:VarType.OUT;
        this.lowRange = lowRange;
        this.highRange = highRange;
        this.crispValue =0;
        fuzzySets = new ArrayList<>();
    }

    void addFuzzySet(FuzzySet newFuzzySet){
        fuzzySets.add(newFuzzySet);
    }

    FuzzySet getFuzzySet(String fuzzySetName){
        for (FuzzySet fuzzySet:fuzzySets){
            if(fuzzySet.name.equalsIgnoreCase(fuzzySetName)){
                return fuzzySet;
            }
        }
        return null;
    }

    ArrayList<FuzzySet> getFuzzySets(){
        return fuzzySets;
    }
}
