import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GUI implements ActionListener {


    GUI(){
      start();
    }
public void start(){
    JFrame frame = new JFrame();
    JRadioButton cb1,cb2;
    JButton b;
    cb1=new JRadioButton("Create a new fuzzy system");
    cb1.setBounds(100,100,150,20);
    cb2=new JRadioButton("Quit");
    cb2.setBounds(100,100,150,20);
    b=new JButton("OK");
    b.setBounds(100,250,80,30);
    b.addActionListener(this);
    frame.add(cb1);frame.add(cb2);frame.add(b);
    frame.setLayout(null);
    frame.setSize(400, 400);
    frame.setTitle("Fuzzy Logic Toolbox");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
}

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
