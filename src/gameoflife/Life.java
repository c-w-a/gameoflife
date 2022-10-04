package gameoflife;

import java.util.concurrent.TimeUnit;
import java.lang.Math;

class GameOfLife {
    
    private Board board;
    
    private boolean halt = false;
    
    public GameOfLife() {
        board = new Board();
    }
    
    public Board getBoard() {
        return board;
    }
    
    public boolean getHalt() {
        return halt;
    }
    
    public void play(){
        
        board.print();
        
        
        try {
             Thread.sleep(120);
        } catch (InterruptedException ie) {
            
        }
        

        board.update(); 
        
        checkHalt();
        
    }
    
    private void checkHalt() {
        
        if (board.getAliveCount() == 0) halt = true;
    }
}

class Board{
    
    private Cell[] cells;
    
    private Cell[] ref;
    
    int aliveCount = 0;
    
    public Board() {
        cells = new Cell[2601];
        
        ref = new Cell[2601];
        
        for (int i = 0; i < 2601; i++) {
            cells[i] = new Cell();
            
            ref[i] = new Cell();
        }
    }
    
    public Cell[] getCells() {
        return cells;
    }
    
    public void setCells(int i, char c) {
        cells[i].setState(c);
    }
    
    public int getAliveCount() {
        return aliveCount;
    }
    
    public void print() {
        
        for (int i = 0; i < 2601; i++) {
            
            if (i % 51 == 0 && i > 0) System.out.println();
            
            System.out.print(cells[i].getState() + " ");
        }     
    }
    
    public void update() {
       
        updateNeighbours();
        
        aliveCount = 0;
        
        for (int i = 0; i < 2601; i++) {
            
            // update ref array
            ref[i].setNeighbours(cells[i].getNeighbours());
            ref[i].setState(cells[i].getState()); 
            
            // count alive cells 
            if (ref[i].getState() == '#') aliveCount++;
             
            // if cell is alive and if cell has 2 or 3 neighbours it stays alive
            if (ref[i].getState() == '#' && ref[i].getNeighbours() != 3 && ref[i].getNeighbours() != 2) {
                     cells[i].setState(' ');
                     
            // if cell is dead an if cell has 3 neighbours it becomes alive
            } else if (ref[i].getState() == ' ' && ref[i].getNeighbours() == 3) {
                    cells[i].setState('#');
            }
        } 
    }
    
    private void updateNeighbours() {
        
        for (int i = 0; i < 2601; i++) {
            
            int nCount = 0;
            

            if ((i - 52) >= 0 && i % 51 != 0) {
                if (cells[i - 52].getState() == '#') nCount++;
            }
            if ((i - 51) >= 0) {
                if (cells[i - 51].getState() == '#') nCount++;
            }
            if ((i - 50) >= 0 && (i + 1) % 40 != 0) {
                if (cells[i - 50].getState() == '#') nCount++;
            }
            if ((i + 1) % 51 != 0) {
                if (cells[i + 1].getState() == '#') nCount++;
            }
            if ((i + 52) < 2601 && (i + 1) % 40 != 0) {
                if (cells[i + 52].getState() == '#') nCount++;
            }
            if ((i + 51) < 2601 ) {
                if (cells[i + 51].getState() == '#') nCount++;
            }
            if ((i + 51) < 2601 && i % 51 != 0) {
                if (cells[i + 50].getState() == '#') nCount++;
            }
            if (i != 0 && i % 51 != 0) {
                if (cells[i - 1].getState() == '#') nCount++;
            }
            
            cells[i].setNeighbours(nCount);
            
        }     
    }
}

class Cell {
    
    private char state;
    
    private int neighbours;
    
    public Cell() {
        state = ' ';
        neighbours = 0;
    }
    
    public int getNeighbours() {
        return neighbours;
    }
   
    public void setNeighbours(int n) {
        neighbours = n;
    }
    
    public char getState() {
        return state;
    }
    
    public void setState(char c) {
        state = c;
    }
}

public class Life {

    public static void main(String[] args) {
        
        GameOfLife game = new GameOfLife();
        
        int[] init = new int[973];
        
        for (int i = 0; i < 973; i++) {
            init[i] = (int) (Math.random() * 2601);
        }
        
        for (int i = 0; i < 973; i++) {
            game.getBoard().setCells(init[i], '#');
        }

        while (!game.getHalt()) {
            game.play();
        }
    }
}
