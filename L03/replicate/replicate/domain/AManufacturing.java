package domain;
import java.util.*;
import  java.lang.Class;
import java.lang.reflect.Constructor;

/*No olviden adicionar la documentacion*/
public class AManufacturing{
    private static int SIZE=50;
    private Thing[][] lattice;
    
    public AManufacturing() {
        lattice=new Thing[SIZE][SIZE];
        for (int r=0;r<SIZE;r++){
            for (int c=0;c<SIZE;c++){
                lattice[r][c]=null;
            }
        }
        someThings();
    }

    public int getSize(){
        return SIZE;
    }

    public Thing getThing(int r,int c){
        return lattice[r][c];
    }

    public void setThing(int r, int c, Thing e){
        if (lattice[r][c] instanceof StickyWall){
            System.out.println("There's a sticky wall here. You cannot create anything here.");
        }else{
            lattice[r][c]=e;
        }
    }

    public void someThings(){  
        //CELLS
        Cell yersinia = new Cell(this,10,10, true);
        Cell listeria = new Cell(this,15,15,true);
        setThing(10,10,yersinia);
        setThing(15,15,listeria);
        
        //TOURIST CELLS
        TouristCell move = new TouristCell(this, 5,5,true);
        TouristCell walk = new TouristCell(this, 20,20,true);
        setThing(5,5,move);
        setThing(20,20,walk);
        
        //POISON
        Poison mercury = new Poison();
        Poison arsenic = new Poison();
        setThing(0, 0, mercury);
        setThing(0, getSize() - 1, arsenic);
        
        // Reflective Cells
        ReflectiveCell alice = new ReflectiveCell(this, 25, 25, true);
        ReflectiveCell bob = new ReflectiveCell(this, 30, 30, true);
        setThing(25, 25, alice);
        setThing(30, 30, bob);
        
        // Reflective Cells - unitTest compilation in program
        ReflectiveCell Pedraza = new ReflectiveCell(this, 5, 5, true);
       // ReflectiveCell Sanchez = new ReflectiveCell(this, 5, 6, true);
        setThing(5, 5, Pedraza);
        //setThing(5, 6, Sanchez);
        
        // STICKY WALL
        // Define rows for each Wall
        int pedrazaRow = 4; // Example row for the Pedraza Wall
        int sanchesRow = 19; // Example row for the Sanches Wall
    
        // Create the Walls with your names
        StickyWall pedrazaWall = new StickyWall(this, pedrazaRow);
        StickyWall sanchesWall = new StickyWall(this, sanchesRow);
        
    }
    
    public int neighborsActive(int r, int c){
        int num=0;
        for(int dr=-1; dr<2;dr++){
            for (int dc=-1; dc<2;dc++){
                if ((dr!=0 || dc!=0) && inLatice(r+dr,c+dc) && 
                    (lattice[r+dr][c+dc]!=null) &&  (lattice[r+dr][c+dc].isActive())) num++;
            }
        }
        return (inLatice(r,c) ? num : 0);
    }
   

    
    public boolean isEmpty(int r, int c){
        return (inLatice(r,c) && lattice[r][c]==null);
    }    
        
    protected boolean inLatice(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
   
    public void ticTac() {
    // Determine the next state of cells
    for (int r = 0; r < SIZE; r++) {
        for (int c = 0; c < SIZE; c++) {
            Thing thing = lattice[r][c];
            if (thing != null) {
                thing.decide();
            }
        }
    }
    // Update the state of cells
    for (int r = 0; r < SIZE; r++) {
        for (int c = 0; c < SIZE; c++) {
            Thing thing = lattice[r][c];
            if (thing != null) {
                thing.change();
            }
        }
    }
}



}
