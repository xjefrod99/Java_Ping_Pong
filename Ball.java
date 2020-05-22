//Ball.java
// Structure nabbed from squash.java by Daniel Scharstein

import java.awt.*;

public class Ball{
	int x;      // position x
	int y;	    // position y
	int dx;     // velocity x
	int dy;	    // velocity y
	int rad;	// radius of the ball
	int p1min;  // the top part of paddle 1
	int p1max;  // the bottom part of paddle 1
	int p2min;  // the top part of paddle 2
	int p2max;  // the bottom part of paddle 2
	int iSpeed; //initial speed

	// Constructor
	public Ball(){ 
		x = 450;
		y = 300;
		iSpeed = 3;
		dy = 4;
		rad = 10;

		//decides if the ball starts to the left or to the right
		if(Math.random()*2 > 1) {
			dx = -iSpeed;
		}
		else {
			dx = iSpeed;
		}
	}

	// moves the ball within the box
	// returns true if ball gets lost
	public boolean move(int width, int height, int p1, int p2) {
		x += dx;
		y += dy;

		p1min = p1 - 50;
		p1max = p1 + 50;

		p2min = p2 - 50;
		p2max = p2 + 50;

		//bottom and top walls
		if (y-rad < 0 || y+rad >= height) {
			dy = -dy;
			y += dy;
		}

		//makes it bounce if the center of ball is within the left paddle's position
		if((x + rad < 20 ) && (y+rad >= p1min && y+rad <= p1max)){
			int w = p1 % 10;
			dy += w;
			dx = 5;
			x += dx;
		}
		//makes it bounce if the center of ball is within the right paddle's position
		if((x + rad >= width) && (y+rad >= p2min && y+rad <= p2max)){
			int w = p1 % 10;
			dy += w;
			dx = -5;
			x += dx;
		}

		// left and right boundaries
		if (x-rad < 0 || x+rad >= width) {
			return true;
		}

		return false;
	}

	// draw the ball
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x - rad, y - rad, 2*rad, 2*rad);
		g.drawOval(x - rad, y - rad, 2*rad, 2*rad);
	}
}