import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Schedule
{
	ArrayList<ArrayList<Block>> schedule = new ArrayList<ArrayList<Block>>();
	
	final String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri"};
	
	public Schedule()
	{
		for (int day = 0; day < 5; day++)
		{
			schedule.add(new ArrayList<Block>());
		}
	}
	
	public void addBlock(Block b, int day)
	{
		int n = 0;
		while (n < schedule.get(day).size() && b.startTime().compareTo(schedule.get(day).get(n).startTime()) > 0)
		{
			n++;
		}
		schedule.get(day).add(n, b);
	}
	
	public String toString()
	{
		String str = "";
		for (int day = 0; day < 5; day++)
		{
			if (schedule.get(day).size() > 0)
			{
				str += schedule.get(day).get(0);
			}
			for (int b = 1; b < schedule.get(day).size(); b++)
			{
				str += " " + schedule.get(day).get(b);
			}
			if (day != 4)
			{
				str += "\n";
			}
		}
		return str;
	}
	
	public Time getEarliestTime()
	{
		Time firstTime = null;
		for (int day = 0; day < 5; day++)
		{
			if (schedule.get(day).size() > 0)
			{
				if ((firstTime == null) || firstTime.compareTo(schedule.get(day).get(0).startTime()) > 0)
				{
					firstTime = schedule.get(day).get(0).startTime();
				}
			}
		}
		return firstTime;
	}
	
	public Time getLatestTime()
	{
		Time lastTime = null;
		for (int day = 0; day < 5; day++)
		{
			if (schedule.get(day).size() > 0)
			{
				if ((lastTime == null) || lastTime.compareTo(schedule.get(day).get(schedule.get(day).size() - 1).endTime()) < 0)
				{
					lastTime = schedule.get(day).get(schedule.get(day).size() - 1).endTime();
				}
			}
		}
		return lastTime;
	}
	
	public Time getEarliestTime(int day)
	{
		if (day < 5 && schedule.get(day).size() > 0)
		{
			return schedule.get(day).get(0).startTime();
		}
		return null;
	}
	
	public Time getLatestTime(int day)
	{
		if (day < 5 && schedule.get(day).size() > 0)
		{
			return schedule.get(day).get(schedule.get(day).size() - 1).endTime();
		}
		return null;
	}
	
	public int minutesLeftInBlock(Time currentTime, int day)
	{
		for (Block block : schedule.get(day))
		{
			if (block.startTime().compareTo(currentTime) > 0)
			{
				return block.startTime().getMinutesFromMidnight() - currentTime.getMinutesFromMidnight();
			}
			else if (block.endTime().compareTo(currentTime) > 0)
			{
				return block.endTime().getMinutesFromMidnight() - currentTime.getMinutesFromMidnight();
			}
		}
		return -1;
	}
	
	public void paint(Graphics g, int x, int y, int w, int h)
	{
		// get time ranges:
		
		Time firstTime = getEarliestTime();
		Time lastTime = getLatestTime();
		
		if (firstTime != null)
		{
			for (int col = 0; col < 5; col++)
			{
				if (schedule.get(col).size() > 0)
				{
					// determine width:
					int colW = w / 6;
					int colX = x + col * (colW + colW / 4);
					// determine height
					double scaleFactor = (double) h / (double) (lastTime.getMinutesFromMidnight() - firstTime.getMinutesFromMidnight());
					int colH = (int) (scaleFactor * (schedule.get(col).get(schedule.get(col).size() - 1).endTime().getMinutesFromMidnight() - schedule.get(col).get(0).startTime().getMinutesFromMidnight()));
					int colY = y + (int) (scaleFactor * (schedule.get(col).get(0).startTime().getMinutesFromMidnight() - firstTime.getMinutesFromMidnight()));
					g.setColor(Color.black);
					g.drawRect(colX, colY, colW, colH - 1); // For some reason the -1 is important
					
					// Write the name of the day:
					Viewer.drawCenteredString(g, dayNames[col], new Rectangle(colX, colY - 20, colW, 20));
					
					g.setColor(Color.white);
					g.fillRect(colX, colY, colW, colH - 1);
					for (int b = 0; b < schedule.get(col).size(); b++)
					{
						schedule.get(col).get(b).paint(g, colX, colY + (int) (scaleFactor * (schedule.get(col).get(b).startTime().getMinutesFromMidnight() - schedule.get(col).get(0).startTime().getMinutesFromMidnight())), colW, (int) (scaleFactor * (schedule.get(col).get(b).endTime().getMinutesFromMidnight() - schedule.get(col).get(b).startTime().getMinutesFromMidnight())));
					}
				}
			}
		}
		
	}
	
	public void drawMenu(Graphics g, int ptX, int ptY, int x, int y, int w, int h)
	{
		Time firstTime = getEarliestTime();
		Time lastTime = getLatestTime();
		
		if (firstTime != null)
		{
			for (int col = 0; col < 5; col++)
			{
				if (schedule.get(col).size() > 0)
				{
					// determine width:
					int colW = w / 6;
					int colX = x + col * (colW + colW / 4);
					// determine height
					double scaleFactor = (double) h / (double) (lastTime.getMinutesFromMidnight() - firstTime.getMinutesFromMidnight());
					int colY = y + (int) (scaleFactor * (schedule.get(col).get(0).startTime().getMinutesFromMidnight() - firstTime.getMinutesFromMidnight()));
					for (int b = 0; b < schedule.get(col).size(); b++)
					{
						int boxX = colX;
						int boxY = colY + (int) (scaleFactor * (schedule.get(col).get(b).startTime().getMinutesFromMidnight() - schedule.get(col).get(0).startTime().getMinutesFromMidnight()));
						int boxW = colW;
						int boxH = (int) (scaleFactor * (schedule.get(col).get(b).endTime().getMinutesFromMidnight() - schedule.get(col).get(b).startTime().getMinutesFromMidnight()));
						if (ptX >= boxX && ptX <= boxX + boxW)
						{
							if (ptY >= boxY && ptY <= boxY + boxH)
							{
								schedule.get(col).get(b).drawMenu(g, ptX, ptY, x + w, y + h);
							}
						}
						
					}
				}
			}
		}
	}
	
}
