package xyz.chengzi.aeroplanechess.model;

import xyz.chengzi.aeroplanechess.StartingFrame;

public class PVE {
    private int player;
    private ChessBoard model;
    private ChessBoardLocation[] locations = new ChessBoardLocation[4];
    private boolean canFlyOut = false;

    public Integer rolledNumber1;
    public Integer rolledNumber2;
    public int[] totalNumber = new int[4];
    public int[][] value = new int[4][4];//value是每种可能性的价值
    public int currentPlayer;

    public PVE(int player, ChessBoard model, int rolledNumber1, int rolledNumber2){
        this.player = player;
        this.model = model;
        this.rolledNumber1 = rolledNumber1;
        this.rolledNumber2 = rolledNumber2;
        this.currentPlayer = StartingFrame.mainFrame.controller.currentPlayer;
    }

    public int plusNumber(int num1, int num2){
        if(num2 == -1) return  num1;
        return num1 + num2;
    }
    public int minusNumber(int num1, int num2){
        if(num2 == -1) return  num1;
        return Math.abs(num1 - num2);
    }
    public int multiplyNumber(int num1, int num2){
        if(num2 == -1) return  num1;
        if(num1 * num2 > 12) return -1;
        else return num1 * num2;
    }
    public int divideNumber(int num1, int num2){
        if(num2 == -1) return  num1;
        if(num1 > num2){
            if(num1 % num2 == 0){
                return num1 / num2;
            }
            else return -1;
        }
        if(num1 <= num2){
            if(num2 % num1 == 0){
                return num2 / num1;
            }
            else return -1;
        }
        else return 1;
    }

    public void setTotalNumber(){
        totalNumber[0] = plusNumber(rolledNumber1, rolledNumber2);
        totalNumber[1] = minusNumber(rolledNumber1, rolledNumber2);
        totalNumber[2] = multiplyNumber(rolledNumber1, rolledNumber2);
        totalNumber[3] = divideNumber(rolledNumber1, rolledNumber2);
    }

    int count = 0;

    public void setLocations(){
        count = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 24; j++){
                ChessBoardLocation location = new ChessBoardLocation(i, j);
                ChessPiece piece = model.getChessPieceAt(location);
                if(piece != null && piece.getPlayer() == currentPlayer && piece.state > 0){
                    locations[count] = location;
                    count++;
                }
            }
        }
    }

    public void flyOut(){
        if(rolledNumber1 == 6 || rolledNumber2 == 6){
            canFlyOut = true;
        }
    }


    public void calculateValue(){
        setTotalNumber();
        setLocations();
        flyOut();
        for(int i = 0; i < count; i++){
            for(int j = 0; j < 4; j++){
                if(locations[i].getIndex() > 18 && locations[i].getIndex() != 23) {
                    if(canFlyOut) value[i][j] = 2000;
                    else value[i][j] = -1000;
                    continue;
                }
                if(totalNumber[j] <= 0) {
                    value[i][j] = -1000;
                    continue;
                }
                ChessBoardLocation dest = model.checkChessPiecePVE(locations[i], totalNumber[j], player);
                boolean isOnTopOfOthers = false, isOnTopOfOwn = false, hasArrived = false;
                int distance = totalNumber[j];
                if(dest.getColor() == currentPlayer && dest.getIndex() == 4){//捷径飞行
                    distance += 12;
                }
                else if(dest.getColor() == currentPlayer && dest.getIndex() < 12){//同色跳一下
                    distance += 4;
                }
                if(model.grid[dest.getColor()][dest.getIndex()].getPiece() != null) {//块上有棋子
                    ChessPiece formerPiece = model.grid[dest.getColor()][dest.getIndex()].getPiece();
                    if(formerPiece.player != currentPlayer) isOnTopOfOthers = true;
                    else isOnTopOfOwn = true;
                }
                if(dest.getIndex() == 18) {//到终点
                    hasArrived = true;
                }
                if(hasArrived) value[i][j] = 4000;
                if(isOnTopOfOwn) value[i][j] = 800;
                if(isOnTopOfOthers) value[i][j] = 700;
                value[i][j] += distance;
            }
        }
    }

    /*
    if(dest.getColor() == currentPlayer && dest.getIndex() == 4){//捷径飞行
            distance += 12;
        }
        if(dest.getColor() == currentPlayer && dest.getIndex() < 12){//同色跳一下
            distance += 4;
        }
        if(grid[dest.getColor()][dest.getIndex()].getPiece() != null) {//块上有棋子
            ChessPiece formerPiece = grid[dest.getColor()][dest.getIndex()].getPiece();
            if(formerPiece.player != currentPlayer) {
                isOnTopOfOthers = true;
            }
            else {
                isOnTopOfOwn = true;
            }
        }
        if(dest.getIndex() == 18) {//到终点
            hasArrived = true;
        }
     */



    public class PVEOut{
        private int steps;
        private ChessBoardLocation location;
        public PVEOut( ChessBoardLocation location, int steps){
            this.steps = steps;
            this.location = location;
        }

        public int getSteps(){
            return steps;
        }

        public ChessBoardLocation getLocation(){
            return location;
        }
    }

    public PVEOut compareValue(){
        int max = value[0][0];
        ChessBoardLocation location = locations[0];
        int steps = totalNumber[0];
        for(int i = 0; i < count; i++) {
            for (int j = 0; j < 4; j++) {
                if (value[i][j] > max) {
                    max = value[i][j];
                    location = locations[i];
                    steps = totalNumber[j];
                }
            }
        }
        System.out.println(max + "&&&" + location.getColor() + " " + location.getIndex() + " " + currentPlayer);
        PVEOut pveOut = new PVEOut(location, steps);
    //    System.out.println("steps: " + steps);
        if(max < 0) pveOut = new PVEOut(null, 0);
        return pveOut;
    }
}
