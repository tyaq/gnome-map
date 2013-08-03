package roadMapGUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import roadMap.Graph;
import roadMap.Road;
import roadMap.Village;

public class PaintGraph extends JComponent{
	private BufferedImage[] img;
	private HashMap<Village, Point2D> coordinates;
	private Graph g;
	public PaintGraph(Graph g){
		coordinates =new HashMap<Village,Point2D>();
		
		setLayout(new FlowLayout());
		this.setOpaque(true);
		this.g=g;
		populateCircleCoordinates(g);
		img=images();
		
		
	}//End gui method
	
	public void setGraph(Graph g){
		this.g=g;
	}
	
	public void inject(JFrame frame){
		//Paint graph
        frame.add(new JScrollPane(this), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
	}
	
	
	void populateCircleCoordinates(Graph g){
		//arrange them in gridish format
		int h =700;
		int w = 900;
		int numV = g.verticesConnections().keySet().size();
		int seperations = ((int) (Math.ceil(Math.sqrt(numV))));
		double dx =w/(seperations+1);
		double dy =h/(seperations+1);
		int r = (int) ((h-dx)/2);
		int i = 1;//Segment shifter
		
		HashMap<Village, Point2D> tempCoordinates= new HashMap<Village, Point2D>();
		for (Village v:g.verticesConnections().keySet()){
			Point2D point =new Point2D.Double((r*(Math.cos((2*Math.PI/numV)*i)))+w/2,(r*(Math.sin((2*Math.PI/numV)*i)))+h/2);
//			System.out.println(point);
			tempCoordinates.put(v, point);
			i++;
			if (i+1==numV) {
				System.out.println("error exceeded size");
			}
			/*i++;
			if (i==seperations+1) {//See if need to move to new row
				i=1;
				j++;
			}//End if
			if (j==seperations+2) {
				System.out.println("error exceeded size");
			}*/
		}//End for
		System.out.println("Done populating");
		coordinates=tempCoordinates;
	}
	
	void populateCoordinates(Graph g){
		//arrange them in gridish format
		int h =700;
		int w = 900;
		int numV = g.verticesConnections().keySet().size();
		int seperations = ((int) (Math.ceil(Math.sqrt(numV))));
		double dx =w/(seperations+1);
		double dy =h/(seperations+1);
		int i=1;//HOrizontal shifter
		int j=1;//Vertical shifter
//		System.out.println(h);
//		System.out.println(w);
//		System.out.println(numV);
//		System.out.println(seperations);
//		System.out.println(dx);
//		System.out.println(dy);
		for (Village v:g.verticesConnections().keySet()){
			Point2D point =new Point2D.Double(dx*i,dy*j);
			
			coordinates.put(v, point);
			i++;
			if (i==seperations+1) {//See if need to move to new row
				i=1;
				j++;
			}//End if
			if (j==seperations+2) {
				System.out.println("error exceeded size");
			}
		}//End for
		System.out.println("Done populating");
		
	}
	
	final static float dash1[] = {10.0f};
	final Color[] colors={new Color(245,165,3),new Color(74,217,217),new Color(242,56,90),new Color(233,241,209)};
	
	protected void paintComponent(Graphics graphics){
		populateCircleCoordinates(g);
		System.out.println(g);
		Graphics2D g2 = (Graphics2D)graphics;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.BLACK);
		for (Road e:g.edges()) {
			//e.draw(g);
			
			g2.setColor(colors[(int)(Math.random()*colors.length)]);
			Point2D a = coordinates.get(e.tail());
			Point2D b = coordinates.get(e.tip());
			
			g2.setStroke(new BasicStroke(5));
			
			//g2.drawLine((int) a.getX(),(int) a.getY(),(int) b.getX(),(int) b.getY());
			//Lines
			Polygon arrow=new Polygon();
			
			double dx =(int)a.getX()-(int)b.getX();
			double dy =(int)a.getY()-(int)b.getY();
			
			double c,s;
			if (dy==0) {
				
				c=0;
				s=1;
				
			}else if (dx==0) {
				
				c=1;
				s=0;
				
			}else{
			double invM=(dx/dy);//Get the inverse slope of line
			
			
			c=1/(Math.sqrt(1+(invM*invM)));
			s=invM/(Math.sqrt(1+(invM*invM)));
			
			}
			
			arrow.addPoint((int)(a.getX()+10*c),(int) (a.getY()-10*s));
			arrow.addPoint((int) (a.getX()-10*c),(int) (a.getY()+10*s));
			
			arrow.addPoint((int) b.getX(),(int) b.getY());
			g2.drawLine((int)a.getX(),(int) a.getY(),(int) b.getX(),(int) b.getY());
			g2.fillPolygon(arrow);
			if (e.pair()!=null) {
				
				g2.setStroke(new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f));
				g2.setColor(Color.BLACK);
			}//Dashed line
			g2.drawLine((int)a.getX(),(int) a.getY(),(int) b.getX(),(int) b.getY());
		}//end for
		g2.setColor(Color.BLACK);
		
		System.out.println(coordinates.keySet());
		for (Village v:coordinates.keySet()) {
			//v.draw(v);
			Point2D point =coordinates.get(v);
			
			g2.drawImage(img[(int) (Math.random()*img.length)], (int) point.getX()-25, (int) point.getY()-40, 50, 50, null);
			g2.drawString(v.data(),((int) (point.getX())-4-25),((int) (point.getY())-4));
			//Oval nodes
//			g2.fillOval((int) point.getX()-15,(int) point.getY()-15, 30, 30);
//			g2.setColor(Color.WHITE);
//			g2.drawString(v.data(),((int) (point.getX()))-4,((int) (point.getY()))+g2.getFontMetrics().getHeight()/2/2);
//			g2.setColor(Color.BLACK);
//			
		}//End for
	}//end Method
	
	private BufferedImage[] images(){
		BufferedImage image1=null;
		BufferedImage image2=null;
		BufferedImage image3=null;
		try { 
			String url=getClass().getResource("Village1.png").getPath();
			url = url.replace("%20", " ");
			System.out.println(url);
			image1 = ImageIO.read(new File(url));
	        
	       } catch (IOException ex) {
	    	   	System.out.println("exception v1");
	    	   	
	       }
		try { 
			String url=getClass().getResource("Village2.png").getPath();
			url = url.replace("%20", " ");
			System.out.println(url);
			image2 = ImageIO.read(new File(url));
	        
	       } catch (IOException ex) {
	    	   	System.out.println("exception v2");
	    	   
	       }
		try { 
			String url=getClass().getResource("Village3.png").getPath();
			url = url.replace("%20", " ");
			System.out.println(url);
			image3 = ImageIO.read(new File(url));
	        
	       } catch (IOException ex) {
	    	   	System.out.println("exception v3");
	    	   
	       }
		 BufferedImage[] villis = {image1,image2,image3};
		 return villis;
	}
}
