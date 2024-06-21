package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Pruebas de la clase GildedRose")
@TestMethodOrder(MethodOrderer.MethodName.class)
class GildedRoseTest {
	Item[] items;
	GildedRose gildedRose = new GildedRose(items);
	
	@BeforeEach
	void setUp() throws Exception {
		Item[] items = new Item[1];
		gildedRose = new GildedRose(items);
	}
	
	@Nested
	@DisplayName("Metodo updateQuality")
	class updateQuality {
		@Nested
		class OK {
			/*
			Al final de cada día, nuestro sistema decrementa ambos valores para cada artículo --> Caso 1 (limite superior) y Caso 2 (limite inferior)
			La calidad de un artículo nunca es negativa --> Caso 3
			Una vez que ha pasado la fecha recomendada de venta, la calidad se degrada al doble de velocidad --> Caso 4
			*/
			@DisplayName("Acualizar calidad productos generales")
			@ParameterizedTest(name = "Caso {index}: name {0}, sellIn {1}, quality {2} --> updateQuality --> sellIn {3}, quality {4}")
			@CsvSource(value = {"Jamon,20,50,19,49",
								"Mortadela,0,1,-1,0",
								"Lomo,1,0,0,0",
								"Chorizo,-1,7,-2,5"})
			void test01UpdateQualityProductosGenerales(String nombre, int sellIn, int qualityIn, int sellInOut, int qualityOut) {
				Item item = new Item(nombre, sellIn, qualityIn);
				gildedRose.items[0] = item;
                gildedRose.updateQuality();
                assertEquals(sellInOut, gildedRose.items[0].sellIn);
                assertEquals(qualityOut, gildedRose.items[0].quality);
		    }
			
			/*
			Entrada al Backstage --> se incrementa la calidad --> Caso 1
			Una "Entrada al Backstage", como el queso brie, incrementa su calidad a medida que la fecha de venta se aproxima:
			si faltan 10 días o menos para el concierto, la calidad se incrementa en 2 unidades --> Caso 2
			si faltan 5 días o menos, la calidad se incrementa en 3 unidades --> Caso 3
			luego de la fecha de venta la calidad cae a 0 --> Caso 4
			La calidad nunca debe pasar de 50 --> Caso 5
			*/
			@DisplayName("Acualizar calidad entradas Backstage")
			@ParameterizedTest(name = "Caso {index}: name {0}, sellIn {1}, quality {2} --> updateQuality --> sellIn {3}, quality {4}")
			@CsvSource(value = {"Backstage passes to a TAFKAL80ETC concert,20,44,19,45",
								"Backstage passes to a TAFKAL80ETC concert,10,44,9,46",
								"Backstage passes to a TAFKAL80ETC concert,5,44,4,47",
								"Backstage passes to a TAFKAL80ETC concert,-1,50,-2,0",
								"Backstage passes to a TAFKAL80ETC concert,20,50,19,50"})
			void test02UpdateQualityEntradasBackStage(String nombre, int sellIn, int qualityIn, int sellInOut, int qualityOut) {
				Item item = new Item(nombre, sellIn, qualityIn);
				gildedRose.items[0] = item;
                gildedRose.updateQuality();
                assertEquals("Backstage passes to a TAFKAL80ETC concert",gildedRose.items[0].name);
                assertEquals(sellInOut, gildedRose.items[0].sellIn);
                assertEquals(qualityOut, gildedRose.items[0].quality);
		    }
			
			/* 
			El artículo "Sulfuras" (Sulfuras), siendo un artículo legendario, no modifica su fecha de venta ni se degrada en calidad 
			*/
			@DisplayName("Acualizar calidad Sulfuras")
			@ParameterizedTest(name = "Caso {index}: name {0}, sellIn {1}, quality {2} --> updateQuality --> sellIn {3}, quality {4}")
			@CsvSource(value = {"'Sulfuras, Hand of Ragnaros',20,44,20,44"})
			void test03UpdateQualitySulfuras(String nombre, int sellIn, int qualityIn, int sellInOut, int qualityOut) {
				Item item = new Item(nombre, sellIn, qualityIn);
				gildedRose.items[0] = item;
                gildedRose.updateQuality();
                assertEquals("Sulfuras, Hand of Ragnaros",gildedRose.items[0].name);
                assertEquals(sellInOut, gildedRose.items[0].sellIn);
                assertEquals(qualityOut, gildedRose.items[0].quality);
		    }
			
			/* 
			El "Queso Brie envejecido" (Aged brie) incrementa su calidad a medida que se pone viejo
			Su calidad aumenta en 1 unidad cada día --> Caso 1
			luego de la fecha de venta su calidad aumenta 2 unidades por día --> Caso 2
			La calidad nunca debe pasar de 50 --> Caso 3
			*/
			@DisplayName("Acualizar calidad Queso Brie")
			@ParameterizedTest(name = "Caso {index}: name {0}, sellIn {1}, quality {2} --> updateQuality --> sellIn {3}, quality {4}")
			@CsvSource(value = {"Aged Brie,20,44,19,45",
								"Aged Brie,-1,44,-2,46",
								"Aged Brie,20,50,19,50"})
			void test04UpdateQualityQuesoBrie(String nombre, int sellIn, int qualityIn, int sellInOut, int qualityOut) {
				Item item = new Item(nombre, sellIn, qualityIn);
				gildedRose.items[0] = item;
                gildedRose.updateQuality();
                assertEquals("Aged Brie",gildedRose.items[0].name);
                assertEquals(sellInOut, gildedRose.items[0].sellIn);
                assertEquals(qualityOut, gildedRose.items[0].quality);
		    }
			
			/* 
			Los artículos conjurados degradan su calidad al doble de velocidad que los normales
			*/
			@DisplayName("Acualizar calidad Conjurados")
			@ParameterizedTest(name = "Caso {index}: name {0}, sellIn {1}, quality {2} --> updateQuality --> sellIn {3}, quality {4}")
			@CsvSource(value = {"Conjurado,20,44,19,42",
								"Conjurado,-1,44,-2,42",
								"Conjurado,20,0,19,0"})
			void test05UpdateQualityConjurados(String nombre, int sellIn, int qualityIn, int sellInOut, int qualityOut) {
				Item item = new Item(nombre, sellIn, qualityIn);
				gildedRose.items[0] = item;
                gildedRose.updateQuality();
                assertEquals("Conjurado",gildedRose.items[0].name);
                assertEquals(sellInOut, gildedRose.items[0].sellIn);
                assertEquals(qualityOut, gildedRose.items[0].quality);
		    }
		}
		
		@Nested
		class KO{
			@DisplayName("Acualizar calidad productos con calidad negativa")
			@ParameterizedTest(name = "Caso {index}: name {0}, sellIn {1}, quality {2} --> updateQuality --> sellIn {3}, quality {4}")
			@CsvSource(value = {"Jamon,20,-5,19,-5",
								"Conjurado,20,-5,19,-5",
								"Aged Brie,20,-5,19,-4",
								"Backstage passes to a TAFKAL80ETC concert,20,-5,19,-4",
								"'Sulfuras, Hand of Ragnaros',20,-5,20,-5"})
			void test01KOUpdateQualityProductosGenerales(String nombre, int sellIn, int qualityIn, int sellInOut, int qualityOut) {
				Item item = new Item(nombre, sellIn, qualityIn);
				gildedRose.items[0] = item;
                gildedRose.updateQuality();
                assertEquals(sellInOut, gildedRose.items[0].sellIn);
                assertEquals(qualityOut, gildedRose.items[0].quality);
			}
		}
	}

}