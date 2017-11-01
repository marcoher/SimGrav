import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
class SimGrav3 extends Frame implements ActionListener{
	
	private int W=1000,H=700;
	private Button [] b={new Button("Iniciar"),new Button("Acerca de"),new Button("Salir")}; 
	private TextField[] t={new TextField(),new TextField(),new TextField()};
	private Label[] l ={new Label("Nº Galaxias:"),new Label("Nº estrellas/galaxia:"),new Label("Duracion:")};
	private Canvas cv=new Canvas(),cv2=new Canvas();
	private Panel p=new Panel(),p1=new Panel(),p2=new Panel(),p3=new Panel();
	private Button b2=new Button("Quitar");
	private Frame acercade=new Frame("Acerca de");
	
	private int N,n,v;
	
	public static void main (String[]args) throws IOException{
		new SimGrav3().setVisible(true);
	}
	
	public SimGrav3(){
		setSize(W,H);
		setLocation(150,50);
		setTitle("(Otra) Simulación de interacción gravitatoria");
		setLayout(new BorderLayout());
		cv.setSize(W-125,H-40);
		cv.setBackground(Color.black);
		p.setLayout(new GridLayout(2,1));
		p2.setLayout(new FlowLayout());
		p1.setLayout(new GridLayout(1,6));
		p1.add(l[0]);p1.add(t[2]);p1.add(l[1]);p1.add(t[0]);p1.add(l[2]);p1.add(t[1]);p1.add(b[0]);
		for(int i=2;i<3;++i)
		p2.add(b[i]);
		p.add(p1);p.add(p2);
		add("Center",cv);add("South",p);
		t[0].addActionListener(this);
		t[1].addActionListener(this);
		t[2].addActionListener(this);
		for(int i=0;i<3;++i)
		b[i].addActionListener(this);
		acercade.setSize(620,300);
		acercade.setLocation(0,0);
		acercade.setLayout(new BorderLayout());
		p3.setLayout(new FlowLayout());
		p3.add(b2);
		acercade.add("South",p3);
		b2.addActionListener(this);	
		acercade.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent x){
		if(x.getSource()==b[2]){System.exit(0);}
		if(x.getSource()==t[0]){N=Integer.parseInt(t[0].getText());}
		if(x.getSource()==t[1]){n=(100/31)*(10*Integer.parseInt(t[1].getText()));}
		if(x.getSource()==t[2]){v=Integer.parseInt(t[2].getText());}
		if(x.getSource()==b[0]){M2.graficarG(N,n,v,cv);}
		if(x.getSource()==b[1]){acercade.setVisible(true);
		acercade.toFront();
		Graphics g= acercade.getGraphics();
		
		g.drawString("Escala: 1 pixel = 1.3·10^3 a.l. (año luz)",40,70);
		g.drawString("           1 u.m. (unidad de masa) = 1 masa solar",40,90);
		g.drawString("           1 u.t. (unidad de tiempo) = 10^18 segundos",40,110);
		g.drawString("	           G~1 en estas dimensiones",40,130);
		g.drawString("Simulacion: Masa central= 2.6·10^6 u.m. ~ masa del bulbo de la Via Latea",40,150);
		g.drawString("                 Masas circundantes= 1-5 u.m ~ masa de una estrella pequeña",40,170);
		g.drawString("                 Diametro=600-700 pixeles ~ 10^5 a.l ~ diametro de la Via Lactea",40,190);
		g.drawString("Autor: Marco Hernandez Cadis",40,225);
		g.drawString("Copyleft",40,250);
		acercade.setVisible(true);}
		if(x.getSource()==b2){acercade.dispose();}
	}
}		
		
		
		
		
		
		
		
		
		
		
				
