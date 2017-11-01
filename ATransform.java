import java.io.*;
import java.awt.*;
import java.awt.geom.*;
class ATransform{
	private double a[][]=new double [3][3]; 
	private double [] b=new double [3];
	public ATransform(){
		for(int i=0;i<3;++i){b[i]=0;
		for(int j=0;j<3;++j){
		if(j==i){a[i][j]=1;}else a[i][j]=0;}}
	}
	public ATransform(double [][]m,double []n){	
		for(int i=0;i<3;++i){b[i]=n[i];
		for(int j=0;j<3;++j){
		a[i][j]=m[i][j];}}
	}
	public ATransform(double a00,double a10,double a01,double a11,double b0,double b1){
		a[0][2]=a[1][2]=a[2][0]=a[2][1]=b[2]=0;a[2][2]=1;
		a[0][0]=a00; a[1][0]=a10; a[0][1]=a01; a[1][1]=a11;b[0]=b0;b[1]=b1;
	}
	public void xyRot(double ang){
		for(int i=0;i<3;++i){b[i]=0;}
		a[0][0]=a[1][1]=Math.cos(ang);
		a[0][1]=Math.sin(ang);a[1][0]=-a[0][1];
		a[2][2]=1;
		a[0][2]=a[1][2]=a[2][0]=a[2][1]=0;
	}
	public Point3D transform(Point3D p){
		return new Point3D(a[0][0]*p.getX()+a[0][1]*p.getY()+a[0][1]*p.getZ()+b[0],a[1][0]*p.getX()+a[1][1]*p.getY()+a[1][2]*p.getZ()+b[1],a[2][0]*p.getX()+a[2][1]*p.getY()+a[2][2]*p.getZ()+b[2]);
	}
}	
			
