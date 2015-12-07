/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freightscanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class FreightUtil {
    BufferedWriter writePro, writeTrailer, writeOSD;
    Scanner br;
    String info[];
    File proInfo;
    public FreightUtil(){
        
    }
    
    public String[] setInfoArray(String pro){
        info = new String[11];
        proInfo = new File("Database\\" + pro + ".txt");
        br = null;
        try {
            br = new Scanner(new BufferedReader(new FileReader(proInfo)));
        } 
        catch (FileNotFoundException ex) {
            proNotOnFile(pro);            
        }
        if(br == null){
            JOptionPane.showMessageDialog(null, "Freight placed in OS&D");
        }
        //scans the pro file's and stores pertient information in info array
        else{
            for(int i=0; i<info.length; i++){
                info[i] = br.nextLine();
            }
        }
        return info;
    }
    
    public void setLocationLoaded(String pro, String trueTrailerNum, 
        String trueDest, String trailerNumber){
        Object input = JOptionPane.showInputDialog(null, "Scan trailer");
        input.toString();
        writeTrailer = null;
        writePro = null;
        if(input.equals(trueTrailerNum)) {
            Object badge = JOptionPane.showInputDialog(null, "Scan Badge");
            try {
                writeTrailer = new BufferedWriter(new FileWriter
                            ("Database\\Lanes\\"+ trueDest + ".txt", true));
                writeTrailer.write(pro + " by " + badge);
                writeTrailer.newLine();
                writeTrailer.flush();
                writeTrailer.close();
            } 
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Trailer file not found");
            }                

            try{
                writePro =  new BufferedWriter(new FileWriter
                        ("Database\\" + pro + ".txt", true));
                writePro.write("Loaded on: " + trueTrailerNum + " by " + badge);
                writePro.newLine();
                writePro.flush();
                writePro.close();
            }
            catch(IOException e1){
                JOptionPane.showMessageDialog(null, "That pro is not on file");
            }
            JOptionPane.showMessageDialog(null, "Freight loaded on " +
                    trailerNumber);
        }
        else{
            JOptionPane.showMessageDialog(null, "Wrong Trailer");
        }
    }
    
    public void setLocationDocked(String pro, String doorNumber){
        writePro = null;
        Object badge = JOptionPane.showInputDialog(null, "Scan Badge");
        try{
            writePro =  new BufferedWriter(new FileWriter
                    ("Database\\" + pro + ".txt", true));
            writePro.write("Docked in STL " + doorNumber + "by " + badge);
            writePro.newLine();
            writePro.flush();
            writePro.close();
        }
        catch(IOException e1){
            JOptionPane.showMessageDialog(null, "That pro is not on file");
        }
        JOptionPane.showMessageDialog(null, "Freight docked");
    }
     /**
     * triggers if the pro scanned isn't on file
     * @param pro string taken from initial scan 
     */
    public void proNotOnFile(String pro){
        Object input = JOptionPane.showInputDialog(null, "Supervisor must "
        + "scan badge and freight held in OS&D"
        , "Notify Supervisor", JOptionPane.WARNING_MESSAGE);
        String badgeNumber = input.toString();
        try{
            writeOSD = new BufferedWriter(new FileWriter
                        ("Database\\OSD.txt", true));
            writeOSD.write(pro);
            writeOSD.write("  Authorized by: " + badgeNumber);
            writeOSD.write("   Reason: Pro not on file");
            writeOSD.newLine();
            writeOSD.flush();
            writeOSD.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "OS&D File not found, call the"
                    + " helpdesk", "Oops", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void setLocationOSD(String pro) {
        
        //makes sure to notify supervisor and have them sign off   
        Object input = JOptionPane.showInputDialog(null, "Supervisor must "
                    + "scan badge and freight held in OS&D"
                    , "Notify Supervisor", JOptionPane.WARNING_MESSAGE);
        String badgeNumber = input.toString();
        
        //list of reasons for freight put in OS&D
        String[] reasons = {"0. HAZ error", "1. per CSR", "2. not on file", 
            "3. no packing slip", "4. spill"};
        
        //builds an array's elemnents for use in a single JOptionPane
        StringBuilder builder = new StringBuilder(reasons.length);        
        for(String reason : reasons) {
            builder.append(reason);
            builder.append("\n");
        }
        
        //displays the builder in an input pane, takes input and parses into int
        //whatever choice is made gets appeneded to the pro's file along with loc
        Object ans = JOptionPane.showInputDialog(null, builder.toString(), 
                "Reasons", JOptionPane.QUESTION_MESSAGE);
        String ans1 = ans.toString();
        int choice = Integer.parseInt(ans1);
        
        //updates pro's location with a reason why and who authorized it
        try{
            writePro =  new BufferedWriter(new FileWriter("Database\\" + 
                    pro + ".txt", true));
            writePro.write("Held in STL OS&D  Authorized by:"  + badgeNumber +
                    "   Reason: " + reasons[choice]);
            writePro.newLine();
            writePro.flush();
            writePro.close();
        }
        catch(IOException e1){
            JOptionPane.showMessageDialog(null, "That pro is not on file");
        }
        //updates OS&D file with new pro
        try{
            writeOSD = new BufferedWriter(new FileWriter
                        ("Database\\OSD.txt", true));
            writeOSD.write(pro + "  Authorized by: " + badgeNumber + 
                    "   Reason: " + reasons[choice]);
            writeOSD.newLine();
            writeOSD.flush();
            writeOSD.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "OS&D File not found, call the"
                    + " helpdesk", "Oops", JOptionPane.WARNING_MESSAGE);
        }
    }
}