public class Rule {
    enum OpType {OR,AND,AND_NOT};
     FuzzySet inVarSet1;
     FuzzySet inVarSet2;
     FuzzySet outVarSet;
     OpType type;

    public Rule(FuzzySet inVarSet1, FuzzySet inVarSet2, FuzzySet outVarSet, String type) {
        this.inVarSet1 = inVarSet1;
        this.inVarSet2 = inVarSet2;
        this.outVarSet = outVarSet;
        if(type.equalsIgnoreCase("or")){
            this.type = OpType.OR;
        }else if(type.equalsIgnoreCase("and")) {
            this.type = OpType.AND;
        }else{
            this.type = OpType.AND_NOT;
        }
    }
}
