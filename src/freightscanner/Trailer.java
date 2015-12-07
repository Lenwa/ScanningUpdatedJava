
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freightscanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Kyle
 */
public class Trailer {
    String trailerDest, trailerDoor, trailerNum, trueDest, trueTrailerNum;
    String[] trailerInfo = new String[3];
    Scanner readTrailer = null;
    
    public Trailer(){
        
    }   
    public void setTrailerInfo(String destination){
        trueDest = destination.substring(destination.lastIndexOf(':')+2);
        try {
            readTrailer = new Scanner(new BufferedReader(new FileReader
            ("Database\\Lanes\\" + trueDest + ".txt")));
        } 
        catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lane not on file");
        }
        for(int i =0; i<trailerInfo.length; i++){
            trailerInfo[i] = readTrailer.nextLine();
        }
        trailerDest = trailerInfo[0];
        trailerDoor = trailerInfo[1];
        trailerNum = trailerInfo[2];
        trueTrailerNum = trailerNum.substring(trailerNum.lastIndexOf(':')+2);        
    }
    
    public String getTrailerDest(){
        return trailerDest;
    }
    
    public String getTrailerDoor(){
        return trailerDoor;
    }

    public String getTrailerNumber(){
        return trailerNum;
    }
    
    public String trueTrailerNumber(){
        return trueTrailerNum;
    }
    
    public String getTrueDest(){
        return trueDest;
    }
   
}