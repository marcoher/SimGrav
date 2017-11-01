import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Ca extends Frame implements ActionListener{
	//parametros 
	private int W=1300,H=790;
	private Canvas cv=new Canvas();
	private Panel p=new Panel(), p1=new Panel(),p2=new Panel();
	private Button [] b={new Button("Iniciar"),new Button("Salir")}; 
	private TextField t= new TextField("inicial"), t3=new TextField("regla");
	private String patron, regla;
	private int r;
	
	public static void main (String[]args) throws IOException{
		new Ca().setVisible(true);
	}
	
	public Ca(){
		setSize(W,H);
		setLocation(0,0);
		setTitle("Regla 90");
		setLayout(new BorderLayout());
		cv.setSize(W-125,H-39);
		p.setLayout(new GridLayout(2,1));
		p1.setLayout(new GridLayout(1,3));
		p2.setLayout(new FlowLayout());
		p1.add(t); p1.add(t3); p1.add(b[0]);
		p2.add(b[1]);
		p.add(p1); p.add(p2);
		t.addActionListener(this);
		
		t3.addActionListener(this);
		b[0].addActionListener(this);
		b[1].addActionListener(this);
		add("Center",cv);add("South",p);
	}
	public void actionPerformed(ActionEvent x){
		if(x.getSource()==b[1]){System.exit(0);}
		if(x.getSource()==t){patron=new String(t.getText());}
		if(x.getSource()==t3){regla=new String(t3.getText());}
		if(x.getSource()==b[0]){
			patron=new String(t.getText());
			regla=new String(t3.getText());
			M.Rnov(patron,regla,cv);}
		}
}