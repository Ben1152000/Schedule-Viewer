
@SuppressWarnings("rawtypes")
public class Time implements Comparable
{
	private int hour;
	private int minute;
	
	public Time(int h, int m)
	{
		hour = h;
		minute = m;
	}
	
	public int getHour()
	{
		return hour;
	}
	
	public int getMinute()
	{
		return minute;
	}
	
	public int getMinutesFromMidnight()
	{
		return hour * 60 + minute;
	}

	public int compareTo(Object other) {
		Time otherTime = (Time) other;
		return ((Integer) this.getMinutesFromMidnight()).compareTo(otherTime.getMinutesFromMidnight());
	}
	
	public String toString()
	{
		return (hour % 12 == 0? "12": hour % 12) + ":" + (minute < 10? "0": "") + minute + " " + (hour / 12 < 1? "AM": "PM");
	}
}
