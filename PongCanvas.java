// PongCanvas.java
// Final Project CS201
// Jeff Rodriguez and Danzan Achit-Erdene
// Structure nabbed from squash.java by Daniel Scharstein

import java.awt.event.*;
import java.awt.*;

public class PongCanvas extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	Pong parent; 						// instance variable to be able to access applet components
	Ball pongBall;
	Graphics g2;						// a variable for the canvas
	Image offscreen;					
	Dimension offscreensize;
	Thread t;							// the main thread
	static int p1;						// y position for paddle 1
	static int p2;						// y position for paddle 2
	int paddleWidth;
	int paddleLength;
	int keyPressed;
	int paddleSpeed;
	int startingPosition;
	int scoreLeft;
	int scoreRight;
	public static Font Lato;			// the font

	// the constructor
	public PongCanvas(Pong s) {
		parent = s;
		pongBall = new Ball();
		addKeyListener(this);
		paddleWidth = 15;
		paddleLength = 100;
		startingPosition = 300;
		p1 = startingPosition;
		p2 = startingPosition;
		scoreLeft = 0;
		scoreRight = 0;
		paddleSpeed = 40;
		Lato = new Font("Lato", Font.BOLD, 20);
	}

	// creates a thread that moves the ball 
	public void run() {
		Thread currentThread = Thread.currentThread();
		while (currentThread == t) {
			Dimension d = getSize();
			boolean lost = pongBall.move(d.width, d.height, p1, p2);
			if(lost) {
				if(pongBall.dx > 0) {
					scoreLeft++;
				}
				else {
					scoreRight++;
				}
				reposition();
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {};
			repaint();
		}
	}

	// draws the main screen
	public synchronized void update(Graphics g) {
		Dimension d = getSize();
		if((offscreen == null) || (d.width != offscreensize.width) ||
				(d.height != offscreensize.height)) {
			offscreen = createImage(d.width, d.height);
			offscreensize = d;
			g2 = offscreen.getGraphics();
			g2.setFont(getFont());
		}

		g2.setColor(Color.red);
		g2.fillRect(0, 0, d.width, d.height);

		g2.setColor(Color.black);
		g2.fillRect(3, 3, d.width-6, d.height-6);

		g2.setColor(Color.white);
		g2.fillRect(0, p1-50, paddleWidth, paddleLength);

		g2.setColor(Color.white);
		g2.fillRect(d.width-paddleWidth, p2-50, paddleWidth, paddleLength);

		g2.setColor(Color.white);
		g2.setFont(Lato);
		g2.drawString(Integer.toString(scoreLeft), d.width/3, d.height/6);
		g2.drawString(Integer.toString(scoreRight), (d.width/3)*2, d.height/6);

		g2.setColor(Color.white);
		g2.drawLine(d.width/2, 3, d.width/2, d.height-3);
		g2.drawOval(d.width/2-20, d.height/2, 40, 40);

		pongBall.draw(g2);

		g.drawImage(offscreen, 0, 0, null);
	}

	// adds a new ball
	public void reposition(){
		pongBall = new Ball();
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) { }
	@Override
	public void keyReleased(KeyEvent e) { }
	@Override
	// 'W' and 'S' move p2 and TopArrow and BottomArrow 
	// move p1 only if both paddles are within boxed area
	public void keyPressed(KeyEvent e) { 
		Dimension d = getSize();
		int key = e.getKeyCode();
		if(key == 38 && p2 > paddleLength/2 + paddleWidth) 
			p2 -= paddleSpeed;
		if(key == 40 && p2 < d.height - paddleLength/2 )
			p2 += paddleSpeed;
		if(key == 87 && p1 > paddleLength/2 + paddleWidth)
			p1 -= paddleSpeed;
		if(key == 83 && p1 < d.height - paddleLength/2)
			p1 += paddleSpeed;
		repaint();
	}

	// starts the thread
	public void start() {
		t = new Thread(this);
		t.start();
	}

	// stops the thread
	public void stop() {
		t = null;
	}

	// gives the score to whichever player scored
	public int getScore(boolean left) {
		if(left)
			return scoreLeft;
		else
			return scoreRight;
	}

	// resets the canvas
	public void restart() {
		scoreLeft = 0;
		scoreRight = 0;
		p1 = startingPosition;
		p2 = startingPosition;
		reposition();
	}
}