package xyz.chengzi.aeroplanechess.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DiceSelectorComponent extends JComponent implements ItemListener {
    private JRadioButton manualDiceRadio;
    private JRadioButton randomDiceRadio;
    private JComboBox<String> diceComboBox;
    private boolean randomDice = true;

    public DiceSelectorComponent() {
        setLayout(null); // Use absolute layout
        setSize(570, 50);
        diceComboBox = new JComboBox<>();
        /*for (int i = 1; i <= 6; i++) {
            diceComboBox.addItem("1");
        }*/
        diceComboBox.setBackground(new Color(225, 223, 231));
        diceComboBox.addItem("1");
        diceComboBox.addItem("2");
        diceComboBox.addItem("3");
        diceComboBox.addItem("4");
        diceComboBox.addItem("5");
        diceComboBox.addItem("6");

        diceComboBox.setLocation(280, 0);
        diceComboBox.setSize(100, 50);
        diceComboBox.setVisible(true);
        diceComboBox.setEnabled(false);
        add(diceComboBox);

        manualDiceRadio = new JRadioButton("Manual");
        manualDiceRadio.setBackground(new Color(203, 201, 209));
        randomDiceRadio = new JRadioButton("Auto", true);

        Font diceRadioFont = new Font("Consolas",Font.BOLD,22);
        randomDiceRadio.setLocation(0, 0);
        randomDiceRadio.setSize(130, 50);
        randomDiceRadio.setFont(diceRadioFont);
        randomDiceRadio.setBackground(new Color(203, 201, 209));
        randomDiceRadio.setFocusPainted(false);

        manualDiceRadio.setLocation(130, 0);
        manualDiceRadio.setSize(150, 50);
        manualDiceRadio.setFont(diceRadioFont);
        manualDiceRadio.setFocusPainted(false);

        manualDiceRadio.addItemListener(this);
        randomDiceRadio.addItemListener(this);
        add(manualDiceRadio);
        add(randomDiceRadio);
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(manualDiceRadio);
        btnGroup.add(randomDiceRadio);

    }

    public boolean isRandomDice() {
        return randomDice;
    }

    public Object getSelectedDice() {
        return diceComboBox.getSelectedItem();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (randomDiceRadio.isSelected()) {
            randomDice = true;
            diceComboBox.setEnabled(false);
        } else {
            randomDice = false;
            diceComboBox.setEnabled(true);
        }
    }
}
