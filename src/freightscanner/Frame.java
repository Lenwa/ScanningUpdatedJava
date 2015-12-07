package freightscanner;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Kyle
 */
public class Frame {
    private Freight freight;
    private JFrame window; 
    private JLabel[] label = new JLabel[12];
    private JPanel panel, panel1;
    private JButton scanB, loadB, dockB, OSDB;
    private String[] info;
    private JLabel trailerDoor, trailerNumber;
    private Trailer trailer;
    private FreightUtil freightUtil;
    /**
     *
     */
    public Frame(){
        //create components 
        window = new JFrame();
        panel = new JPanel();
        panel1 = new JPanel();
        scanB = new JButton("Scan");
        OSDB = new JButton("OS&D");
        dockB = new JButton("Dock");
        loadB = new JButton("Load");
        
        //add listeners to buttons
        scanB.addActionListener(new Scan());
        OSDB.addActionListener(new OSD());
        dockB.addActionListener(new DOCK());
        loadB.addActionListener(new LOAD());
        
        //JPanel
        panel.setSize(400, 400);
        panel.setLayout(new GridLayout(0,2));
        panel1.setSize(400, 400);
        panel1.setLayout(new GridLayout(2,2));
        panel.setBackground(Color.orange);
        panel1.setBackground(Color.red);
        
        //add buttons to panel1
        panel1.add(scanB);
        panel1.add(OSDB);
        panel1.add(dockB);
        panel1.add(loadB);
        
        //add componets to window
        window.setSize(1000, 400);
        window.setLocationRelativeTo(null);
        window.setTitle("Scanning");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(1,2));
        window.add(panel);
        window.add(panel1);

        //set window visible
        window.setVisible(true);
    }
    
    /**
     * sets the JLabels in the JLabel array and adds them to the left side panel
     */
    private void setLabels(){
        for(int i=0;i<info.length;i++){
            label[i] = new JLabel(info[i]);
            panel.add(label[i]);
        }
        trailerDoor = new JLabel("Freight is being loaded in: "
                + trailer.getTrailerDoor());
        trailerNumber = new JLabel("Freight goes on " + 
                trailer.getTrailerNumber());
        panel.add(trailerDoor);
        panel.add(trailerNumber);
        panel.validate();
        panel.repaint();
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
        panel.remove(label[0]);
        panel.remove(label[1]);
        panel.remove(label[2]);
        panel.remove(label[3]);
        panel.remove(label[4]);
        panel.remove(label[5]);
        panel.remove(label[6]);
        panel.remove(label[7]);
        panel.remove(label[8]);
        panel.remove(label[9]);
        panel.remove(label[10]);
        panel.remove(trailerDoor);
        panel.remove(trailerNumber);
        panel.revalidate();
        panel.repaint();
    }
    
    /**
     * Scan button which creates a new instance of freight and calls functions 
     * necessary for setting freight information
     */
    private class Scan implements ActionListener {        
        
        public void actionPerformed(ActionEvent e) {           
            Object input1 = JOptionPane.showInputDialog(null, "Scan Freight");        
            String pro = input1.toString();
            freight = new Freight();
            trailer = new Trailer();
            freightUtil = new FreightUtil();
            freight.setPro(pro);
            freight.setFreightInfo(freightUtil.setInfoArray(pro));
            trailer.setTrailerInfo(freight.getDestination());
            setLabelInfo();
            //for the first time through otherwise nullpointerexceptions
            if(label[0] == null)
                setLabels();
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
                JOptionPane.showMessageDialog(panel, "Freight has been "
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
            if(freight.isDocked() == false){
                freight.setDocked();
                freightUtil.setLocationDocked(freight.getPro(), 
                        freight.getDoorNumber());
            }
            else{
                JOptionPane.showMessageDialog(panel, "Freight has already been "
                    + "docked");
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