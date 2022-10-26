/*private int airPortGridLocation(int player, int index) {
        int beforeLocation = gridLocation(player, 0);
        int x = beforeLocation >> 16, y = beforeLocation & 0xffff;
        if(player == 0) {
        y += (index + 1) * gridSize;
        }
        else if(player == 1) {
        x += (index + 1) * gridSize;
        }
        else if(player == 2) {
        y -= (index + 1) * gridSize;
        }
        else {
        x -= (index + 1) * gridSize;
        }
        return x << 16 | y;
        }

        for(int player = 0; player < 4; player ++) {
        for(int index = dimension + endDimension; index <= dimension + endDimension + 4; index ++) {
        int gridLocation = airPortGridLocation(player, index - dimension - endDimension);
        gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
        gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
        add(gridComponents[player][index]);
        }
        }*/