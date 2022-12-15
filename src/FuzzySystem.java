import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class FuzzySystem {
    String name;
    String description;
    Set<Variable> variables;
    ArrayList<Rule> rules;
    @Override
    public String toString() {
        return "FuzzySystem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", variables=" + variables +
                ", rules=" + rules +
                '}';
    }

    public FuzzySystem(String name, String description) {
        this.name = name;
        this.description = description;
        variables = new LinkedHashSet<>();
        rules = new ArrayList<>();
    }

    Variable getVarByName(String name){
        for(Variable var: variables){
            if(var.name.equalsIgnoreCase(name))
                return var;
        }
        return null;
    }

    public void addNewVariable(Variable newVar){
        variables.add(newVar);
    }

    public void addNewRule(Rule newRule){
        rules.add(newRule);
    }

    public boolean checkStart(){
        if(variables.size()!=0){
            for (Variable var : variables){
                if(var.fuzzySets.size()==0) return false;
            }
            if(rules.size()!=0){
                return true;
            }
        }
        return false;
    }

    public Set<Variable> getVariables()
    {
        return variables;
    }

    public void fuzzification() {
        for (Variable var : variables) {
            if (var.type == Variable.VarType.IN) {
                int crisp = var.crispValue;
                for (FuzzySet fuzzySet : var.getFuzzySets()) {
                    if(crisp>fuzzySet.values.get(fuzzySet.values.size()-1) || crisp<fuzzySet.values.get(0)){
                        fuzzySet.membershipVal=0.0;
                    }
                    else{
                        for (int val=1 ; val <fuzzySet.values.size();val++) {
                            if (fuzzySet.values.get(val) >= crisp) {
                                if(fuzzySet.type== FuzzySet.SetType.TRIANGULAR){
                                    //[0,1,0]
                                    fuzzySet.membershipVal = membershipFunction(fuzzySet.values.get(val-1), (val-1)%2,
                                            fuzzySet.values.get(val),val%2,
                                            crisp);
                                }else{
                                    //[0,1,1,0]
                                    fuzzySet.membershipVal = membershipFunction(fuzzySet.values.get(val-1),(val-1)%3==2?1:(val-1)%3,
                                            fuzzySet.values.get(val),val%3==2?1:(val)%3,
                                            crisp);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Fuzzification => done");
    }

    public void inference() {
        for (Rule rule : rules){
            if(rule.type== Rule.OpType.AND){
                rule.outVarSet.membershipVal =Math.max(Math.min(rule.inVarSet1.membershipVal,rule.inVarSet2.membershipVal),rule.outVarSet.membershipVal);
            }else if(rule.type== Rule.OpType.OR){
                rule.outVarSet.membershipVal = Math.max(Math.max(rule.inVarSet1.membershipVal,rule.inVarSet2.membershipVal),rule.outVarSet.membershipVal);
            }else{
                rule.outVarSet.membershipVal = Math.max(Math.min(rule.inVarSet1.membershipVal,1-rule.inVarSet2.membershipVal),rule.outVarSet.membershipVal);
            }
        }
        System.out.println("Inference => done");
    }

    public void Defuzzification() {
        for(Variable var : variables){
            if(var.type == Variable.VarType.IN)continue;
            for (FuzzySet fuzzySet : var.getFuzzySets()){
                fuzzySet.centroid=calcCentroid(fuzzySet.values,fuzzySet.type==FuzzySet.SetType.TRIANGULAR ?3: 4);
            }
        }
        for(Variable var : variables){
            double ans=0.0;
            double sumMembers=0.0;
            if(var.type == Variable.VarType.IN)continue;
            for (FuzzySet fuzzySet : var.getFuzzySets()){
                ans = ans + (fuzzySet.centroid * fuzzySet.membershipVal);
                sumMembers = sumMembers + fuzzySet.membershipVal;
            }
            ans = ans / sumMembers;
            ans = (int) (ans*100) / 100.0;
            String behave = outputBehave(ans,var);
            System.out.println("Defuzzification => done");
            System.out.println("The predicted "+var.name+" is "+behave+" ("+ans+") ");
        }
    }

    String outputBehave(double ans , Variable var) {
        double maxIntercept=0.0;
        String output="#";

        for (FuzzySet fuzzySet : var.getFuzzySets()) {
            if(ans>fuzzySet.values.get(fuzzySet.values.size()-1) || ans<fuzzySet.values.get(0)){
                continue;
            }
            double temp=0;
            for (int val=1 ; val <fuzzySet.values.size();val++) {
                if (fuzzySet.values.get(val) >= ans) {
                    if(fuzzySet.type== FuzzySet.SetType.TRIANGULAR){
                        temp = membershipFunction(fuzzySet.values.get(val-1), (val-1)%2,
                                fuzzySet.values.get(val),val%2,
                                ans);

                    }else{
                       temp = membershipFunction(fuzzySet.values.get(val-1),(val-1)%3==2?1:(val-1)%3,
                                fuzzySet.values.get(val),val%3==2?1:(val)%3,
                                ans);
                        }
                    break;
                }
            }
            if(temp>=maxIntercept) {
                maxIntercept=temp;
                output=fuzzySet.name;
            }
        }
        return output;
    }

    int calcCentroid(ArrayList<Integer> values,int base){
        int sum=0;
        for(Integer it : values){
            sum = sum + it;
        }
        return sum/base;
    }

    public double membershipFunction(int x1,int y1,int x2,int y2,double x) {
        double slope = (double)(y2-y1)/(double)(x2-x1);
        double intersect = y2 - (slope * x2);
        return ( (slope * x) + intersect);
    }

}
