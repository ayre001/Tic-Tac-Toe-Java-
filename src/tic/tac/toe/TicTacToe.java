package tic.tac.toe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Project File Name: TicTacToe.java
 * Project Date: 11/18/17
 * @author Damian Ayres
 */
public class TicTacToe extends JFrame implements ActionListener {

    
    //initialize variables
    private final int WINDOW_WIDTH = 700;
    private final int WINDOW_HEIGHT = 700;
    ImageIcon X,O;//
    boolean owned;//false - empty cell, true - owned cell, default false
    boolean winner;//calls newGame() when true
    boolean activeTurn;//nextTurn() runs when true
    int unowned     = 0;//ownership states for squares
    int human       = 1;//ownership states for squares
    int computer    = 2;//ownership states for squares
    int owner;//0 - empty, 1 - human, 2 - computer, default 0    
    int debug;    
    int[] scanMatrix = new int[9];//an array to check for win patterns
    Random rand = new Random();
    
    //grid and buttons
    JPanel gameBoard = new JPanel();//the grid
    public Squares[] squares = new Squares[9];//the buttons
        
    /*
        CONSTRUCTOR
    
        This constructor initializes the components of the game board as a 
        JFrame object.  The squares contain an action listener that will
        run through a series of steps every time a square is clicked
    */
    public TicTacToe(){
        //basic JFrame window set up
        super("Tic-Tac-Toe");// 
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setLayout(new GridLayout(3,3));   
        //loop to add the buttons on the board
        for (int i =0; i<9; i++){
            squares[i]=new Squares(false,0);//all squares are false (unowned) and equal to 0 (empty)
            squares[i].addActionListener(this);//add a listener to the button
            gameBoard.add(squares[i]);//populate the board with square buttons
            X=new ImageIcon(squares[i].getClass().getResource("X.png"));//button can contain an X or O icon
            O=new ImageIcon(squares[i].getClass().getResource("O.png"));//button can contain an X or O icon
            }
        add(gameBoard);//adds the gameboard to the JPanel
        setVisible(true);//reveal the gameboard
        playRandom();//plays the first move when the game begins
    }
    //clicking a square calls this command.  runs a play cycle or checks for a win
    public void actionPerformed(ActionEvent e){
        //if the square is not owned (false), set the icon to X for the player
        //and change the ownership parameters to the human player
        for (int i = 0; i<9; i++){
            if (e.getSource() == squares[i]){
                if (squares[i].isOwned() == false){
                    squares[i].setIcon(X);
                    squares[i].setOwner(human);
                    squares[i].setOwned(true);
                    isWinner();//checks for a win after human plays
                    if (winner){
                        newGame();
                        break;
                    }
                    checkTie();//checks for a tie if no win
                    nextTurn();//lets computer play next
                    isWinner();//checks for a win after computer plays                    
                    if (winner){
                        newGame();
                        break;
                    }
                    checkTie();//check for a tie if no win after computer plays
                }
            }
        }
    };
    //plays computer's turn by calling all the scan matrices to look for valid spot
    public void nextTurn(){
        activeTurn = true;
        do{
            highRow();
            midRow();
            lowRow();
            leftCol();
            midCol();
            rightCol();
            diag1();
            diag2();
            activeTurn = false;
        }while(activeTurn);
        
    }
    //plays a random square on the first turn
    public void playRandom(){
        int r = rand.nextInt(9);
        squares[r].setIcon(O);
        squares[r].setOwner(computer);
        squares[r].setOwned(true);
    }
    //check the grid for a winner by calling all the scanMatrix functions
    public void isWinner(){
        highRow();
        midRow();
        lowRow();
        leftCol();
        midCol();
        rightCol();
        diag1();
        diag2();
    }
    //checks for a tie if all squares are owned by someone and there is no winner
    public void checkTie(){
        if (squares[0].owned == true && squares[1].owned == true && 
                squares[2].owned == true && squares[3].owned == true &&
                squares[4].owned == true && squares[5].owned == true &&
                squares[6].owned == true && squares[7].owned == true &&
                squares[8].owned == true && !winner){
                JOptionPane.showMessageDialog(null, "It was tied!");
                newGame();
        }
    }
    /*  sets all squares to unowned (false), and empty (owner = 0)
        the icons are removed and the winner boolean is reset to false    */
    public void newGame(){
        JOptionPane.showMessageDialog(null, "Thanks for playing!");
        for (int i = 0; i<9; i++){
            squares[i].setEmpty();
            squares[i].setOwner(unowned);
            squares[i].setIcon(null);
            winner = false;
        }
        playRandom();
    }
    
    //upper row scan matrix
    public void highRow(){
        //creates an array from the top row
        for (int i= 0; i < 3 ; i++){
            scanMatrix[i] = squares[i].owner;
        }
        //check if the nextTurn function is active, if not, check for a winner
        if (!activeTurn){
            //check for winning pattern
            if (scanMatrix[0] == scanMatrix[1] && scanMatrix[1] == scanMatrix[2]
                && scanMatrix[0] != 0){
                winner = true;
            }        
        } else {
            //check for a spot to play
            if (scanMatrix[0] ==  scanMatrix[1] || scanMatrix[0] == scanMatrix[2] 
                    || scanMatrix[1] == scanMatrix[2] ){
                //play the first open spot
                for (int i= 0; i < 3 ; i++){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }
                }
            }
    }
 
    //middle row scan matrix
    public void midRow(){
        for (int i= 3; i < 6 ; i++){
            scanMatrix[i] = squares[i].owner;
        }
        if (!activeTurn){
            if (scanMatrix[3] == scanMatrix[4] && scanMatrix[4] == scanMatrix[5]
            && scanMatrix[3] != 0){
                winner = true;
             }
        } else {
            if (scanMatrix[3] ==  scanMatrix[4] || scanMatrix[3] == scanMatrix[5] 
            || scanMatrix[4] == scanMatrix[5] ){
                for (int i= 3; i < 6 ; i++){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }
                }
        }

    }
    
    //bottom row scan matrix
    public void lowRow(){
        for (int i= 6; i < 9 ; i++){
            scanMatrix[i] = squares[i].owner;
        }
        if  (!activeTurn){
                if (scanMatrix[6] == scanMatrix[7] && scanMatrix[7] == scanMatrix[8]
                && scanMatrix[6] != 0){
                    winner = true;
                }               
        } else {
            if (scanMatrix[6] ==  scanMatrix[7] || scanMatrix[6] == scanMatrix[8] 
            || scanMatrix[7] == scanMatrix[8] ){
                for (int i= 6; i < 9 ; i++){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }  
            }
        }

    }
    
    //scans the left column for winners or playable spots
    public void leftCol(){
        for (int i= 0; i < 9 ; i+= 3){
            scanMatrix[i] = squares[i].owner;
        }
        if (!activeTurn){
            if (scanMatrix[0] == scanMatrix[3] && scanMatrix[3] == scanMatrix[6]
                && scanMatrix[0] != 0){
                winner = true;
            }           
        } else {
            if (scanMatrix[0] ==  scanMatrix[3] || scanMatrix[0] == scanMatrix[6] 
            || scanMatrix[6] == scanMatrix[3] ){
                for (int i= 0; i < 9 ; i+=3){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }  
            }            
        }
    
    }
    
    //scans the middle column for winners or playable spots
    public void midCol(){
        for (int i= 1; i < 9 ; i+= 3){
            scanMatrix[i] = squares[i].owner;
            
        }
        if (!activeTurn){
                if (scanMatrix[1] == scanMatrix[4] && scanMatrix[4] == scanMatrix[7]
                && scanMatrix[1] != 0){
                    winner = true;
                }
            } else {
            if (scanMatrix[1] ==  scanMatrix[4] || scanMatrix[1] == scanMatrix[7] 
                || scanMatrix[4] == scanMatrix[7] ){
                for (int i= 1; i < 9 ; i+=3){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }  
            }            
        }
    }
    
    //scans the right column for winners or playable spots
    public void rightCol(){
        for (int i= 2; i < 9 ; i+= 3){
            scanMatrix[i] = squares[i].owner;
            
        }
        if (!activeTurn){
            if (scanMatrix[2] == scanMatrix[5] && scanMatrix[5] == scanMatrix[8]
                && scanMatrix[2] != 0){
            winner = true;
            }           
        } else {
            if (scanMatrix[2] ==  scanMatrix[5] || scanMatrix[2] == scanMatrix[8] 
                || scanMatrix[5] == scanMatrix[8] ){
                for (int i= 2; i < 9 ; i+=3){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }  
            }            
        }
          
    }
    
    //diagonal scan pattern
    public void diag1(){
        for (int i= 0; i < 9 ; i+= 4){
            scanMatrix[i] = squares[i].owner;
            
        }
        if (!activeTurn){
            if (scanMatrix[0] == scanMatrix[4] && scanMatrix[4] == scanMatrix[8]
                && scanMatrix[0] != 0){
                winner = true;
            }         
        } else {
            if (scanMatrix[0] ==  scanMatrix[4] || scanMatrix[0] == scanMatrix[8] 
                || scanMatrix[4] == scanMatrix[8] ){
                for (int i= 0; i < 9 ; i+=4){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }  
            }             
        }
          
    }
    
    //diagonal scan pattern
    public void diag2(){
        for (int i= 2; i < 9 ; i+= 2){
            scanMatrix[i] = squares[i].owner;
            
        }
        if (!activeTurn){
            if (scanMatrix[2] == scanMatrix[4] && scanMatrix[4] == scanMatrix[6]
                && scanMatrix[2] != 0){
                winner = true;
            }             
        } else {
            if (scanMatrix[2] ==  scanMatrix[4] || scanMatrix[2] == scanMatrix[6] 
                || scanMatrix[4] == scanMatrix[6] ){
                for (int i= 0; i < 9 ; i+=4){
                        if (scanMatrix[i] == unowned){
                            squares[i].setIcon(O);
                            squares[i].setOwner(computer);
                            squares[i].setOwned(true);
                            activeTurn = false;
                            break;
                        }
                    }  
            }            
        }
          
    } 
}
