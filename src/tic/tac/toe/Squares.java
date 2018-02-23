package tic.tac.toe;
import javax.swing.JButton;
import javax.swing.ImageIcon;

        
/**
 * Project File Name: Squares.java
 * Project Date: 11/18/17
 * @author Damian Ayres
 */
public class Squares extends JButton{
    
    //local variables
    ImageIcon X,O;//empty ImageIcon variables
    boolean owned;//true - played tile, false - unplayed, default false
    int unowned     = 0;//unused here, included for reference
    int human       = 1;
    int computer    = 2;
    int owner;//0 - empty, 1 - human, 2 - computer, default 0

    /*  updates the JButton class with a boolean representing a played tile (owned)
        and the number representing the owner    */
    public Squares(boolean owned, int owner){
        X=new ImageIcon(this.getClass().getResource("X.png"));
        O=new ImageIcon(this.getClass().getResource("O.png"));
    }
    //getter method
    public boolean isOwned(){
        return this.owned;
    }
    //setter method
    public void setOwned(boolean owned){
        this.owned = owned;
    }
    //setter method
    public void setEmpty(){
        this.owned = false;
    }
    //getter method
    public int getOwner(){
        return this.owner;
    }
    //setter method
    public void setOwner(int owner){
        this.owner = owner;
    }
}
