public class Cell {

    private int x;
    private int y;
    private int size;
    private int row;
    private int column;

    // private int fill;
    private CellState cellState;
    private Rules rules;
    private boolean c;

    Cell(int _x, int _y, int _size, int _row, int _column, CellState _cellState, Rules _rules){
        x = _x;
        y = _y;
        size = _size;
        row = _row;
        column = _column;
        cellState = _cellState;
        rules = _rules;
        c = false;
    }

    public void display() {
        Main.app.rect(x, y, size, size);
        if(cellState == CellState.DEAD)
            Main.app.fill(255);
        else{
            Main.app.fill(0);
        }
    }

    public void handleMouseClicked() {

        c = !c;
        if(cellState == CellState.DEAD){
            cellState = CellState.ALIVE;
        }else if(cellState == CellState.ALIVE) {
            cellState = CellState.DEAD;
        }
    }

    public void evolve(){
        if (cellState == CellState.WILL_REVIVE){
            cellState = CellState.ALIVE;
        } else if (cellState == CellState.WILL_DIE){
            cellState = CellState.DEAD;
        }
    }

    private int countLiveNeighbors(Cell[][] cells){
        int neighbors = 0;
        for(int r = row - 1; r <= row + 1; r++){
            for(int c = column - 1; c <= column + 1; c++){
                if(cells[r][c].cellState == CellState.ALIVE || cells[r][c].cellState == CellState.WILL_DIE){
                    neighbors++;
                }
            }
        }
        if(cellState == CellState.ALIVE || cellState == CellState.WILL_DIE){
            neighbors--;
        }
        return neighbors;
    }

    public void applyRules(Cell[][] cells){
        int liveNeighbors = countLiveNeighbors(cells);
        cellState = rules.applyRules(cellState, liveNeighbors);
    }


}
