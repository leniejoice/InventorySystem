/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsa.domain;

import com.dsa.inventory.inventorysystem.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author leniejoice
 */
public class InventoryData {
    
    private int id;
    private Date dateEntered;
    private StockLabelStatus stockLabelStatus;
    private String brand;
    private String engineNumber;
    private PurchaseStatus purchaseStatus;

    
    //Getters
    public int getId(){
        return id;
    }
    
    public Date getDate(){
        return dateEntered;
    }
    
    public StockLabelStatus getStockLabelStatus(){
        return stockLabelStatus;
    }
    
    public String getBrand(){
        return brand;
    }
    
    public String getEngineNumber(){
        return engineNumber;
    }
    
    public PurchaseStatus getPurchaseStatus(){
        return purchaseStatus;
    }
    
    public String getFormattedDate(){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
        return this.dateEntered != null ? formatter.format(this.dateEntered):null;
    }
    
    
    //Setters
    
    public void setId(int id){
        this.id=id;
    }
    
    public void setDateEntered(Date dateEntered){
        this.dateEntered=dateEntered;
    }
    
    public void setStockLabelStatus(StockLabelStatus stockLabelStatus){
        this.stockLabelStatus=stockLabelStatus;
    }
    
    public void setBrand(String brand){
        this.brand=brand;
    }
    
    public void setEngineNumber(String engineNumber){
        this.engineNumber=engineNumber;
    }
    
    public void setPurchaseStatus(PurchaseStatus purchaseStatus){
        this.purchaseStatus=purchaseStatus;
    }
    
}
