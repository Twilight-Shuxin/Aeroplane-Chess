package xyz.chengzi.aeroplanechess.view;

import javazoom.jl.decoder.JavaLayerException;
import xyz.chengzi.aeroplanechess.DiceRolling;
import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.PVE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class Working implements ActionListener {
	public int gameState = 0;
	public GameController controller;
	public DiceRolling Dice;
	public  Timer timer = new Timer(1700, this);
	private int player;
	private PVE.PVEOut pveOut;
	public Working(GameController controller, DiceRolling Dice, int player) {
		this.controller = controller;
		this.Dice = Dice;
		this.player = player;
	}

	public PVE.PVEOut getPveOut(){
		return pveOut;
	}

	public void Work2() {
		StartingFrame.mainFrame.setEnabled(false);
		if(controller.endGame1 == 0) {
			controller.rollDice1();
			controller.rollDice2();
			System.out.println("goin pve");
			PVE pve = new PVE(player, controller.getModel(), controller.rolledNumber1, controller.rolledNumber2);
			pve.calculateValue();
			this.pveOut = pve.compareValue();
			System.out.println("gooutPve");
			Dice.state = 1;
			gameState = 0;
			timer.start(); // start timing
		}
	}


	/*public void actionPerformed(ActionEvent e) {
		if(controller.realPlayer == 1) {
			if(gameState == 0) {
				//	StartingFrame.mainFrame.setEnabled(false);
				gameState ++; // just for timing control
			}
			else if(gameState == 1) {
				ChessBoard CB = controller.getModel(); // chess board
				int t = RandomUtil.nextInt(1, 3); //+ - *
				if(t == 1) controller.totalNumber = controller.rolledNumber1 + controller.rolledNumber2;
				else if(t == 2) controller.totalNumber = controller.rolledNumber1 * controller.rolledNumber2;
				else if(t == 3) {
					if(controller.rolledNumber1 > controller.rolledNumber2)
						controller.totalNumber = controller.rolledNumber1 - controller.rolledNumber2;
					else if(controller.rolledNumber1 < controller.rolledNumber2)
						controller.totalNumber = controller.rolledNumber2 - controller.rolledNumber1;
					else controller.totalNumber = controller.rolledNumber1 + controller.rolledNumber2;
				}
				int done = 0; int cnt = 0; // count chess pieces
				for(int i = 0; i < 4; i ++) {
					if(done == 1) break; // done: moved or not
					for (int j = 23; j >= 0; j--) { //find places where there is a chess of my current player
						ChessPiece piece = CB.grid[i][j].getPiece();
						if (piece == null) continue;
						if (piece.state == -1) { // returned plane
							cnt ++;
							continue;
						}
						if (piece.getPlayer() != controller.currentPlayer) continue;
						cnt += piece.state;
						if (j > 18 && j != 23 && (controller.rolledNumber1 == 6 || controller.rolledNumber2 == 6)) {
							ChessBoardLocation CBL = new ChessBoardLocation(i, j);
							ChessComponent CP = controller.getView().Map[i][j];
							try {
								controller.onPlayerClickChessPiece(CBL, CP, false);
							} catch (FileNotFoundException fileNotFoundException) {
								fileNotFoundException.printStackTrace();
							} catch (JavaLayerException javaLayerException) {
								javaLayerException.printStackTrace();
							}
							done = 1;
							break;
						}
						else if (j > 18 && j != 23 && cnt == 4) { //if all chess has been checked, click this piece no matter what
							ChessBoardLocation CBL = new ChessBoardLocation(i, j);
							ChessComponent CP = controller.getView().Map[i][j]; //Map[i][j]: the chess component on place i, j
							try {
								controller.onPlayerClickChessPiece(CBL, CP, false);
							} catch (FileNotFoundException fileNotFoundException) {
								fileNotFoundException.printStackTrace();
							} catch (JavaLayerException javaLayerException) {
								javaLayerException.printStackTrace();
							}
							done = 1;
							break;
						}
						else if(j <= 18 || j == 23) { // must be able to move, just move this
							ChessBoardLocation CBL = new ChessBoardLocation(i, j);
							ChessComponent CP = controller.getView().Map[i][j];
							try {
								controller.onPlayerClickChessPiece(CBL, CP, false);
							} catch (FileNotFoundException fileNotFoundException) {
								fileNotFoundException.printStackTrace();
							} catch (JavaLayerException javaLayerException) {
								javaLayerException.printStackTrace();
							}
							done = 1;
						}
					}
				}
				gameState ++;
				if(controller.currentPlayer == 0) {
					StartingFrame.mainFrame.setEnabled(true);
					//stop going to the next automatic player
				}
				else {
					if(controller.endGame1 != 1 && controller.currentPlayer != 0 && controller.realPlayer == 1) {
						Working work = new Working(controller, StartingFrame.mainFrame.Dice, controller.currentPlayer);
						work.Work2();
						// next automatic player
					}
				}
				timer.stop(); // stop timing
			}
		}
	}*/

	public void actionPerformed(ActionEvent e) {
		if(controller.realPlayer == 1) {
			if(gameState == 0) {
				//StartingFrame.mainFrame.setEnabled(false);
				gameState ++; // just for timing control
			}
			else if(gameState == 1) {
				ChessBoard CB = controller.getModel(); // chess board
				System.out.println("+++++++???");
				if(getPveOut().getLocation() != null) {
					controller.totalNumber = getPveOut().getSteps();
					gameState++;
					ChessComponent CP = controller.getView().Map[pveOut.getLocation().getColor()][pveOut.getLocation().getIndex()];
					try {
						System.out.println("gogogo" + controller.totalNumber + " " + pveOut.getLocation().getColor() + " " + pveOut.getLocation().getIndex());
						controller.onPlayerClickChessPiece(pveOut.getLocation(), CP, true);
					} catch (FileNotFoundException fileNotFoundException) {
						fileNotFoundException.printStackTrace();
					} catch (JavaLayerException javaLayerException) {
						javaLayerException.printStackTrace();
					}
				}
				else {
					System.out.println("this or not");
					controller.endRound();
				}
				if(controller.currentPlayer == 0) {
					StartingFrame.mainFrame.setEnabled(true);
					//stop going to the next automatic player
				}
				else {
					if(controller.endGame1 != 1 && controller.currentPlayer != 0 && controller.realPlayer == 1) {
						Working work = new Working(controller, StartingFrame.mainFrame.Dice, controller.currentPlayer);
						work.Work2();
						// next automatic player
					}
				}
				timer.stop(); // stop timing
			}
		}
	}




}


