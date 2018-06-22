package mediaplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener , ActionListener{
    
	private boolean play=false;
	private int score=0;
	private int totalBricks=21;
	private Timer timer;
	private int delay=8;
	private int playerx = 310;
	private int ballposx=120;
	private int ballposy =350;
	private int balldirx=-1;
	private int balldiry=-2;
	private MapGenerator map;
	
	public Gameplay()
	{   map=new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	    timer = new Timer(delay,this);
	    timer.start();
	    
	}
	public void paint(Graphics g)
	{//background
		g.setColor(Color.black);
	    g.fillRect(1,1,692,592);
	    
	    //drawing bricks
	    map.draw((Graphics2D)g);
	    
	    //borders
	    g.setColor(Color.yellow);
	    g.fillRect(0, 0, 3, 592);
	    g.fillRect(0, 0, 692, 3);
	    g.fillRect(691, 0, 3, 592);
	    
	    //score
	    g.setColor(Color.white);
	    g.setFont(new Font("serif",Font.BOLD,25));
	    g.drawString(""+score, 590, 30);
	    
	    //name
	    g.setColor(Color.white);
	    g.setFont(new Font("serif",Font.BOLD,25));
	    g.drawString("Game By :- Sanjay Anand", 190, 450);
	    
	    //paddle
	    g.setColor(Color.green);
	    g.fillRect(playerx, 550, 100,8);
	    
	    //ball
	    g.setColor(Color.yellow);
	    g.fillOval(ballposx, ballposy, 20,20);
	    
	    if(totalBricks<=0)
	    {
	    	play=false;
	    	balldirx=0;
	    	balldiry=0;
	    	g.setColor(Color.red);
	    	g.setFont(new Font("serif",Font.BOLD,30));
	    	g.drawString("You Won Score :- "+score, 190, 300);
	    	
	    	g.setFont(new Font("serif",Font.BOLD,20));
	    	g.drawString("Press Enter To Restart", 230, 350);
	    }
	    
	    if(ballposy > 570)
	    {
	    	play=false;
	    	balldirx=0;
	    	balldiry=0;
	    	g.setColor(Color.red);
	    	g.setFont(new Font("serif",Font.BOLD,30));
	    	g.drawString("Game Over , Score :- "+score,190, 300);
	    	
	    	g.setFont(new Font("serif",Font.BOLD,20));
	    	g.drawString("Press Enter To Restart", 230, 350);
	    } 
	    g.dispose();
	    
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			if(new Rectangle(ballposx,ballposy,20,20).intersects(new Rectangle(playerx,550,100,8)))
			{
				balldiry=-balldiry;
			}
			
		A:  for(int i=0;i< map.map.length;i++)
		    {
			 for(int j=0;j<map.map[0].length;j++)
				 {
				  if(map.map[i][j]>0)
				  {
					int brickx=j*map.brickwidth +80;
					int bricky=i*map.brickheight +50;
					int brickwidth=map.brickwidth;
					int brickheight=map.brickheight;
					
					Rectangle rect=new Rectangle(brickx,bricky,brickwidth,brickheight);
					Rectangle ballrect=new Rectangle(ballposx,ballposy,20,20);
					Rectangle brickrect=rect;
					if(ballrect.intersects(brickrect))
					{
						map.setBrickValue(0, i, j);
						totalBricks--;
						score+=5;
						
						if(ballposx + 19<= brickrect.x|| ballposx + 1>=brickrect.x+brickrect.width)
						{
							balldirx=-balldirx;
						}
						else
						{
							balldiry=-balldiry;
							
						}
						break A;
					}
					
				  }
				 }
		    }
					  
			ballposx+= balldirx;
			ballposy += balldiry;
			if(ballposx<0) {
				balldirx=-balldirx;
			}
			if(ballposy < 0) {
				balldiry=-balldiry;
			}
			if(ballposx > 670) {
				balldirx=-balldirx;
			}
		}
		repaint();
				
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(playerx >= 600)
			{
				playerx = 600;
			}else
			{
				moveRight();
			}
		}
		if(arg0.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(playerx < 10)
			{
				playerx = 10;
			}else
			{
				moveLeft();
			}			
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(!play)
			{
				play=true;
				ballposx=120;
				ballposy=350;
				balldirx=-1;
				balldiry=-2;
				playerx=310;
				score=0;
				totalBricks=21;
				map=new MapGenerator(3,7);
				repaint();
				
			}
		}
	}
	public void moveRight()
	{
		play=true;
		playerx+=20;
	}
	public void moveLeft()
	{
		play=true;
		playerx-=20;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
