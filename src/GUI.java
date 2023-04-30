import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GUI implements ActionListener {
    public  static FuzzySystem fuzzySystem;
    GUI(){
        fuzzySystem = new FuzzySystem("" ,"");
       start();
    }

    public void start ()
    {

        JFrame frame = new JFrame();
        JRadioButton jRadioButton1 =new JRadioButton("Create a new fuzzy system");

        // Declaration of object of JRadioButton class.
        JRadioButton jRadioButton2 = new JRadioButton("Quit");

        // Declaration of object of JButton class.
        JButton jButton = new JButton("ok");

        // Declaration of object of ButtonGroup class.
        ButtonGroup G1= new ButtonGroup();

        // Setting layout as null of JFrame.
        frame.setLayout(null);

        // Setting Bounds of "jRadioButton2".
        jRadioButton1.setBounds(100,50,300,30);

        // Setting Bounds of "jRadioButton4".
        jRadioButton2.setBounds(100,100,300,30);

        // Setting Bounds of "jButton".
        jButton.setBounds(100,150,80,30);

        // "this" keyword in java refers to current object.
        // Adding "jRadioButton2" on JFrame.
        frame.add(jRadioButton1);

        // Adding "jRadioButton4" on JFrame.
        frame.add(jRadioButton2);

        // Adding "jButton" on JFrame.
        frame.add(jButton);
        frame.setTitle("Fuzzy Logic Toolbox");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 400, 300);
        G1.add(jRadioButton1);
        G1.add(jRadioButton2);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (jRadioButton1.isSelected()) {
                    //Gui for fuzzy sys
                    //res.setText("1");
                    takeDescription();
                    frame.dispose();

             }
                else if (jRadioButton2.isSelected()) {
                    frame.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "please choose");
                }
            }
        });

    }
    public void takeDescription ()
    {

        JFrame f= new JFrame();
        JLabel l1=new JLabel("Enter the name :");
        JLabel l2=new JLabel("Enter the description :");
        JTextField t = new JTextField();
        //JLabel l2 = new JLabel();
        l1.setBounds(100,50,300,30);

        t.setBounds(100,100,300,30);
        l2.setBounds(100,150,300,30);
        JTextArea area=new JTextArea();
        area.setBounds(100,200,250,200);
        JButton b=new JButton("ok");
        b.setBounds(100,500,120,30);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("ok")) {
                    // set the text of the label to the text of the field
                    String name = t.getText();
                    String desc= area.getText();
                    fuzzySystem.name= name;
                    fuzzySystem.description= desc;
                    f.dispose();
                    menu();
                }

            }
        });
        f.add(l1);f.add(l2);f.add(t);f.add(area);f.add(b);
        f.setSize(500,600);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void menu()
    {
        JFrame frame = new JFrame();
        JRadioButton jRadioButton1 =new JRadioButton("Add variables");
        JRadioButton jRadioButton2 = new JRadioButton("Add fuzzy sets to an existing variable");
        JRadioButton jRadioButton3 = new JRadioButton("Add rules");
        JRadioButton jRadioButton4 = new JRadioButton("Run the simulation on crisp values");

        // Declaration of object of JButton class.
        JButton jButton = new JButton("ok");

        // Declaration of object of ButtonGroup class.
        ButtonGroup G1= new ButtonGroup();

        // Setting layout as null of JFrame.
        frame.setLayout(null);

        // Setting Bounds of "jRadioButton2".
        jRadioButton1.setBounds(100,50,300,30);
        jRadioButton2.setBounds(100,100,300,30);
        jRadioButton3.setBounds(100,150,300,30);
        jRadioButton4.setBounds(100,200,300,30);
        jButton.setBounds(100,250,80,30);

        frame.add(jRadioButton1);
        frame.add(jRadioButton2);
        frame.add(jRadioButton3);
        frame.add(jRadioButton4);
        frame.add(jButton);

        frame.setTitle("Main Menu");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.setSize(500,600);

        // Adding "jRadioButton1" and "jRadioButton3" in a Button Group "G2".
        G1.add(jRadioButton1);
        G1.add(jRadioButton2);
        G1.add(jRadioButton3);
        G1.add(jRadioButton4);

        // Adding Listener to JButton.
        //JLabel res = new JLabel();
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (jRadioButton1.isSelected()) {
                    // add variable
                    addVariable();

                }
                else if (jRadioButton2.isSelected()) {
                    // add fuzzy sets
                    addFuzzySet();


                }
                else if (jRadioButton3.isSelected()) {
                    //add rules
                    addRule();
                }
                else if(jRadioButton4.isSelected()) {
                    if (!fuzzySystem.checkStart()) {

                        JOptionPane.showMessageDialog(frame, "CAN'T START THE SIMULATION! Please add the fuzzy sets and rules first.");
                        System.out.println("CAN'T START THE SIMULATION! Please add the fuzzy sets and rules first.");
                    }else {
                        //run window
                        run();
                    }
                }else{
                    JOptionPane.showMessageDialog(frame, "please choose an option");
                }
            }
        });


    }
    public void addVariable() {
        JFrame f= new JFrame();
        JLabel l1=new JLabel("Enter the variable’s name, type (IN/OUT) and range ([lower, upper]) :");
        l1.setBounds(100,50,500,30);
        JTextArea area=new JTextArea();
        area.setBounds(100,100,250,200);
        JButton b=new JButton("ok");
        b.setBounds(100,500,120,30);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("ok")) {
                    for (String l : area.getText().split("\\n")) {
                        System.out.println(l);
                        if(!Main.validVarInput(l)){
                            System.out.println("Invalid Format!! Try again.");
                            continue;
                        }

                        String[] line = l.split(" ");
                        int low = Integer.parseInt(line[2].substring(1,line[2].length()-1));
                        int high = Integer.parseInt(line[3].substring(0,line[3].length()-1));
                        Variable newVar = new Variable(line[0],line[1],low,high);
                        fuzzySystem.addNewVariable(newVar);
                    }
                    System.out.println(fuzzySystem);
                    f.dispose();

                }

            }
        });
        f.add(l1);f.add(area);f.add(b);
        f.setSize(800,800);
        f.setLayout(null);
        f.setVisible(true);


    }
    public void addFuzzySet(){
        JFrame f= new JFrame();
        JLabel l1=new JLabel("Enter the variable’s name :");
        JLabel l2=new JLabel("Enter the fuzzy set name, type (TRI/TRAP) and values :");
        JTextField t = new JTextField();
        //JLabel l2 = new JLabel();
        l1.setBounds(100,50,300,30);

        t.setBounds(100,100,300,30);
        l2.setBounds(100,150,300,30);
        JTextArea area=new JTextArea();
        area.setBounds(100,200,250,200);
        JButton b=new JButton("ok");
        b.setBounds(100,500,120,30);
        b.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("ok")) {
                    if(fuzzySystem.variables.isEmpty()){
                        JOptionPane.showMessageDialog(f, "You've to enter variables first!");

                    }
                    String var_name = t.getText();
                    System.out.println(var_name);
                    Variable var = fuzzySystem.getVarByName(var_name);
                    if(var==null)
                    {
                        JOptionPane.showMessageDialog(f, "Invalid Variable name !! Try again.");

                    }
                    for (String l : area.getText().split("\\n")) {
                        System.out.println(l);
                        if(Main.validFuzzySetInput(l))
                        {
                            String[] line = l.split(" ");
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
                            JOptionPane.showMessageDialog(f, "Invalid Format!! Try again.");
                        }
                    }
                        System.out.println(fuzzySystem);
                        f.dispose();
                }

            }
        });
        f.add(l1);f.add(l2);f.add(t);f.add(area);f.add(b);
        f.setSize(500,600);
        f.setLayout(null);
        f.setVisible(true);
    }
    public void addRule(){
        JFrame f= new JFrame();
        JLabel l1=new JLabel("Enter the rules in this format: ");
        JLabel l2=new JLabel("IN_variable set operator IN_variable set => OUT_variable set ");

        l1.setBounds(100,50,300,30);
        l2.setBounds(100,100,300,30);
        JTextArea area=new JTextArea();
        area.setBounds(100,150,250,200);
        JButton b=new JButton("ok");
        b.setBounds(100,500,120,30);
        b.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("ok")){
                    for (String l : area.getText().split("\\n")) {
                        System.out.println(l);
                        if(!Main.validRuleInput(l)){
                            System.out.println("Invalid Format!! Try again.");
                            JOptionPane.showMessageDialog(f, "Invalid Format!! Try again.");

                        }
                        String[] line = l.split(" ");
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
                    System.out.println(fuzzySystem);
                    f.dispose();
                }
            }
        });
        f.add(l1);f.add(l2);f.add(area);f.add(b);
        f.setSize(800,800);
        f.setLayout(null);
        f.setVisible(true);


    }




    public void run(){

        JFrame f= new JFrame();
        JLabel l1 = new JLabel("Enter the crisp values :");
        Set<Variable> variables;
        variables = fuzzySystem.getVariables();
        ArrayList<JLabel> labels= new ArrayList<>();
        ArrayList<JTextField> texts= new ArrayList<>();
        for (Variable var : variables) {
            if (var.type == Variable.VarType.IN) {
                labels.add(new JLabel(var.name));
                texts.add(new JTextField());

            }
        }
        System.out.println("labels.size " + labels.size());
        int y=100;
        for (int j = 0; j < labels.size(); j++) {
            System.out.println(labels.get(j).getText());
            labels.get(j).setBounds(100,y,300,30);
            f.add(labels.get(j));
            texts.get(j).setBounds(100,y+50,300,30);
            f.add(texts.get(j));
            y+=100;
        }
        JButton b=new JButton("ok");
        l1.setBounds(100,50,300,30);
        b.setBounds(100,500,120,30);
        f.add(b);f.add(l1);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("ok")) {
                    //run algorithm
                    String localCrispVal;
                    int i=0;
                    for (Variable var : variables) {
                        if (var.type == Variable.VarType.IN) {
                            localCrispVal = texts.get(i).getText();
                            if (!localCrispVal.matches("\\d+")) {
                                System.out.println("Please Enter Valid Crisp Value!");
                            }
                            var.crispValue = Integer.parseInt(localCrispVal);
                            System.out.println();
                        }
                        i++;
                    }
                    System.out.println("Running the simulation...");
                    fuzzySystem.fuzzification();
                    fuzzySystem.inference();
                    fuzzySystem.Defuzzification();
                    f.dispose();

                }


            }
        });
        f.setSize(500,600);
        f.setLayout(null);
        f.setVisible(true);

    }







        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
