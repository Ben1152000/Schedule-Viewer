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
		// Define strings
		String nameStr = "" + blockClass;
		String startStr = "start: " + startTime.toString();
		String endStr = "end: " + endTime.toString();
		String roomStr = blockClass.getRoom() != 0? "room: " + blockClass.getRoom(): null;
		String teacherStr = blockClass.getTeacher() != null? "teacher: " + blockClass.getTeacher(): null;
		
		// Determine menu width
		int w = g.getFontMetrics().stringWidth(nameStr);
		w = Math.max(w, g.getFontMetrics().stringWidth(startStr));
		w = Math.max(w, g.getFontMetrics().stringWidth(endStr));
		w = roomStr != null? Math.max(w, g.getFontMetrics().stringWidth(roomStr)): w;
		w = teacherStr != null? Math.max(w, g.getFontMetrics().stringWidth(teacherStr)): w;
		w += 20;
		
		// Determine menu height
		int numRows = 5 - (roomStr != null? 0: 1) - (teacherStr != null? 0: 1);
		int h = numRows * (int) (g.getFontMetrics().getStringBounds(nameStr, g).getHeight() * 1.4);
		
		// Make sure menu doesn't exceed window boundaries
		if (x + w > rightEdge)
		{
			x -= w;
		}
		if (y + h > lowerEdge)
		{
			y -= h;
		}
		
		// Draw menu
		g.setColor(blockClass.getColor());
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
		g.setColor(blockClass.getColor().getRed() * 0.213 + blockClass.getColor().getGreen() * 0.715 + blockClass.getColor().getBlue() * 0.072 > 128? Color.black: Color.white); // Determine proper text color
		Viewer.drawCenteredString(g, nameStr, new Rectangle(x, y, w, h/numRows));
		Viewer.drawCenteredString(g, startStr, new Rectangle(x, y + h/numRows, w, h/numRows));
		Viewer.drawCenteredString(g, endStr, new Rectangle(x, y + 2 * h/numRows, w, h/numRows));
		if (roomStr != null)
		{
			Viewer.drawCenteredString(g, roomStr, new Rectangle(x, y + (teacherStr == null? 3: 4) * h/numRows, w, h/numRows));
		}
		if (teacherStr != null)
		{
			Viewer.drawCenteredString(g, teacherStr, new Rectangle(x, y + 3 * h/numRows, w, h/numRows));
		}
		
	}
	
	
}
