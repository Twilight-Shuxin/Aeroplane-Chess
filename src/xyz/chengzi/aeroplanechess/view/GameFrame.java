package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.DiceRolling;
import xyz.chengzi.aeroplanechess.Winner.MyListener;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements GameStateListener {
    public static final String[] PLAYER_NAMES = {"Nano", "Qwerty", "Neptune the First", "Rocket ChangZheng"};
    public final JLabel statusLabel = new JLabel();
    public GameController controller;
    public DiceSelectorComponent diceSelectorComponent;
    public JRadioButton[] Op = new JRadioButton[4];
    public DiceRolling Dice = new DiceRolling(this);
    public int gameState = 0;
    public StarComponent starComponent;
    public ShadeComponent shadeComponent;

    public GameFrame(GameController controller) {
        this.controller = controller;
        controller.registerListener(this);
        MyListener endGameListener = new MyListener(this);
        controller.endGame.addPropertyChangeListener(endGameListener);
        setTitle("Aeroplane Chess Version 1");
        setSize(1500, 900);//软件界面的size
        this.setResizable(false);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);//点×关闭
        setLayout(null);
        shadeComponent = new ShadeComponent();
        add(shadeComponent);

        statusLabel.setLocation(1000, 210);
        Font labelFont = new Font("Consolas",Font.BOLD,22);
        statusLabel.setFont(labelFont);
        statusLabel.setSize(700, 120);
        add(statusLabel);

        Dice.setLocation(1050, 595);
        add(Dice);

        diceSelectorComponent = new DiceSelectorComponent();
        diceSelectorComponent.setLocation(950, 495);
        add(diceSelectorComponent);

        starComponent = new StarComponent();
        starComponent.setLocation(395, 358);
        add(starComponent);

        JLabel[][] arrow = new JLabel[5][2];
       // for(int i = 0; i < 4; i ++) {
        for(int i = 1; i <= 4; i ++) {
            ImageIcon arrowPic = new ImageIcon("src/Pic2/arrow" + i + ".png");
            arrow[i][0] = new JLabel(arrowPic);
            arrow[i][1] = new JLabel(arrowPic);
        }

        arrow[1][0].setBounds(630, 265, 55, 110);
        arrow[1][1].setBounds(630, 428, 55, 110);

        arrow[2][0].setBounds(355, 588, 110, 55);
        arrow[2][1].setBounds(518, 588, 110, 55);

        arrow[3][0].setBounds(250, 315, 55, 110);
        arrow[3][1].setBounds(250, 478, 55, 110);

        arrow[4][0].setBounds(305, 208, 110, 55);
        arrow[4][1].setBounds(468, 208, 110, 55);

        for(int i = 1; i <= 4; i ++)
            for(int j = 0; j <= 1; j ++)
                add(arrow[i][j]);

        JButton btn = new JButton();
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setLocation(950, 105);
        btn.setBorder(null);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setIcon(new ImageIcon("src/Pic2/Menu1.png"));
        btn.setRolloverIcon(new ImageIcon("src/Pic2/Menu2.png"));
        btn.setPressedIcon(new ImageIcon("src/Pic2/Menu3.png"));
        btn.setSize(380, 73);
        btn.setFocusPainted(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNewWindow(controller);
            }
        });
        add(btn);


        JButton button = new JButton();
        button.addActionListener((e) -> {
            int player = controller.getCurrentPlayer();
            Work(player);
        });

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setIcon(new ImageIcon("src/Pic2/RollDice1.png"));
        button.setRolloverIcon(new ImageIcon("src/Pic2/RollDice2.png"));
        button.setPressedIcon(new ImageIcon("src/Pic2/RollDice3.png"));

        button.setLocation(950, 380);
        button.setFocusPainted(false);
       /* Font rollFont=new Font("Georgia",Font.BOLD,28);
        button.setFont(rollFont);
        */
        button.setSize(300, 70);
        add(button);

        JRadioButton musicButton = new JRadioButton();
        musicButton.setOpaque(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setBorder(null);
        musicButton.setMargin(new Insets(0, 0, 0, 0));

        musicButton.setIcon(new ImageIcon("src/Pic2/music0.png"));
        musicButton.setSelectedIcon(new ImageIcon("src/Pic2/music1.png"));
        musicButton.setRolloverIcon(new ImageIcon("src/Pic2/music2.png"));
        musicButton.setSize(40, 40);
        musicButton.setLocation(1420, 20);
        musicButton.setFocusPainted(false);
        add(musicButton);

        musicButton.addActionListener(e -> {
            MusicComponent.musicPlay.playMusic();
        });

        ButtonGroup btnGroup = new ButtonGroup();
        for(int i = 0; i < 4; i ++) {
            Op[i] = new JRadioButton();
            Op[i].setOpaque(false);
            Op[i].setContentAreaFilled(false);
            Op[i].setBorder(null);
            Op[i].setMargin(new Insets(0, 0, 0, 0));
            //ImageIcon image = new ImageIcon("src/pic/B" + (i + 1) + "0.png");//+-*/的图
            ImageIcon image = new ImageIcon("src/pic2/btn" + i + "0.png");//+-*/的图
            Op[i].setIcon(image);
            ImageIcon image2 = new ImageIcon("src/pic2/btn" + i + "2.png");//粉色
            Op[i].setSelectedIcon(image2);
            ImageIcon image3 = new ImageIcon("src/pic2/btn" + i + "1.png");//紫色
            Op[i].setRolloverIcon(image3);
            Op[i].setSize(30, 30);
            Op[i].setLocation((i % 2) * 40 + 1250 + 10, ((i >= 2) ? 40 : 0) + 380);
            btnGroup.add(Op[i]);
            Op[i].setFocusPainted(false);
            add(Op[i]);
        }
        Op[0].setSelected(true);
    }



    void Work(int player) {
        if(controller.endGame1 == 0) {
            if(controller.getNumber1() != -1 && controller.outPlane[player] == 0) {
                if(controller.realPlayer == 0) {
                    int finalPlayer = player;
                    controller.listenerList.forEach(listener -> listener.onPlayerEndRound(finalPlayer));
                    controller.nextPlayer();
                    player = controller.getCurrentPlayer();
                    int finalPlayer1 = player;
                    controller.listenerList.forEach(listener -> listener.onPlayerStartRound(finalPlayer1));
                }
                else controller.endRound();

            }
            else if (diceSelectorComponent.isRandomDice()) {
                int dice1 = controller.rollDice1();
                int dice2 = controller.rollDice2();
                //System.out.println("NOOOOO");
                if (dice1 != -1 && dice2 != -1) {//两个自动骰子
                    Dice.state = 1;
                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            } else {
                int dice = controller.getNumber1();//手动
                if (dice == -1) {
                    //JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
                    int x;
                    if (diceSelectorComponent.getSelectedDice() == "1") x = 1;
                    else if (diceSelectorComponent.getSelectedDice() == "2") x = 2;
                    else if (diceSelectorComponent.getSelectedDice() == "3") x = 3;
                    else if (diceSelectorComponent.getSelectedDice() == "4") x = 4;
                    else if (diceSelectorComponent.getSelectedDice() == "5") x = 5;
                    else x = 6;
                    controller.manualRollDice(x);
                    Dice.state = 1;
                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            }
        }
    }



    @Override
    public void onPlayerStartRound(int player) {
        if(controller.endGame1 == 1) return;
        controller.status = String.format("<html>[%s] : <br> Please roll the dice</html>", PLAYER_NAMES[player]);
        statusLabel.setText(String.format("<html>[%s] : <br> Please roll the dice</html>", PLAYER_NAMES[player]));
    }

    @Override
    public void onPlayerEndRound(int player) {

    }

    public void showNewWindow(GameController controller) {
        JFrame back = this;
        MenuFrame newJFrame = new MenuFrame("Menu", back, controller);

        newJFrame.setSize(550, 448);
        newJFrame.setLocationRelativeTo(this);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");  // 图片的具体位置
        newJFrame.setIconImage(icon);

        newJFrame.setVisible(true);
    }
}
