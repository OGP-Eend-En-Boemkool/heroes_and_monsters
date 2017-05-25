package Main;

import java.math.*;
import java.util.*;

import heroes_and_monsters.*;

public class MainProgram {

	public static void main(String [] args){
		// Hero
		Weapon geweer = new Weapon(2, Unit.KG, 70);
		Weapon zwaard = new Weapon(1063, Unit.GRAM, 42);
		Armor harnas = new Armor(2477, 84, 752, 73, Unit.KG);
		Backpack rugzak = new Backpack(132, 261, 1, Unit.KG);
		Purse geldbeurs = new Purse(222, Unit.GRAM, 7520);
		Ducat geld = new Ducat(47);
		geldbeurs.addToStorage(geld);
		ArrayList<Object> anchorObjects = new ArrayList<Object>(Arrays.asList(geweer, zwaard, rugzak, harnas, geldbeurs));
		Hero eend = new Hero("Eend", new BigDecimal("264.15") , 137, anchorObjects);
		int totaleWaarde = 0;
		Iterator iterator = eend.getAnchors().keySet().iterator();
		while (iterator.hasNext()){
			Object anchor = iterator.next();
			Object voorwerp = eend.getAnchors().get(anchor);
			if (voorwerp != null){
				if (voorwerp instanceof Ownable){
					totaleWaarde += ((Ownable) voorwerp).getValue();
				}
				if (voorwerp instanceof Ducat){
					totaleWaarde += ((Ducat) voorwerp).getValue();
				}
			}
		}
		System.out.println("Waarde bezittingen eend: " + totaleWaarde);
		
		// Monster
		Weapon mes = new Weapon(361, Unit.GRAM, 28);
		Weapon pijlEnBoog = new Weapon(20, Unit.KG, 49);
		Armor harnas2 = new Armor(65, 62, 456, 68, Unit.KG);
		Backpack rugzak2 = new Backpack(98, 541, 2, Unit.KG);
		rugzak2.addToStorage(pijlEnBoog);
		Ducat eenDukaat = Ducat.ONE_DUCAT;
		ArrayList<String> anchors = new ArrayList<String>(Arrays.asList("Hand 1", "Hand 2", "Hand 3", "Hand 4", "Tail", "Horns"));
		ArrayList<Object> anchorObjects2 = new ArrayList<Object>(Arrays.asList(mes, harnas2, rugzak2, eenDukaat));
		Monster boemkool = new Monster("Boemkool", new BigDecimal(312.48), 101, anchors, anchorObjects2, 35, 72);
		System.out.println(1);
	}
}
