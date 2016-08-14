import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.time.LocalDateTime;

public class Pointer
{
	private Time time;
	private int day;
	private int xVal, yVal, rad;
	
	public Pointer()
	{
		update();
		rad = 5;
	}
	
	public void update()
	{
		LocalDateTime today = LocalDateTime.now();
		time = new Time(today.getHour(), today.getMinute());
		day = today.getDayOfWeek().getValue() - 1;
	}
	
	public void paint(Graphics g, int x, int y, int w, int h, Time minTime, Time maxTime, Time minTimeOnDay, Time maxTimeOnDay)
	{
		
		if (day < 5 && time.compareTo(minTimeOnDay) >= 0 && time.compareTo(maxTimeOnDay) <= 0)
		{
			int boxW = w / 6 + 6;
			int boxX = x + day * (w * 5 / 24) - 3;
			int boxY = y + (int) (h * (time.getMinutesFromMidnight() - minTime.getMinutesFromMidnight()) / ((maxTime.getMinutesFromMidnight() - minTime.getMinutesFromMidnight())));
			int boxH = 2;
			xVal = boxX - rad;
			yVal = boxY;
			g.setColor(Color.black);
			g.drawRect(boxX, boxY, boxW, boxH);
			g.drawOval(boxX - 10, boxY - 5 + 1, 2 * rad, 2 * rad);
			g.setColor(Color.decode("#A00000"));
			g.fillRect(boxX, boxY, boxW, boxH);
			g.fillOval(boxX - 10, boxY - 5 + 1, 2 * rad, 2 * rad);
		}
	}
	
	public void drawMenu(Graphics g, int cX, int cY, int rightEdge, int lowerEdge, int minsUntilNextBlock)
	{
		if ((cX - xVal) * (cX - xVal) + (cY - yVal) * (cY - yVal) <= rad * rad)
		{
			// Define strings
			String timeStr = "Current time: " + time;
			String remStr = minsUntilNextBlock > 0? "Time left in block: " + minsUntilNextBlock + " mins": null;
			
			// Determine menu width
			int w = Math.max(g.getFontMetrics().stringWidth(timeStr), remStr != null? g.getFontMetrics().stringWidth(remStr): -1) + 20;
			
			// Determine menu height
			int numRows = 2 - (remStr == null? 1: 0);
			int h = numRows * (int) (g.getFontMetrics().getStringBounds(timeStr, g).getHeight() * 1.4);
			
			// Make sure menu doesn't exceed window boundaries
			if (xVal + w > rightEdge)
			{
				xVal -= w;
			}
			if (yVal + h > lowerEdge)
			{
				yVal -= h;
			}
			
			// Draw menu
			g.setColor(Color.black);
			g.drawRect(xVal, yVal, w, h);
			g.setColor(Color.decode("#A00000"));
			g.fillRect(xVal, yVal, w, h);
			
			g.setColor(Color.white);
			Viewer.drawCenteredString(g, timeStr, new Rectangle(xVal, yVal, w, h/numRows));
			if (remStr != null)
			{
				Viewer.drawCenteredString(g, remStr, new Rectangle(xVal, yVal + h/numRows, w, h/numRows));
			}
			
		}
	}
	
	public Time currentTime()
	{
		return time;
	}
	
	public int getDay()
	{
		return day;
	}
	
}
