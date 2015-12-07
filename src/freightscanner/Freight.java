package freightscanner;


/**
 *
 * @author Kyle
 */

public class Freight {
    private String pro, destination, doorNumber, weight, pieceCount, shipper,
            shipperAddress, consignee, consigneeAddress, hazmat, posion, food;
    private boolean docked = false;
    private boolean inOSD = false;
    private boolean isHazmat = false;
    private boolean isPosion = false;
    private boolean isFood = false;
    public Freight(){
        
    }
    
    public void setFreightInfo(String[] info){
        destination = info[0];
        doorNumber = info[1];
        weight = info[2];
        pieceCount = info[3];
        shipper = info[4];
        shipperAddress = info[5];
        consignee = info[6];
        consigneeAddress = info[7];
        hazmat = info[8];
        posion = info[9];
        food = info[10];   
    }
    
    /**
     * sets the pro to whatever the scan input is, nine numbers sometimes
     * with two letters at the beginning 
     * @param pro nine digit number that identifies a piece of freight 
     */
    public void setPro(String pro){
        this.pro = pro;
    }
    
    /**
     * get the pro of the freight 
     * @return pro, the main identifier of a piece of freight
     */
    public String getPro(){
        return pro;
    }

    /**
     *
     * @return freight destination
     */
    public String getDestination(){
        return destination;
    }
    
    /**
     *
     * @return get static door number for terminal 
     */
    public String getDoorNumber(){
        return doorNumber;
    }
    
    /**
     *
     * @return weight of freight 
     */
    public String getWeight(){
        return weight;
    }
    
    /**
     *
     * @return piece count of freight
     */
    public String getPieceCount(){
        return pieceCount;
    }
    
    /**
     *
     * @return name of shipper
     */
    public String getShipper(){
        return shipper;
    }
    
    /**
     *
     * @return return shipper's address
     */
    public String getShipperAddress(){
        return shipperAddress;
    }
    
    /**
     *
     * @return consignee's name  
     */
    public String getConsignee(){
        return consignee;
    }
    
    /**
     *
     * @return consignee's address
     */
    public String getConsigneeAddress(){
        return consigneeAddress;
    }
    
    /**
     *
     * @return hazmat type if there is one otherwise returns string "N/A"
     */
    public String getHazmat(){
        return hazmat;
    }
    
    /**
     *
     * @return if posion or not
     */
    public String getPosion(){
        return posion;
    }
    
    /**
     *
     * @return if freight is food or not
     */
    public String getFood(){
        return food;
    }
    
    /**
     *sets freight to docked
     */
    public void setDocked(){
         this.docked = true;
    }
    
    /**
     *
     * @return boolean if freight is docked or not
     */
    public boolean isDocked(){
        return docked;
    }
    
    /**
     * 
     *set freight in OSD
     */
    public void setInOSD(){
        this.inOSD = true;
    }
    
    /**
     *
     * @return check and see if freight has been placed in OSD
     */
    public boolean isInOSD(){
        return inOSD;
    }
}




