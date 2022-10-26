package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ChessBoardMouseListener implements MouseMotionListener {
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	boolean mark = false, mark2 = false;
	ChessComponent chessComponent;
	SquareComponent squareComponent;

	@Override
	public void mouseMoved(MouseEvent e) {
		GameController controller = StartingFrame.mainFrame.controller;
		ChessBoardComponent CBC = controller.getView();
		JComponent component = (JComponent) CBC.getComponentAt(e.getX(), e.getY());
		ShadeComponent shadeComponent = StartingFrame.mainFrame.shadeComponent;
		if (component instanceof SquareComponent) {
			SquareComponent square = (SquareComponent) component;
			ChessBoardLocation location = new ChessBoardLocation(square.getPlayer(), square.getIndex());
			int t = controller.cal(0);
			//System.out.println("??")
			if(component.getComponentCount() != 0) {
				if(chessComponent != null) {
					chessComponent.entered = 0; mark = false;
					chessComponent.repaint();
				}
				if(squareComponent != null) {
					shadeComponent.state = 0; mark2 = false;
					shadeComponent.repaint();
				}
				chessComponent = (ChessComponent) square.getComponent(0);
				chessComponent.entered = 1; mark = true;
				chessComponent.repaint();
				if(t != -1 && ((location.getIndex() > 18 && location.getIndex() != 23) == false) && chessComponent.player == controller.currentPlayer) {
					ChessBoardLocation CBL = controller.getModel().checkChessPiecePVE(location, controller.totalNumber, controller.currentPlayer);
					shadeComponent.setLocation(CBC.placeX[CBL.getColor()][CBL.getIndex()], CBC.placeY[CBL.getColor()][CBL.getIndex()]);
					shadeComponent.state = 1; mark2 = true;
					shadeComponent.repaint();
				}
			}
			else {
				if (mark) {
					chessComponent.entered = 0;
					mark = false;
					chessComponent.repaint();
				}
				if (mark2) {
					//squareComponent.step = 0; mark2 = false;
					//	squareComponent.repaint();
					shadeComponent.state = 0;
					mark2 = false;
					shadeComponent.repaint();
				}
			}
		}
		else {
			if (mark) {
				chessComponent.entered = 0;
				mark = false;
				chessComponent.repaint();
			}
			if (mark2) {
				shadeComponent.state = 0;
				mark2 = false;
				shadeComponent.repaint();
			}
		}
	}
}
