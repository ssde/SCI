/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssde.desktop.sci.model;

/**
 *
 * @author Jorge Rivera Andrade (SSDE)
 */
public class ItemPrecio {
    private String ItemId;
    private double precio;

    /**
     * @return the ItemId
     */
    public String getItemId() {
        return ItemId;
    }

    /**
     * @param ItemId the ItemId to set
     */
    public void setItemId(String ItemId) {
        this.ItemId = ItemId;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
