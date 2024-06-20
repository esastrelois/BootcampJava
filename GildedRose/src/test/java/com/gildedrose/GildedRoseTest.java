package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.MethodName.class)
class GildedRoseTest {
	Item item;
	GildedRose gildedRose;
	
	@Nested
	@DisplayName("Metodo updateQuality")
	class updateQuality {
		@Nested
		class OK {
			/*
			@Test
			@DisplayName("Acualizar calidad productos generales")
			@ParameterizedTest(name = "Caso {index}: name {0}, sellIn {1}, quality {2} -- updateQuality sellIn {3}, quality {4}")
			@CsvSource(value = {"Jamon,20,10"})
			void test01UpdateQualityProductosGenerales() {
		       
		    }
		    */
			@Test
			@DisplayName("Acualizar calidad productos generales")
			void test01UpdateQualityProductosGenerales() {
				Item item = new Item("Jamon",20,50);
				gildedRose.items[0]=item;
				gildedRose.updateQuality();
				assertEquals(49,gildedRose.items[0].quality);
			}
		}class KO{
			
		}
	}

}
