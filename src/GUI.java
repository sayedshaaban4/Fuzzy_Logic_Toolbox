import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GUI implements ActionListener {
    public  static FuzzySystem fuzzySystem;
    GUI(){
         fuzzySystem = new FuzzySystem();
       // start();
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

    // Adding "jRadioButton1" and "jRadioButton3" in a Button Group "G2".
    G1.add(jRadioButton1);
    G1.add(jRadioButton2);

    // Adding Listener to JButton.
    //JLabel res = new JLabel();
    jButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
            if (jRadioButton1.isSelected()) {
                //Gui for fuzzy sys
                //res.setText("1");
                takeDescription();
                frame.dispose();

                System.out.println("okkkkkkkk im saad");
               // return;
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
    String desc="";
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
                //fuzzySystem.name= name;
                //fuzzySystem.description= desc;
                f.dispose();


            }

        }
    });
    f.add(l1);f.add(l2);f.add(t);f.add(area);f.add(b);
    f.setSize(500,600);
    f.setLayout(null);
    f.setVisible(true);

}



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
