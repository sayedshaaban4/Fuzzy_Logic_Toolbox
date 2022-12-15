import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args){
        System.out.println("Choose a way for running: ");
        System.out.println("1- console");
        System.out.println("2- GUI");
        System.out.println("3- Files");
        Scanner sc=new Scanner(System.in);
        int option=sc.nextInt();
        switch (option)
        {
            case 1:
                console();
                break;
            case 2:
                new GUI();
                break;
            case 3:
                Files();
                break;
        }


    }
    public static void Files(){
        try {
            File O = new File("input.txt");
            Scanner read = new Scanner(O);
            while (read.hasNextLine()) {
                String data = read.nextLine();
                System.out.println(data);
            }
            read.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("There is an error while reading the file");
            e.printStackTrace();
        }
    }

    public static void console() {
        while(true){
        System.out.println("Fuzzy Logic Toolbox");
        System.out.println("===================");
        System.out.println("1- Create a new fuzzy system");
        System.out.println("2- Quit");

        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        while( Integer.parseInt(choice) != 1 && Integer.parseInt(choice) !=2){
            System.out.println("Please Enter 1 or 2");
            choice = sc.nextLine();
        }

        if(Integer.parseInt(choice)==1){
            System.out.println("""
                    Enter the system's name and a brief description:
                    ------------------------------------------------
                    (Press x to finish)""");
            String name = sc.nextLine();
            StringBuilder desc = new StringBuilder();
            while (sc.hasNextLine()){
                String tmp = sc.nextLine();
                if(tmp.equalsIgnoreCase("x")) break;
                desc.append(tmp);
            }
            FuzzySystem fuzzySystem = new FuzzySystem(name,desc.toString());

            while(true) {
                System.out.println("""
                        Main Menu:
                        ==========
                        1- Add variables.
                        2- Add fuzzy sets to an existing variable.
                        3- Add rules.
                        4- Run the simulation on crisp values.
                        5- Close""");

                choice = sc.nextLine();
                while (Integer.parseInt(choice) > 5 || Integer.parseInt(choice) < 0) {
                    System.out.println("Please Enter number between 1-5");
                    choice = sc.nextLine();
                }

                // 1- Add variables.
                if (Integer.parseInt(choice) == 1) {
                    System.out.println("""
                            Enter the variable's name, type (IN/OUT) and range ([lower, upper]):
                            (Press x to finish)
                            --------------------------------------------------------------------""");
                    while (sc.hasNextLine()){
                        String tmp = sc.nextLine();
                        if(tmp.equalsIgnoreCase("x")) break;
                        if(!validVarInput(tmp)){
                            System.out.println("Invalid Format!! Try again.");
                            continue;
                        }

                        String[] line = tmp.split(" ");

                        int low = Integer.parseInt(line[2].substring(1,line[2].length()-1));
                        int high = Integer.parseInt(line[3].substring(0,line[3].length()-1));

                        Variable newVar = new Variable(line[0],line[1],low,high);
                        fuzzySystem.addNewVariable(newVar);

                    }
                }

                // 2- Add fuzzy sets to an existing variable.
                else if(Integer.parseInt(choice) == 2){
                    if(fuzzySystem.variables.isEmpty()){
                        System.out.println("You've to enter variables first!");
                        continue;
                    }
                    System.out.println("Enter the variable's name:\n" +
                            "--------------------------");
                    String var_name = sc.nextLine();
                    Variable var = fuzzySystem.getVarByName(var_name);
                    while(var==null)
                    {
                        System.out.println("Invalid Variable name !! Try again.");
                        System.out.println("Enter the variable's name:\n" +
                                "--------------------------");
                        var_name = sc.nextLine();
                        var = fuzzySystem.getVarByName(var_name);
                    }
                    System.out.println("Enter the fuzzy set name, type (TRI/TRAP) and values: (Press x to finish)\n" +
                            "-----------------------------------------------------");
                    while(true) {
                        String tmp = sc.nextLine();
                        if (tmp.equalsIgnoreCase("x")) break;
                        if(validFuzzySetInput(tmp))
                        {
                            String[] line = tmp.split(" ");
                            String n = line[0];
                            String t = line[1];
                            ArrayList<Integer> ar = new ArrayList<>();
                            for(int i=2;i<line.length;i++)
                            {
                                ar.add(Integer.parseInt(line[i]));
                            }
                            FuzzySet fs = new FuzzySet(n,t,ar);
                            var.addFuzzySet(fs);
                        }
                        else
                        {
                            System.out.println("Invalid Format!! Try again.");
                        }
                    }
                }

                // 3- Add rules.
                else if (Integer.parseInt(choice) == 3){
                    System.out.println("""
                            Enter the rules in this format: (Press x to finish)
                            IN_variable set operator IN_variable set => OUT_variable set
                            ------------------------------------------------------------""");
                    while (sc.hasNextLine()) {
                        String tmp = sc.nextLine();
                        if (tmp.equalsIgnoreCase("x")) break;
                        if(!validRuleInput(tmp)){
                            System.out.println("Invalid Format!! Try again.");
                            continue;
                        }
                        String[] line = tmp.split(" ");


                        String varName1 = line[0];
                        String setName1 = line[1];
                        Variable var1 = fuzzySystem.getVarByName(varName1);
                        FuzzySet fuzzySet1 ;
                        if(var1 != null && var1.type == Variable.VarType.IN){
                            fuzzySet1 = var1.getFuzzySet(setName1);
                            if(fuzzySet1 == null){
                                System.out.println("Invalid Fuzzy Set For Variable 1!! Try Again.");
                                continue;
                            }
                        }else{
                            System.out.println("Invalid Variable 1!! Try Again.");
                            continue;
                        }

                        String varName2 = line[3];
                        String setName2 = line[4];
                        Variable var2 = fuzzySystem.getVarByName(varName2);
                        FuzzySet fuzzySet2;
                        if(var2 != null && var2.type == Variable.VarType.IN){
                            fuzzySet2 = var2.getFuzzySet(setName2);
                            if(fuzzySet2 == null){
                                System.out.println("Invalid Fuzzy Set For Variable 2!! Try Again.");
                                continue;
                            }
                        }else{
                            System.out.println("Invalid Variable 2!! Try Again.");
                            continue;
                        }

                        String varName3 = line[6];
                        String setName3 = line[7];
                        Variable var3 = fuzzySystem.getVarByName(varName3);
                        FuzzySet fuzzySet3;
                        if(var3 != null && var3.type ==  Variable.VarType.OUT){
                            fuzzySet3 = var3.getFuzzySet(setName3);
                            if(fuzzySet3 == null){
                                System.out.println("Invalid Fuzzy Set For Variable 3!! Try Again.");
                                continue;
                            }
                        }else{
                            System.out.println("Invalid Variable 3!! Try Again.");
                            continue;
                        }
                        Rule newRule = new Rule(fuzzySet1,fuzzySet2,fuzzySet3,line[2]);
                        fuzzySystem.addNewRule(newRule);
                    }

                }

                // 4- Run the simulation on crisp values.
                else if (Integer.parseInt(choice) == 4){
                    if(!fuzzySystem.checkStart()) {
                        System.out.println("CAN'T START THE SIMULATION! Please add the fuzzy sets and rules first.");
                    }
                    else {
                        System.out.println("""
                                Enter the crisp values:
                                -----------------------""");
                        Set<Variable> variables;
                        variables = fuzzySystem.getVariables();
                        String localCrispVal;
                        for (Variable var : variables) {
                            if (var.type == Variable.VarType.IN) {
                                System.out.print(var.name + ":");
                                localCrispVal = sc.nextLine();
                                while (!localCrispVal.matches("\\d+")) {
                                    System.out.println("Please Enter Valid Crisp Value!");
                                    System.out.print(var.name + ":");
                                    localCrispVal = sc.nextLine();
                                }
                                var.crispValue = Integer.parseInt(localCrispVal);
                                System.out.println();
                            }
                        }
                        System.out.println("Running the simulation...");
                        fuzzySystem.fuzzification();
                        fuzzySystem.inference();
                        fuzzySystem.Defuzzification();
                    }
                }

                // 5- Close
                else{
                    break;
                }
            }
        }else break;

    }
    }



    public static boolean validVarInput(String tmp) {
        String[] line = tmp.split(" ");
        if( (line[1].equalsIgnoreCase("in") || line[1].equalsIgnoreCase("out")) && line.length == 4  && !line[0].isEmpty()){
            if(line[2].charAt(0) == '[' && line[2].charAt(line[2].length()-1) == ','){
                if(line[3].charAt(line[3].length()-1)==']'){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validFuzzySetInput(String tmp) {
        String[] line = tmp.split(" ");
        if(line[0].isEmpty())return false;
        if(line[1].equalsIgnoreCase("TRI")){
            if(line.length==5)
            {
                if(line[2].matches("\\d+") && line[3].matches("\\d+") && line[4].matches("\\d+"))
                {
                    return true;
                }
            }
        }
        else if(line[1].equalsIgnoreCase("TRAP")) {
            if(line.length==6)
            {
                if(line[2].matches("\\d+") && line[3].matches("\\d+") && line[4].matches("\\d+") && line[5].matches("\\d+"))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validRuleInput(String tmp) {
        String[] line = tmp.split(" ");
        if(
                (line[2].equalsIgnoreCase("or") || line[2].equalsIgnoreCase("and") || line[2].equalsIgnoreCase("and_not"))
                        && line.length == 8
                        && line[5].equalsIgnoreCase("=>")
        ) {return true;}

        return false;
    }
}
