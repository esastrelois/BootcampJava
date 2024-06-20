package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    //Al final de cada día, nuestro sistema decrementa ambos valores para cada artículo mediante el método updateQuality
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
        	//El resto de productos que no son queso brie envejecido ni Entrada al Backstage
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) { //Asegura que la calidad de un artículo nunca sea negativa
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } 
             //El "Queso Brie envejecido" (Aged brie) incrementa su calidad a medida que se pone viejo
             //Su calidad aumenta en 1 unidad cada día
             else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    //Entradas al Backstage
                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    	//Si faltan 10 días o menos para el concierto, la calidad se incrementa en otra unidad más (2 al día)
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) { //Asegura que la calidad de un artículo nunca sea mayor a 50
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                        //Si faltan 5 días o menos, la calidad se incrementa de nuevo (3 unidades por día)
                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) { //Asegura que la calidad de un artículo nunca sea mayor a 50
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

           //El artículo "Sulfuras" no modifica su fecha de venta ni se degrada en calidad. El resto de artículos van descontando días de validez
            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            //Una vez que ha pasado la fecha recomendada de venta, la calidad se degrada al doble de velocidad
            if (items[i].sellIn < 0) {
            	//El resto de productos que no son queso brie envejecido ni entradas Backstage
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) { //Asegura que la calidad de un artículo nunca sea negativa
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } 
                    //Entradas Backstage --> Luego de la fecha de venta la calidad cae a 0
                     else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } 
                 //Queso brie envejecido
                 //Luego de la fecha de venta su calidad aumenta 2 unidades por día
                 else {
                    if (items[i].quality < 50) { //Asegura que la calidad de un artículo nunca sea mayor a 50
                        items[i].quality = items[i].quality + 1; //incrementa la calidad por segunda vez
                    }
                }
            }
        }
    }
}
