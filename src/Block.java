import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block
{
	private Class blockClass;
	private Time startTime;
	private Time endTime;
	
	public Block(Class cl, Time start, Time end)
	{
		blockClass = cl;
		startTime = start;
		endTime = end;
	}
	
	
	public Time startTime()
	{
		return startTime;
	}
	
	public Time endTime()
	{
		return endTime;
	}
	
	public String toString()
	{
		return blockClass.toString() + "(" + startTime + "-" + endTime + ")";
	}
	
	public void paint(Graphics g, int x, int y, int w, int h)
	{
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
		blockClass.paint(g, x, y, w, h);
	}
	
	public void drawMenu(Graphics g, int x, int y, int rightEdge, int lowerEdge)
	{
		int w = 100;
		int h = 100;
		
		if (x + w > rightEdge)
		{
			x -= w;
		}
		if (y + h > lowerEdge)
		{
			y -= h;
		}
		
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
		blockClass.drawMenu(g, x, y, w, h);
		Viewer.drawCenteredString(g, "start: " + startTime.toString(), new Rectangle(x, y + h/3, w, h/3));
		Viewer.drawCenteredString(g, "end: " + endTime.toString(), new Rectangle(x, y + 2*h/3, w, h/3));
		
	}
	
	
}
