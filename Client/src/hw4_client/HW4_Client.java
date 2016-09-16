/*
Shivam Swarnkar
Send button and Enter in text field, either will send the message.
 */
package hw4_client;
import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HW4_Client {
    /**
     * @param args the command line arguments
     */
    static JFrame jf;
    static JPanel jp1;
    static JPanel jp2;
    static JPanel jp3;
    static JButton jb;
    static JTextField jt1;
    static JLabel jl1;
    static JTextField jt2;
    static JLabel jl2;
    static JTextField jt3;
    static JTextArea jt4;
    static JLabel jl3;
    static JLabel jl;
    
    static JScrollPane scroll ;
    static String address = ""; 
    static String name = null;
    
    static String curr = "";
    
    public static void main(String[] args) {
        jf = new JFrame("ChatBox");
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        
        jp1.setBackground(Color.WHITE);
        jp2.setBackground(Color.WHITE);
        jp3.setBackground(Color.WHITE);
        jp3.setBorder(BorderFactory.createLineBorder (Color.black));
        jl = new JLabel("");
        jl.setSize(500, 500);
        jl1 = new JLabel("IP Address");
        jl2 = new JLabel("User Name");
        jl3 = new JLabel("Enter Message:");
        
        jb = new JButton("SEND");
        
        jt1 = new JTextField("");
        jt4 = new JTextArea("CHAT BOX");
        jt4.setColumns(50);
        jt4.setAlignmentY(400);
        jt4.setEditable(false);
        jt1.addActionListener(new IP());
        jt1.setColumns(10);
        jt1.setToolTipText("Enter Server address: ");
        
        jt2 = new JTextField("");
        jt2.setToolTipText("Enter your username");
        jt2.setColumns(10);
        
        jt3 = new JTextField("");
        jt3.setToolTipText("Enter your message");
        jt3.setColumns(20);
        jt3.setEditable(false);
        
        jp1.add(jl1, BorderLayout.PAGE_START);
        jp1.add(jt1, BorderLayout.PAGE_START);
        jp1.add(jl2, BorderLayout.PAGE_START);
        jp1.add(jt2, BorderLayout.PAGE_END);
        jp3.add(jl, BorderLayout.PAGE_START);
        jp3.add(jt4, BorderLayout.CENTER);
        jp2.add(jl3, BorderLayout.LINE_START);
        jp2.add(jt3, BorderLayout.CENTER);
        jp2.add(jb, BorderLayout.EAST);
        scroll = new JScrollPane (jt4);
        jf.setSize(800,800);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(jp1, BorderLayout.PAGE_START);
        jf.add(jp3, BorderLayout.CENTER);
        jf.add(jp2, BorderLayout.SOUTH);
        jf.add(scroll);
        jf.setVisible(true);
        
        while(address.equals("")){System.out.print("");}
        
        System.out.println("Yeah it's working");
        try{
            Socket s = new Socket(address,5190);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            jt2.addActionListener(new Name(out));
            jb.addActionListener(new Message(out));
            jt3.addActionListener(new Message(out));
            new Read(s).start();
          
        }
        catch(IOException e){
            System.out.println("Connection to google failed. :(");
        }
        
    }
    



static class IP implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            JTextField jt = (JTextField) ae.getSource();
            address = jt.getText();
            jt.setEditable(false);
            
        }
    }


static class Name implements ActionListener{
        PrintWriter out;
        Name(PrintWriter newOut){out = newOut;}
        @Override
        public void actionPerformed(ActionEvent ae){
            JTextField jt = (JTextField) ae.getSource();
            name = jt.getText();
            out.println(name);
            jt3.setEditable(true);
            jt.setEditable(false);
            
        }
    }

static class Message implements ActionListener{
        //Socket s;
        PrintWriter out;
        //Message(Socket newS){s = newS;}
        Message(PrintWriter newOut){out = newOut;}
        @Override
        public void actionPerformed(ActionEvent ae){
            String line = jt3.getText();
            out.println(line);
            jt3.setText("");
        
        }   
    }
    



static class Read extends Thread{
    Socket s;
    Read(Socket newS){s = newS;}
    synchronized public void run(){
        Scanner sin;
        curr = jt4.getText();
        try {
            sin = new Scanner(s.getInputStream());
            while(!s.isClosed()){
                
                while(sin.hasNext()){
                    curr =curr+"\n"+sin.nextLine();
                    jt4.setText(curr);
                }
            }
            
        } catch (IOException ex) {
          System.out.println("EXIT");
        }
        
    }
}

}

