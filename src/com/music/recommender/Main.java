package com.music.recommender;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;

public class Main extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JList list;
	private static JButton play;
	private static JButton stop;
	private Mp3 mp3;

	public Main(String[] data) {
		super(new BorderLayout());
		list = new JList(data);
		list.addListSelectionListener(new SelectionHandler());
		list.setSelectedIndex(0);
		JScrollPane jsp = new JScrollPane(list);
		this.add(jsp, BorderLayout.CENTER);

		play = new JButton("Play");
		play.setVerticalTextPosition(SwingConstants.CENTER);
		play.setHorizontalTextPosition(SwingConstants.LEADING);
		play.setActionCommand("play");
		play.addActionListener(this);

		stop = new JButton("Stop");
		stop.setVerticalTextPosition(SwingConstants.CENTER);
		stop.setHorizontalTextPosition(SwingConstants.LEADING);
		stop.setActionCommand("stop");
		stop.addActionListener(this);
	}

	private class SelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				final String fileName = list.getSelectedValue().toString();
			}
		}
	}

	public static void main(String[] args) {
		File dir = new File("music");
		final String[] children = dir.list();
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		container.add(new Main(children));
		container.add(play);
		container.add(stop);
		JFrame f = new JFrame("ListPanel");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(container);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("play".equals(e.getActionCommand())) {
			final String fileName = list.getSelectedValue().toString();
			if (mp3 != null) {
				mp3.close();
			}
			mp3 = new Mp3("music/" + fileName);
			mp3.play();
		}

		if ("stop".equals(e.getActionCommand())) {
			if (mp3 != null) {
				System.out.println("percentage played:" + mp3.close());
			}
		}
	}
}
