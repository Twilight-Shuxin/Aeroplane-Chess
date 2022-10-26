package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuFrame extends JFrame {
	private GameController controller;

	public MenuFrame(String title, JFrame back, GameController controller) {
		super(title);
		back.setEnabled(false);
		MenuFrame This = this;
	//	This.setUndecorated(true);
		//This.setLayout(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				/*int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示",
						JOptionPane.YES_NO_OPTION);
				if (a == 0) {
					back.setEnabled(true);
					This.dispose();//关闭
				}*/
				back.setEnabled(true);
				This.dispose();//关闭
			}
		});

		JPanel panel = new JPanel(new GridLayout(4,1));
		this.setResizable(false);
		ImageIcon image = new ImageIcon("src/Pic2/MenuBackground2.jpg");
		JLabel label=new JLabel(image);
		label.setBounds(0,0,550,103);
		label.setLocation(0, 0);
		panel.add(label);

		Font btnFont = new Font("Georgia", Font.BOLD,25);
		JButton Savebtn = new JButton();//("Save");
		Savebtn.setFont(btnFont);
		Savebtn.setOpaque(false);
		Savebtn.setContentAreaFilled(false);
		Savebtn.setBorder(null);
		Savebtn.setMargin(new Insets(0, 0, 0, 0));
		Savebtn.setIcon(new ImageIcon("src/Pic2/saveBtn0.png"));
		Savebtn.setRolloverIcon(new ImageIcon("src/Pic2/saveBtn1.png"));
		//Savebtn.setText("Save");
		Savebtn.addActionListener((e) -> {
			if(controller.endGame1 == 1) {
				JOptionPane.showMessageDialog(this, "Game has Finished!");
			}
			else {
				back.setEnabled(true);
				This.dispose();
				ShowSaveWindow();
			}
		});

		JButton Loadbtn = new JButton();
		Loadbtn.setOpaque(false);
		Loadbtn.setContentAreaFilled(false);
		Loadbtn.setBorder(null);
		Loadbtn.setMargin(new Insets(0, 0, 0, 0));
		Loadbtn.setIcon(new ImageIcon("src/Pic2/loadBtn0.png"));
		Loadbtn.setRolloverIcon(new ImageIcon("src/Pic2/loadBtn1.png"));
		Loadbtn.addActionListener((e) -> {
			back.setEnabled(true);
			This.dispose();
			ShowChooseWindow();
			/*GameController lastController = null;
			try {
				ShowChooseWindow();
				This.dispose();
				lastController = GameIO.Load();
			} catch (FileNotFoundException fileNotFoundException) {
				fileNotFoundException.printStackTrace();
			}
			//if(lastController == null)
			StartingFrame.mainFrame = new GameFrame(lastController);*/
		});

		JButton Restartbtn = new JButton();
		Restartbtn.setOpaque(false);
		Restartbtn.setContentAreaFilled(false);
		Restartbtn.setBorder(null);
		Restartbtn.setMargin(new Insets(0, 0, 0, 0));
		Restartbtn.setIcon(new ImageIcon("src/Pic2/restartBtn0.png"));
		Restartbtn.setRolloverIcon(new ImageIcon("src/Pic2/restartBtn1.png"));
		Restartbtn.addActionListener((e) -> {
			back.setEnabled(true);
			This.dispose();
			ShowPlayerChooseWindow();
		});

		panel.add(Savebtn);
		panel.add(Loadbtn);
		panel.add(Restartbtn);
		this.add(panel);
	}

	void ShowChooseWindow() {
		ChooseFrame chooseWindow = new ChooseFrame("Load Game");
		chooseWindow.setLocationRelativeTo(StartingFrame.mainFrame);
	}

	void ShowSaveWindow() {
		SaveFrame saveWindow = new SaveFrame("Save Game");
		saveWindow.setLocationRelativeTo(StartingFrame.mainFrame);
	}

	void ShowPlayerChooseWindow() {
		PlayerChooseWindow playerChooseWindow = new PlayerChooseWindow("Choose Player");
	}
}
