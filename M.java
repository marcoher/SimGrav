import java.io.*;
import java.awt.*;
import java.awt.geom.*;
class M{
	static public double potencia(double x, int n){
		if (n == 1) return x;
		else if (n%2==0) return potencia(x,n/2)*potencia(x,n/2);
		else return x*potencia(x,n-1);
	}
	static public void printLn(String x){
		System.out.println(x);
	}
	static public double azar(double x,double y){
		return x+Math.random()*(y-x+1);
	}
	static public int readInt(String x) throws IOException{
		return Integer.parseInt(readLine(x));
	}
	static public String readLine(String x) throws IOException{
		print(x); return teclado.readLine();
	}
	static public BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
	static public double readDouble(String x) throws IOException{
		return Double.parseDouble(readLine(x));
	}
	static public void print(String x){System.out.print(x);}
	static public void println(String x){System.out.println(x);}
	static public void graficarPoint3D(Point3D []p,double ang,Graphics g){
		int W=g.getClipBounds().width;
		int H=g.getClipBounds().height;
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform T = new AffineTransform(1,0,0,-1,W/2,H/2);
		double [] Rap=new double[p.length];
		for(int i=0;i<p.length;++i){
			Rap[i]=Math.abs(2*p[i].getZ()/30);
			if(Rap[i]<2){Rap[i]=2;}
			Point2D.Double Xt= new Point2D.Double();
			T.transform(p[i].Xto2D(ang),Xt);
			g2.fill(new Ellipse2D.Double(Xt.getX(),Xt.getY(),Rap[i],Rap[i]));
		}
	}
	static public void graficarPoint3D(Point3D p,double ang,Graphics g){
		int W=g.getClipBounds().width;
		int H=g.getClipBounds().height;
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform T = new AffineTransform(1,0,0,-1,W/2,H/2);
		double Rap=Math.abs(2*p.getZ()/30);
		if(Rap<2){Rap=2;}
		Point2D.Double Xt= new Point2D.Double();
		T.transform(p.Xto2D(ang),Xt);
		g2.fill(new Ellipse2D.Double(Xt.getX(),Xt.getY(),Rap,Rap));
	}
	static public void abortar(String x){
		System.out.println("x");
		System.exit(0);
	}
	static public void graficarG(int N,int n, int v,Component comp){
		N=v*(N+1);//agr nuev
		Dimension r=comp.getSize();
		int W=r.width;
		int H=r.height;
		Graphics g =comp.getGraphics();
		Point3D [][] R=new Point3D[N][n];
		double [][]Rap=new double [N][n];
		double G=1, dt = 0.1,alfa = Math.PI/4,cosalfa=Math.cos(alfa),senalfa=Math.sin(alfa);
		//aqui parece empezar la alternativa de varias galaxias
		//if(v!=1&&v!=0){return;}
		double masa[] = new double [N];
		//if(v==1){masa[0]=260;}
		 for(int i=0;i<v;++i) masa[i]=40000; //agregado nuevo
		g.setColor(Color.black);g.fillRect(0,0,W,H);
		g.setColor(Color.white);g.drawString("Calculando...",W/2-50,H/2-10);
		ATransform t = new ATransform(0,dt,-dt,0,0,0);
		for(int i=0;i<n;++i){for(int j=0;j<N;++j){R[j][i]=new Point3D();}}
		
		for(int j=0; j<v;++j) {
			R[j][0]=new Point3D(M.azar(-W/2,W/2),M.azar(-H/2,H/2),M.azar(-100,100));}
		//agr nuev
		Point3D centrom = new Point3D();
		for(int j=0;j<v;++j) centrom=centrom.add(R[j][0].pond(1/v));
		for(int j=0; j<v;++j) R[j][1]=R[j][0];//agr nuev
		int x=0;
		for(int j=v;j<N;++j){//condiciones iniciales para los demas cuerpos
			masa[j]=M.azar(1,5);//masas
			
			//desde aqui
			/*R[j][0]=new Point3D(M.azar(-W/3,W/3),M.azar(-H/2,H/2),M.azar(-20,20));//posiciones
			double d=R[j][0].norm();//hasta aqui, se borra*/
			
			//velocidades para v>1
			
			if(j>(x+1)*(N-v)/v){x++;}
			R[j][0]=R[x][0].add(new Point3D(M.azar(-W/8,W/8),M.azar(-H/8,H/8),M.azar(-5,5)));
			double d=R[j][0].distance(R[x][0]);
			//velocidades tangenciales en xy, cero en z
			R[j][1]=R[j][0].add(new Point3D(-dt*Math.sqrt(G*masa[x])*(R[j][0].getY()-R[x][0].getY())/Math.pow(d,1.5),dt*Math.sqrt(G*masa[x])*(R[j][0].getX()-R[x][0].getX())/Math.pow(d,1.5),0));
			
			//desde aqui son velocidades para v=1 y v=0
			/*if(v==1){
			R[j][1]=R[j][0].add(new Point3D(-dt*Math.sqrt(G*masa[0])*R[j][0].getY()/Math.pow(d,1.5),dt*Math.sqrt(G*masa[0])*R[j][0].getX()/Math.pow(d,1.5),0));}
			else {
			R[j][1]=R[j][0].add(new Point3D(dt*M.azar(-20,20),dt*M.azar(-20,20),dt*M.azar(-20,10)));} //hasta aqui*/
		}
		
		for(int i=0;i<n-2;++i){//ahora el verlet.. medio enrredado
			//if(v==1){R[0][i]=new Point3D();}
			for(int j=0;j<N/2;++j){
				Point3D fuerzaG1=new Point3D();//variable auxiliar para calcular la fuerza neta sobre cada cuerpo
				Point3D fuerzaG2=new Point3D();
				for(int k=0;k<N;++k){
					if(k!=j){
						double d1 = 1+R[j][i].distance(R[k][i]);
						double d2 = 1+R[N-j-1][i].distance(R[k][i]);
						if(v>=1){if(k<v&&d1<10){d1+=10;}if(k<v&&d2<10){d2+=10;}}
						
						fuerzaG1=fuerzaG1.add(R[k][i].resta(R[j][i]).pond(masa[k]*(1/M.potencia(d1,3))));
						fuerzaG2=fuerzaG2.add(R[k][i].resta(R[N-j-1][i]).pond(masa[k]*(1/M.potencia(d2,3))));}
						
				}
				for(int q=0;q<v;++q){if(R[j][i+1].distance(R[q][i+1])<30&&j!=q){R[j][i+2]=R[q][i+1];}else{
				R[j][i+2]=R[j][i+1].pond(2).resta(R[j][i]).add(fuerzaG1.pond(dt*dt*G));}}
				for(int q=0;q<v;++q){if(R[N-j-1][i+1].distance(R[q][i+1])<30&&(N-j-1)!=q){R[N-j-1][i+2]=R[q][i+1];}else{
				R[N-j-1][i+2]=R[N-j-1][i+1].pond(2).resta(R[N-j-1][i]).add(fuerzaG2.pond(dt*dt*G));}}
				if(j>=v){
				Rap[j][i]=Math.abs(2*R[j][i].getZ()/30);}
				else{Rap[j][i]=Math.abs(2*R[j][i].getZ()/10);}
				if(N-j-1>=v){
				Rap[N-j-1][i]=Math.abs(2*R[N-j-1][i].getZ()/30);}
				else{Rap[N-j-1][i]=Math.abs(2*R[N-j-1][i].getZ()/10);}
				if(Rap[j][i]<2){Rap[j][i]=2;}if(Rap[N-j-1][i]<2){Rap[N-j-1][i]=2;}
				
						 	
			}
		}
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform T = new AffineTransform(1,0,0,-1,W/2,H/2);//transformador de coordenadas al centro
		g2.setColor(Color.blue);
		g2.fillRect(0,0,W-1,H-1);
		g2.setColor(Color.white);
		long tiempoInicio = System.currentTimeMillis();
		for(int i=0;i<n-1;++i){
			for(int j=0;j<N/2;++j){
				Point2D.Double Xt1= new Point2D.Double();//posicion auxiliar
				Point2D.Double Xt2= new Point2D.Double();
				Point2D.Double Xt11= new Point2D.Double();
				Point2D.Double Xt22= new Point2D.Double();
				T.transform(R[j][i].Xto2D(alfa),Xt1);//mover cada punto al nuevo sist. de coordenadas
				T.transform(R[j][i+1].Xto2D(alfa),Xt2);
				T.transform(R[N-j-1][i].Xto2D(alfa),Xt11);
				T.transform(R[N-j-1][i+1].Xto2D(alfa),Xt22);
				g2.setColor(Color.blue);
				g2.fill(new Ellipse2D.Double(Xt1.getX(),Xt1.getY(),Rap[j][i],Rap[j][i]));
				g2.fill(new Ellipse2D.Double(Xt11.getX(),Xt11.getY(),Rap[N-j-1][i],Rap[N-j-1][i]));
				if(j>=v){g2.setColor(Color.white);}else{g2.setColor(Color.black);}
				g2.fill(new Ellipse2D.Double(Xt2.getX(),Xt2.getY(),Rap[j][i+1],Rap[j][i+1]));//dibujar circulo en cada cuerpo
				if(N-j-1>=v){g2.setColor(Color.white);}else{g2.setColor(Color.black);}
				g2.fill(new Ellipse2D.Double(Xt22.getX(),Xt22.getY(),Rap[N-j-1][i+1],Rap[N-j-1][i+1]));
							
			}
			try {Thread.currentThread().sleep(10);}catch (InterruptedException e) {M.printLn("fallo");System.exit(0);}//esperar 1 milisegundo
		}
		long totalTiempo = System.currentTimeMillis() - tiempoInicio;
		System.out.println("La duración fue :" + totalTiempo + " miliseg");
	}
	static public void Rnov(String patron,String regla , Component comp){
		int ancho=1;
		Dimension r=comp.getSize();
		int W=r.width;
		int H=r.height;
		Graphics g =comp.getGraphics();
		g.setColor(Color.black);
		int spc[]= new int[2*(W/(2*ancho))+1];
		int spcaux[]= new int[2*(W/(2*ancho))+1];
		for(int i=0;i<spc.length;++i){spc[i]=0;}
		for(int i=0;i<patron.length();i++)
			spc[spc.length/2-patron.length()/2+i]=Integer.parseInt(patron.charAt(i)+""); 
		for(int t=1;t<H/ancho;++t){
			for(int i=0;i<spc.length;++i){
				if((spcaux[i]=cels(i,regla,spc))==1){g.fillRect(i*ancho,t*ancho,ancho,ancho);}
			}
		spc=spcaux.clone();
		}
		
			
	}
	/*static public int cel(int i,int t,int r, int [] spc){
		int resp=0;
		int [] j=new int[t];
		for(int k=1;k<t;++k){j[k]=-r;}
		for(int k=1;k<t;++k){
			for(j[k]=-r;j[k]<=r;++j[k]){
				resp=resp+spc[((i+sum(j))<0)?spc.length+(i+sum(j))%spc.length:(i+sum(j))%spc.length];
			}
		}
		return (resp%2==0)?0:1;
	}*/
	static public int sum(int [] a){
		int s=0;
		for(int i=0;i<a.length;i++){
		s+=a[i];}
		return s;
	}
	/*static public void sier(Component comp){
		int ancho=1;
		Dimension r=comp.getSize();
		int W=2*(r.width/2)+1;
		int H=2*(r.height/2)+1;
		Graphics g =comp.getGraphics();
		g.setColor(Color.black);
		int spc[]= new int[2*(W/(2*ancho))+1];
		int spcaux[]= new int[2*(W/(2*ancho))+1];
		for(int i=0;i<spc.length;++i){spc[i]=0;} 
		spc[spc.length/2+1]=1;
		for(int t=1;t<H/ancho;++t){
			for(int i=0;i<spc.length;++i){
				if((spcaux[i]=cels(i,4,spc))==1){g.fillRect(i*ancho,t*ancho,ancho,ancho);}
			}
		spc=spcaux.clone();
		}
			
	}*/
	static public int cels(int i, String regla, int [] spc){
		int s=0;
		char [] aux = regla.toCharArray();
		int [] ga = new int [aux.length];
		for(int k=0;k<aux.length;k++) ga[k]=Integer.parseInt(aux[k]+"");
		for(int k=0;k<ga.length;k++) s+=ga[k]*modulo(spc[modulo(i-k,spc.length)]+spc[modulo(i+k,spc.length)],2);
		//s+=modulo(spc[modulo(i-1,spc.length)]+spc[modulo(i+1,spc.length)],2);
		return modulo(s,2);
		
	}
	static public int prod(int [] ga, int [] j){
		int pr=1;
		for(int i=0;i<j.length;i++){
			pr=pr*ga[j[i]+1];}
		return pr;
	}
	static public int modulo(int n,int m){
		int value=n%m;
		if(value<0){value+=m;}
		return value;
	}
	static public int fac(int n){
		if(n==0){return 1;}
		else{ return n*fac(n-1);}
	}
	public static long bCoef(int n, int r) {
        long t = 1;
	int k=0;
        
        int m = n - r; // r = Math.max(r, n - r);
        if (r < m) {
            r = m;
        }
        
        for (int i = n, j = 1; i > r; i--, j++) {
            t = t * i / j;
        }
        
        return t;
    }
	public static int gcd(int m, int n) {

        if (m < n) {
            int t = m;
            m = n;
            n = t;
        }

        int r = m % n;

        if (r == 0) {
            return n;
        } else {
            return gcd(n, r);
        }

    }

    
	
					
}


















