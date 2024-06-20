package com.gildedrose;

public class Item {

    public String name;

    //Días que tenemos para venderlo
    public int sellIn;

    //Calidad del artículo
    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
