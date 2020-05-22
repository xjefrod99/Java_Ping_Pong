// Structure nabbed from squash.java by Daniel Scharstein

import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;

public class Pong extends Applet implements ActionListener{

	private static final long serialVersionUID = 1L;
	Label mylabel;	      // the top part of the applet
	PongCanvas mycanvas;  // the main part of the applet

	// initializes the canvas
	public void init() {
		mylabel = new Label("Pong 2 Players                                "
				+ "CLICK TO PLAY");
		mylabel.setAlignment(Label.CENTER);
		mylabel.setFont(PongCanvas.Lato);
		mylabel.setBackground(Color.black);
		mylabel.setForeground(Color.white);

		mycanvas = new PongCanvas(this);
		setLayout(new BorderLayout());
		setSize(900, 600);
		add(mylabel, "North");
		add(mycanvas, "Center");
		add(southPanel(), "South");
		setVisible(true);
	}

	//creates bottom panel with button
	public Panel southPanel(){
		Panel p = new Panel();
		p.setBackground(Color.black);
		p.setLayout(new FlowLayout());
		Button r = new Button("Reset");
		r.addActionListener(this);
		p.add(r);
		return p;
	}

	// handles button's being pressed
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof Button){
			String name = ((Button)e.getSource()).getLabel();
			if(name.equals("Reset")) {
				mycanvas.restart();
			}
		}
	}

	// start the timer that is responsible for the animation
	public void start() {
		mycanvas.start();
	}

	// stops the timer
	public void stop() {
		mycanvas.stop();
	}
}