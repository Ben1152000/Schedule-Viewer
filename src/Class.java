import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Class
{
	private String name;
	private Color color;
	private int room;
	private String teacher;
	
	public Class(String n)
	{
		name = n;
		color = Color.white;
		room = -1;
		teacher = null;
	}
	
	public Class(String n, int r)
	{
		name = n;
		color = Color.white;
		room = r;
		teacher = null;
	}
	
	public Class(String n, String t)
	{
		name = n;
		color = Color.white;
		room = -1;
		teacher = t;
	}
	
	public Class(String n, String t, int r)
	{
		name = n;
		color = Color.white;
		room = r;
		teacher = t;
	}
	
	public Class(String n, Color c)
	{
		name = n;
		color = c;
		room = -1;
		teacher = null;
	}
	
	public Class(String n, Color c, int r)
	{
		name = n;
		color = c;
		room = r;
		teacher = null;
	}
	
	public Class(String n, Color c, String t)
	{
		name = n;
		color = c;
		room = -1;
		teacher = t;
	}
	
	public Class(String n, Color c, String t, int r)
	{
		name = n;
		color = c;
		room = r;
		teacher = t;
	}
	
	public void paint(Graphics g, int x, int y, int w, int h)
	{
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.setColor(color.getRed() * 0.213 + color.getGreen() * 0.715 + color.getBlue() * 0.072 > 128? Color.black: Color.white); // Determine proper text color
		Viewer.drawCenteredString(g, name, new Rectangle(x, y, w, h));
	}
	
	public String toString()
	{
		return name;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public int getRoom()
	{
		return room;
	}
	
	public String getTeacher()
	{
		return teacher;
	}
	
	
}
