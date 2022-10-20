import processing.core.PApplet;

public class Main extends PApplet {

    final int NUM_ROWS = 50;
    final int NUM_COLUMNS = 100;
    final int CELL_SIZE = 10;

    boolean doEvolve;
    Cell[][] cells;

    public static PApplet app;

    public Main() {
        super();
        doEvolve = false;
        app = this;
    }

    public void settings(){
        int dim1 = NUM_COLUMNS * CELL_SIZE;
        int dim2 = NUM_ROWS * CELL_SIZE;
        size(dim1, dim2);
    }

    public void setup(){
        cells = new Cell[NUM_ROWS][NUM_COLUMNS];
        frameRate(4);
        int[] birth = {3};
        int[] survival = {2, 3};
        Rules mR = new MooreRules(birth, survival);
        for(int row = 0; row < cells.length; row++){
            for(int col = 0; col < cells[0].length; col++){
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                Cell c = new Cell(x, y, CELL_SIZE, row, col, CellState.DEAD, mR);
                cells[row][col] = c;
            }
        }
    }

    public void mouseClicked() {
        super.mouseClicked();
        int column = mouseX/CELL_SIZE;
        int row = mouseY/CELL_SIZE;
        Cell c = cells[row][column];
        c.handleMouseClicked();
    }

    public void mouseDragged() {
        super.mouseDragged();
        int column = mouseX/CELL_SIZE;
        int row = mouseY/CELL_SIZE;
        Cell c = cells[row][column];
        c.handleMouseClicked();
    }

    public static void main(String[] args){
        PApplet.main("Main");
    }

    public void draw(){
        for(int row = 0; row < cells.length; row++){
            for(int col = 0; col < cells[0].length; col++){
                Cell c = cells[row][col];
                c.display();
            }
        }
        if(doEvolve == true){
            applyRules(cells);
            evolve(cells);
        }
    }

    public void keyPressed(){
        doEvolve = !doEvolve;
    }
    public void applyRules(Cell[][] cells){
        for(int r = 1; r < cells.length - 1; r++){
            for(int c = 1; c < cells[0].length - 1; c++){
                cells[r][c].applyRules(cells);
            }
        }
    }

    public void evolve(Cell[][] cells){
        for(int r = 0; r < cells.length; r++){
            for(int c = 0; c < cells[0].length; c++){
                cells[r][c].evolve();
            }
        }
    }


}