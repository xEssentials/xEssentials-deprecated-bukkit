package tv.mineinthebox.simpleserver.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import tv.mineinthebox.simpleserver.SimpleServer;
import tv.mineinthebox.simpleserver.example.events.AboutListener;
import tv.mineinthebox.simpleserver.example.events.ContactListener;
import tv.mineinthebox.simpleserver.example.events.IndexListener;

public class Main {

	public static String getResource(String name) {
		return Main.class.getResource("resources/"+name).getFile();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame("server test");
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setSize(220, 80);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
				frame.setVisible(true);
				JButton button = new JButton("start");
				button.addActionListener(new SwingEvent());
				frame.add(button);
			}
			
		});
	}
}

class SwingEvent implements ActionListener {

	private final SimpleServer server = new SimpleServer(8080, "simple-server");
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			if(button.getText().equalsIgnoreCase("start")) {
				try {
					server.startServer();
					server.addListener(new IndexListener());
					server.addListener(new AboutListener());
					server.addListener(new ContactListener());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				button.setText("stop");
			} else if(button.getText().equalsIgnoreCase("stop")) {
				try {
					server.stopServer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				button.setText("start");
			}
		}
	}
}
