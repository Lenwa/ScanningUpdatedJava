package freightscanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Kyle
 */
public class Frame {
    private Freight freight;
    private JFrame window; 
    private JLabel[] label = new JLabel[12];
    private JPanel panel, panel1, background;
    private JButton scanB, loadB, dockB, OSDB;
    private String[] info;
    private JLabel trailerDoor, trailerNumber;
    private Trailer trailer;
    private FreightUtil freightUtil;
    private JPanel[] panels = new JPanel[13];
    private Border raisedetched = BorderFactory.createEtchedBorder
                            (EtchedBorder.RAISED);
    private Border loweredetched = BorderFactory.createEtchedBorder
                               (EtchedBorder.LOWERED);
    private Font buttonFont = new Font("Droid Serif", Font.BOLD, 30);
    private Font font = new Font("Droid Serif", Font.BOLD, 18);
    /**
     *
     */
    public Frame(){
        //create components 
        window = new JFrame();
        panel = new JPanel();
        panel1 = new JPanel();
        for(int i=0; i<panels.length; i++){
            panels[i] = new JPanel();
            panels[i].setBackground(Color.YELLOW);
            panels[i].setBorder(loweredetched);
            panel.add(panels[i]);
        }
        background = new JPanel(new BorderLayout());
        scanB = new JButton("Scan");
        OSDB = new JButton("OS&D");
        dockB = new JButton("Dock");
        loadB = new JButton("Load");
        
        //add listeners to buttons
        scanB.addActionListener(new Scan());
        scanB.setBorder(raisedetched);
        scanB.setFont(buttonFont);
        OSDB.addActionListener(new OSD());
        OSDB.setFont(buttonFont);
        OSDB.setBorder(raisedetched);
        dockB.addActionListener(new DOCK());
        dockB.setFont(buttonFont);
        dockB.setBorder(raisedetched);
        loadB.addActionListener(new LOAD());
        loadB.setFont(buttonFont);
        loadB.setBorder(raisedetched);
        
        //JPanel
        panel1.setBorder(raisedetched);
        panel.setBorder(raisedetched);
        panel.setLayout(new GridLayout(0,2));
        panel.setBackground(Color.orange);
        panel1.setBackground(Color.BLUE);
        
        //add buttons to panel1
        panel1.add(scanB);
        panel1.add(Box.createHorizontalStrut(30));
        panel1.add(OSDB);
        panel1.add(Box.createHorizontalStrut(30));
        panel1.add(dockB);
        panel1.add(Box.createHorizontalStrut(30));
        panel1.add(loadB);
        
        //add componets to window
        window.setSize(1100, 400);
        window.setLocationRelativeTo(null);
        window.setTitle("Scanning");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        background.add(panel1, BorderLayout.PAGE_START);
        background.add(panel, BorderLayout.CENTER);
        window.add(background);


        //set window visible
        window.setVisible(true);
    }
    
    /**
     * sets the JLabels in the JLabel array and adds them to the left side panel
     */
    private void setLabels(){
        trailerDoor = new JLabel("Freight is being loaded in: "
                        + trailer.getTrailerDoor());
        trailerNumber = new JLabel("Freight goes on " + 
                        trailer.getTrailerNumber());
        trailerDoor.setFont(font);
        trailerDoor.setForeground(Color.RED);
        trailerNumber.setFont(font);
        trailerNumber.setForeground(Color.RED);
        panels[0].add(trailerDoor);
        panels[1].add(trailerNumber);
        for(int i=0;i<info.length;i++){
            label[i] = new JLabel(info[i]);
            label[i].setFont(font);
            panels[i].add(label[i]);
        }
        for(int i=2; i<panels.length; i++){
            panels[i].add(label[i-2]);
            panels[i].validate();
            panels[i].repaint();
        }
        panels[0].repaint();
        panels[0].revalidate();
        panels[1].repaint();
        panels[1].revalidate();
    }
    
    /**
     * gets all the information about a piece of freight and stores it in an array
     */
    private void setLabelInfo(){
        this.info = new String[11];
        info[0] = freight.getDestination(); 
        info[1] = freight.getDoorNumber();
        info[2]= freight.getWeight();
        info[3]= freight.getPieceCount();
        info[4]= freight.getShipper();
        info[5]= freight.getShipperAddress();
        info[6]= freight.getConsignee();
        info[7]= freight.getConsigneeAddress() ;
        info[8]= freight.getHazmat();
        info[9]= freight.getPosion() ;
        info[10]= freight.getFood();
    }
    
    //removes JLabels from panel so new ones can be placed, was getting 
    //nullpointerexceptions when I used a for loop so typed out whole array
    private void removeLabels(){
        panels[2].remove(label[0]);
        panels[3].remove(label[1]);
        panels[4].remove(label[2]);
        panels[5].remove(label[3]);
        panels[6].remove(label[4]);
        panels[7].remove(label[5]);
        panels[8].remove(label[6]);
        panels[9].remove(label[7]);
        panels[10].remove(label[8]);
        panels[11].remove(label[9]);
        panels[12].remove(label[10]);
        panels[0].remove(trailerDoor);
        panels[1].remove(trailerNumber);
        for(int i=0; i<panels.length;i++){
            panels[i].revalidate();
            panels[i].repaint();
        }
    }
    
    /**
     * Scan button which creates a new instance of freight and calls functions 
     * necessary for setting freight information
     */
    private class Scan implements ActionListener {        
        
        public void actionPerformed(ActionEvent e) {           
            Object input1 = JOptionPane.showInputDialog(window, "Scan Freight");        
            String pro = input1.toString();
            freight = new Freight();
            trailer = new Trailer();
            freightUtil = new FreightUtil();
            freight.setPro(pro);
            freight.setFreightInfo(freightUtil.setInfoArray(pro));
            setLabelInfo();
            trailer.setTrailerInfo(freight.getDestination());
            //for the first time through otherwise nullpointerexceptions
            if(label[2] == null){
                setLabels();
            }
            else{
                removeLabels();
                setLabels();
            }
        }
    }
    
    /**
     * when pushed supervisor has to authorize with a scan of their badge
     * updates both OSD file and pro file,  
     */
    private class OSD implements ActionListener {
        
        public void actionPerformed(ActionEvent e){  
            //files don't have multiple lines of being put into OS&D
            if(freight.isInOSD() == false){
                freight.setInOSD();
                freightUtil.setLocationOSD(freight.getPro());
            }
            else{
                JOptionPane.showMessageDialog(window, "Freight has been "
                        + "put in OS&D already");
            }
        }
    }
    
    /**
     * Updates freight location as docked
     */
    private class DOCK implements ActionListener {
        
        public void actionPerformed(ActionEvent e){
            //to prevent multiple instances of docking
            if(freight.isDocked() == false && freight.isInOSD() == false){
                freight.setDocked();
                freightUtil.setLocationDocked(freight.getPro(), 
                        freight.getDoorNumber());
            }
            else{
                JOptionPane.showMessageDialog(window, "Freight has already been "
                    + "docked or is in OS&D");
            }
        }
    }
    
    /**
     * takes trailer number as input and updates pro location and trailer file
     * if everything matches
     */
    private class LOAD implements ActionListener {
        
        public void actionPerformed(ActionEvent e){
            String trueTrailerNum = trailer.trueTrailerNumber();
            String pro = freight.getPro();
            String trueDest = trailer.getTrueDest();
            String trailerNumber = trailer.getTrailerNumber();
            freightUtil.setLocationLoaded(pro, trueTrailerNum, trueDest,
                        trailerNumber);
        }
    }
   
} 
