/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsa.domain;

/**
 *
 * @author leniejoice
 */
public class StockLabelStatus {
    int id;
    String status;
    
    public int getId(){
        return id;
    }
    
    public String getFinalStockLabelStatus(){
        return status;
    }
    
    public void setId(int id){
        this.id=id;
    }
    
    public void setString(String status){
        this.status=status;
    }
    
     public void setStockStatus(String status){
        this.status=status;
    }
    
    public String toString(){
        return this.status;
        
    }
}
