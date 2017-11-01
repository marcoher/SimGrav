import java.io.*;
import java.awt.*;
import java.awt.geom.*;
class Point3D{
	private double x,y,z;
	public Point3D(){
	x=y=z=0;
	}
	public Point3D(double xi,double yi,double zi){
	x=xi;y=yi;z=zi;
	}
	public Point3D(Point3D p){
		x = p.x;
		y = p.y;
		z = p.z;
	}
	public Point3D clone(){return new Point3D(x,y,z);}
	public double getX(){return x;}
	public double getY(){return y;}
	public double getZ(){return z;}
	public Point3D add(Point3D p){
		return new Point3D(x+p.x,y+p.y,z+p.z);
	}
	public Point3D add(Point3D p1,Point3D p2){
		return new Point3D(x+p1.x+p2.x,y+p1.y+p2.y,z+p1.z+p2.z);
	}
	public Point3D pond(double a){
		return new Point3D(a*x,a*y,a*z);
	}		
	public Point3D resta(Point3D p){
		return this.add(p.pond(-1));
	}
	public double distance(Point3D p){
		return Math.sqrt(M.potencia(x-p.x,2)+M.potencia(y-p.y,2)+M.potencia(z-p.z,2));
	}
	public double distanceSq(Point3D p){
		return (M.potencia(x-p.x,2)+M.potencia(y-p.y,2)+M.potencia(z-p.z,2));
	}
	public Point2D.Double Zto2D(double ang){
		return new Point2D.Double(x-z*Math.cos(ang),y-z*Math.cos(ang)*Math.sin(ang));
	}
	public Point2D.Double Xto2D(double ang){
		return new Point2D.Double(y-x*Math.cos(ang),z-x*Math.cos(ang)*Math.sin(ang));
	}
	public double Ppunto(Point3D p){
		return x*p.x+y*p.y+z*p.z;
	}
	public Point3D Pcruz(Point3D p){
		return new Point3D(y*p.z-z*p.y,p.x*z-p.z*x,x*p.y-y*p.x);
	}
	public double norm(){
		return this.distance(new Point3D());
	}
	public double normSq(){
		return this.distanceSq(new Point3D());
	}	
	
}	
	
	
		
