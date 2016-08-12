import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Read the supplied files and return a list of the clubs with all of their members.
 * @author benjamin
 *
 */
public class Reader
{
	public static ArrayList<String[]> readBlocks()
	{
		Scanner in = null;
		try
		{
			in = new Scanner(new File(System.getProperty("user.dir") + "/bin/blocks.txt"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: blocks.txt was not found.");
		}
		
		ArrayList<String[]> data = new ArrayList<String[]>();
		while (in.hasNextLine())
		{
			String nextLine = in.nextLine();
			if (!nextLine.equals(new String()))
			{
				String[] lineData = nextLine.split(",");
				for (int i = 0; i < lineData.length; i++)
				{
					lineData[i] = stripSpaces(lineData[i]);
				}
				data.add(lineData);
			}
		}
		return data;
	}
	
	public static ArrayList<String[]> readClasses()
	{
		Scanner in = null;
		try
		{
			in = new Scanner(new File(System.getProperty("user.dir") + "/bin/classes.txt"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error: classes.txt was not found.");
		}
		
		ArrayList<String[]> data = new ArrayList<String[]>();
		while (in.hasNextLine())
		{
			String nextLine = in.nextLine();
			if (!nextLine.equals(new String()))
			{
				String[] lineData = nextLine.split(",");
				for (int i = 0; i < lineData.length; i++)
				{
					lineData[i] = stripSpaces(lineData[i]);
				}
				data.add(lineData);
			}
		}
		return data;
	}
		
  
  
  /**
   * Strip the whitespace off of the beginning and end of a string.
   * @param input input string
   * @return stripped string
   */
	private static String stripSpaces(String input)
	{
		if (input.length() == 0) {
			return input;
		}
		while (input.substring(0, 1).equals(" ") || input.substring(0, 1).equals("\t"))
		{
			input = input.substring(1);
			if (input.length() == 0)
			{
				return input;
			}
		}
		while (input.substring(input.length() - 1).equals(" ") || input.substring(input.length() - 1).equals("\t"))
		{
			input = input.substring(0, input.length() - 1);
			if (input.length() == 0)
			{
				return input;
			}
		}
		return input;
	}
  
  
}

