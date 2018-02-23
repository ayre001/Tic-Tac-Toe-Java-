package tic.tac.toe;

/**
 *
 * @author creeperdash
 */
public class ScanMatrix {
    Squares scanMatrix = new Squares(false,0);
    int highMatrix;
    
    public Squares setScanMatrix(Squares squares){
        this.scanMatrix = squares;
        return squares;
    }
    

    public int highMatrix(Squares scanMatrix){
        for (int i= 0; i < 3 ; i++){
            //this.highMatrix = scanMatrix[i].owner;
            
        }
        return this.highMatrix;
    }
    

}
