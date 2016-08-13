import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Viewer extends JFrame
{

		private static final long serialVersionUID = 1L;
		
		// DATA:
		private static int FRAME_WIDTH = 640;
		private static int FRAME_HEIGHT = 480;
		private static final int TOP_OF_WINDOW = 22 + 15;	// Top of the visible window + Amount of space needed for name of day
		private static final int DELAY_IN_MILLISEC = 1000 * 60;	// Time delay between screen updates

		private Schedule schedule;
		private Pointer pointer;
		private int clickX = 0;
		private int clickY = 0;
		
		class MouseActionListener implements MouseListener, MouseMotionListener, KeyListener, WindowStateListener, WindowListener
		{  
			public void mousePressed(MouseEvent event) {}
			public void mouseClicked(MouseEvent e)
			{
				clickX = e.getX();
				clickY = e.getY();
				pointer.update();
				repaint();
			}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {}
			public void windowStateChanged(WindowEvent e)
			{
				if (convertStateToString(e.getNewState()).equals("NORMAL"))
				{
					FRAME_WIDTH = 640;
					FRAME_HEIGHT = 480;
					clickX = -1;
					clickY = -1;
					repaint();
				}
				else if (convertStateToString(e.getNewState()).equals("MAXIMIZED_BOTH"))
				{
					FRAME_WIDTH = 1280;
					FRAME_HEIGHT = 728;
					clickX = -1;
					clickY = -1;
					repaint();
				}
			}
			
			/*
			 * BORROWED CODE--BEN DONT TOUCH!
			 */
			private String convertStateToString(int state) {
		        if (state == Frame.NORMAL) {
		            return "NORMAL";
		        }
		        String strState = " ";
		        if ((state & Frame.ICONIFIED) != 0) {
		            strState += "ICONIFIED";
		        }
		        //MAXIMIZED_BOTH is a concatenation of two bits, so
		        //we need to test for an exact match.
		        if ((state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
		            strState += "MAXIMIZED_BOTH";
		        } else {
		            if ((state & Frame.MAXIMIZED_VERT) != 0) {
		                strState += "MAXIMIZED_VERT";
		            }
		            if ((state & Frame.MAXIMIZED_HORIZ) != 0) {
		                strState += "MAXIMIZED_HORIZ";
		            }
		            if (" ".equals(strState)){
		                strState = "UNKNOWN";
		            }
		        }
		        return strState.trim();
		    }
			public void windowOpened(WindowEvent e)
			{
				pointer.update();
				repaint();
			}
			public void windowClosing(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e)
			{
				pointer.update();
				repaint();
			}
			public void windowDeactivated(WindowEvent e) {}
		}
		
		class ActionOccurListener implements ActionListener 
		{   
			public void actionPerformed(ActionEvent ev) 
			{
				pointer.update();
				repaint();
			}
		}
		
		
		public Viewer() 
		{
			this.addKeyListener(new MouseActionListener());
			this.addMouseListener(new MouseActionListener());
			this.addWindowStateListener(new MouseActionListener());

			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			
			ActionListener actionListener = new ActionOccurListener();
			Timer clock = new Timer(DELAY_IN_MILLISEC, actionListener);
			// Now actually start the timer.
			clock.start();
			
			// create schedule
			ArrayList<String[]> classData = Reader.readClasses();
			ArrayList<Class> classes = new ArrayList<Class>();
			for (String[] classDatum : classData)
			{
				if (classDatum.length == 2)
				{
					classes.add(new Class(classDatum[0], Color.decode(classDatum[1])));
				}
				else if (classDatum.length == 3)
				{
					try
					{
						classes.add(new Class(classDatum[0], Color.decode(classDatum[1]), Integer.parseInt(classDatum[2])));
					}
					catch (NumberFormatException e)
					{
						classes.add(new Class(classDatum[0], Color.decode(classDatum[1]), classDatum[2]));
					}
				}
				else if (classDatum.length == 4)
				{
					try
					{
						classes.add(new Class(classDatum[0], Color.decode(classDatum[1]), classDatum[2], Integer.parseInt(classDatum[3])));
					}
					catch (NumberFormatException e)
					{
						classes.add(new Class(classDatum[0], Color.decode(classDatum[1]), classDatum[3], Integer.parseInt(classDatum[2])));
					}
				}
			}
			
			schedule = new Schedule();
			
			ArrayList<String[]> blockData = Reader.readBlocks();
			for (String[] blockDatum : blockData) // add each block to the schedule
			{
				schedule.addBlock(new Block(classes.get(Integer.parseInt(blockDatum[0]) - 1), new Time(Integer.parseInt(blockDatum[1]), Integer.parseInt(blockDatum[2])), new Time(Integer.parseInt(blockDatum[3]), Integer.parseInt(blockDatum[4]))), Integer.parseInt(blockDatum[5]));
			}
			
			pointer = new Pointer();
			
		}
		
		public static void main(String[] args) 
		{			
			JFrame frame = new Viewer();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setResizable(false); // Until I can figure out how to only disable window drag, resizing will be locked.
		}
		
		@Override
		public void update(Graphics g)
		{
			paint(g);
		}
		
		public void paint(Graphics g) 
		{
			// Clear the window.
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			schedule.paint(g, 10, TOP_OF_WINDOW + 10, FRAME_WIDTH - 20, (FRAME_HEIGHT - TOP_OF_WINDOW) - 20);
			pointer.paint(g, 10, TOP_OF_WINDOW + 10, FRAME_WIDTH - 20, (FRAME_HEIGHT - TOP_OF_WINDOW) - 20, schedule.getEarliestTime(), schedule.getLatestTime(), schedule.getEarliestTime(pointer.getDay()), schedule.getLatestTime(pointer.getDay()));
			schedule.drawMenu(g, clickX, clickY, 10, TOP_OF_WINDOW + 10, FRAME_WIDTH - 20, FRAME_HEIGHT - TOP_OF_WINDOW - 20);
		}
		
		
		/**
		 * Draws a centered string on Graphics. This method came from stack overflow and is by Daniel Kvist.
		 * @param g
		 * @param text
		 * @param rect
		 * @param font
		 */
		public static void drawCenteredString(Graphics g, String text, Rectangle rect)
		{
			JEditorPane outputArea = new JEditorPane();
			Font font = outputArea.getFont();
			FontMetrics metrics = g.getFontMetrics(font);
		    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		    g.setFont(font);
		    g.drawString(text, x, y);
		}
}
