import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Class
{
	private String name;
	private Color color;
	
	public Class(String n)
	{
		name = n;
		color = Color.white;
	}
	
	public Class(String n, Color c)
	{
		name = n;
		color = c;
	}
	
	public void paint(Graphics g, int x, int y, int w, int h)
	{
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		Viewer.drawCenteredString(g, name, new Rectangle(x, y, w, h));
	}
	
	public void drawMenu(Graphics g, int x, int y, int w, int h)
	{
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		Viewer.drawCenteredString(g, name, new Rectangle(x, y, w, h/3));
	}
	
	public String toString()
	{
		return name;
	}
	
	
}
