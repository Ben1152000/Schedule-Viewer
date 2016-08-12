import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDateTime;

public class Pointer
{
	private Time time;
	private int day;
	
	public Pointer()
	{
		update();
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
			g.setColor(Color.decode("#A00000"));
			g.fillRect(boxX, boxY, boxW, boxH);
			g.fillOval(boxX - 10, boxY - 5 + 1, 10, 10);
		}
	}
	
	public int getDay()
	{
		return day;
	}
	
}
