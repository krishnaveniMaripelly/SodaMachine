import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Stack;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class SodaMachine extends JFrame implements MachineActions{
  boolean hasQuaters = false;
  Stack<Transaction> txns = new Stack<Transaction>();
  final int initialCapacity=40;
  Stack<SodaCan> sodas = new Stack<SodaCan>();  
  private JLabel textLbl;
  JPanel displayPanel=new JPanel();  
  JTextField inputF = new JTextField(10);
  SodaMachine() {
    //coke cans
    for(int i = 1; i<=initialCapacity;i++){
      final SodaCan x= new SodaCan("coke",i);
      this.sodas.push(x);
    }

        JButton b=new JButton("Get soda");//create button  
         b.setBounds(130,100,100, 40);  
         b.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            System.out.println("insert coin clicked");
            try {
              int coins = Integer.parseInt(inputF.getText());
              insertCoin(coins);
            } catch (Exception err) {
              //TODO: handle exception
            }


          }
           
         });
         textLbl = new JLabel("Number of sodas available: "+sodas.size());
       
        // add(textLbl);
        // this.dispensedLbl = new JLabel("");
        // add(dispensedLbl);
        JPanel topPanel=new JPanel();
        topPanel.setLayout(new GridLayout(4,1));
        JLabel inputL = new JLabel("Enter number of quarters and push the button");
        topPanel.add(inputL);
        topPanel.add(inputF);
        topPanel.add(b);
        topPanel.add(textLbl);
        add(topPanel);//adding button on frame    
        add(displayPanel);
        setSize(400,500);//400 width and 500 height  
        setLayout(new GridLayout(4,1));//using no layout managers  
        setVisible(true);//making the frame visible 
        
  }
 public int getSodasCount(){
    return sodas.size();
  }
  @Override
  public int insertCoin(int coins) {
    Stack<SodaCan> soldSodas = new Stack<SodaCan>();
     displayPanel.removeAll();
     displayPanel.revalidate();
     displayPanel.repaint();
     this.inputF.setText("");
     if(coins>=4) {
      JLabel t = new JLabel("Only 4 sodas allowed per txn");
      displayPanel.add(t);
      txns.push(new Transaction(coins,soldSodas,false));
       return -1;
     }
    if(sodas.size()==0 || coins>sodas.size()) {
      System.out.println("Out of soda");
      JLabel t = new JLabel("No sodas available to dispense");
      displayPanel.add(t);
      txns.push(new Transaction(coins,soldSodas,false));
      return -1;
    }
    if(hasQuaters){
      ejectCoin();
    }
    hasQuaters=true;
    // TODO Auto-generated method stub
    System.out.println("Coin inserted");
    // Stack<SodaCan> d= new Stack<SodaCan>();
    for(int i =0;i<coins;i++){
     
      SodaCan s = dispenseSoda();
      JLabel j = new JLabel("Dispensed soda, name: "+s.getName()+" item Id"+s.getId());
      displayPanel.add(j);
      soldSodas.push(s);
    }
    txns.push(new Transaction(coins,soldSodas,false));
    return 0;
  }
  private
   int removeCoin() {
     System.out.println("Remove coin");
    // TODO Auto-generated method stub
    return 0;
  }
 private
   SodaCan dispenseSoda() {
    // TODO Auto-generated method stub
    SodaCan x = sodas.pop();
    System.out.println("Dispensed soda: "+x.getName()+x.getId());
    hasQuaters=false;
    if(sodas.size()==0) {
      textLbl.setText("Out of sodas: "+sodas.size());
      System.out.println("Out of soda");
    }else{
      textLbl.setText("Number of sodas available: "+sodas.size());
      System.out.println("Remaining sodas: "+sodas.size());
    }
    
    return x;
  }
  private int ejectCoin() {
    hasQuaters=false;
    System.out.println("Eject coin");
      // TODO Auto-generated method stub
      return 0;
  }

}

interface MachineActions {
public int insertCoin(int coins);
}
class SodaCan {
  private int id;
  private String name = "Coke";
  private int cost=25;
  private int quantityInMl=250;
  SodaCan(String name,int x){
    this.name = name; 
    id = x;
  }
  public int getId() {
      return id;
  }
  public String getName() {
      return name;
  }
}
class Transaction {
  static int id=0;
  Stack<SodaCan> sodas;
  int coins = 0;
  boolean success;
  Timestamp timestamp;
  Transaction(int coins,Stack<SodaCan> sodas,boolean success){
    this.coins = coins;
    this.sodas= sodas;
    this.success= success;
    this.timestamp = new Timestamp(System.currentTimeMillis());
    id++;
  }
}
public class Main {
    public static void main(String[] args) {
        
        SodaMachine a =  new SodaMachine();
        int count = a.getSodasCount();
        System.out.println(count);
    }
}
