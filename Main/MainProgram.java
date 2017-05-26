package Main;

import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import heroes_and_monsters.*;

public class MainProgram {

	public static void main(String [] args){
		// Hero
		Weapon geweer = new Weapon(2, Unit.KG, 70);
		Weapon zwaard = new Weapon(1063, Unit.GRAM, 42);
		Armor harnas = new Armor(2477, 84, 752, 73, Unit.KG);
		Backpack rugzak = new Backpack(132, 261, 1, Unit.KG);
		Purse geldbeurs = new Purse(222, Unit.GRAM, 5000);
		Ducat geld = new Ducat(47);
		geldbeurs.addToStorage(geld);
		ArrayList<Object> anchorObjects = new ArrayList<Object>(Arrays.asList(geweer, zwaard, rugzak, harnas, geldbeurs));
		Hero eend = new Hero("Eend", new BigDecimal("264.15") , 137, anchorObjects);
		int totaleWaarde1 = 0;
		Iterator<String> iterator1 = eend.getAnchors().keySet().iterator();
		while (iterator1.hasNext()){
			String anchor = iterator1.next();
			Object voorwerp = eend.getAnchors().get(anchor);
			if (voorwerp != null){
				if (voorwerp instanceof Ownable){
					totaleWaarde1 += ((Ownable) voorwerp).getValue();
				}
				if (voorwerp instanceof Ducat){
					totaleWaarde1 += ((Ducat) voorwerp).getValue();
				}
			}
		}
		System.out.println("Waarde bezittingen Eend: " + totaleWaarde1);
		
		// Monster
		Weapon mes = new Weapon(361, Unit.GRAM, 28);
		Weapon pijlEnBoog = new Weapon(20, Unit.KG, 49);
		Armor harnas2 = new Armor(65, 62, 456, 68, Unit.KG);
		Backpack rugzak2 = new Backpack(98, 541, 2, Unit.KG);
		rugzak2.addToStorage(pijlEnBoog);
		Ducat eenDukaat = Ducat.ONE_DUCAT;
		ArrayList<String> anchors = new ArrayList<String>(Arrays.asList("Hand 1", "Hand 2", "Hand 3", "Hand 4", "Tail", "Horns"));
		ArrayList<Object> anchorObjects2 = new ArrayList<Object>(Arrays.asList(mes, harnas2, rugzak2, eenDukaat));
		Monster boemkool = new Monster("Boemkool", new BigDecimal(312.48), 698, anchors, anchorObjects2, 35, 72);
		int totaleWaarde2 = 0;
		Iterator<String> iterator2 = boemkool.getAnchors().keySet().iterator();
		while (iterator2.hasNext()){
			String anchor = iterator2.next();
			Object voorwerp = boemkool.getAnchors().get(anchor);
			if (voorwerp != null){
				if (voorwerp instanceof Ownable){
					totaleWaarde2 += ((Ownable) voorwerp).getValue();
				}
				if (voorwerp instanceof Ducat){
					totaleWaarde2 += ((Ducat) voorwerp).getValue();
				}
			}
		}
		System.out.println("Waarde bezittingen Boemkool: " + totaleWaarde2);
		
		// Gevecht
		int random = ThreadLocalRandom.current().nextInt(1, 3);
		while (!eend.getKilled() && !boemkool.getKilled()){
			if (random == 1){
				eend.hit(boemkool);
				random = 2;
			}
			else {
				boemkool.hit(eend);
				random = 1;
			}
		}
		
		// Winnaar en nieuwe waarde bezittingen
		Creature winnaar;
		if (!eend.getKilled()){
			winnaar = eend;
		}
		else {
			winnaar = boemkool;
		}
		System.out.println("De winnaar is " + winnaar.getName() + "!");
		int totaleWaarde3 = 0;
		Iterator<String> iterator3 = winnaar.getAnchors().keySet().iterator();
		while (iterator3.hasNext()){
			String anchor = iterator3.next();
			Object voorwerp = winnaar.getAnchors().get(anchor);
			if (voorwerp != null){
				if (voorwerp instanceof Ownable){
					totaleWaarde3 += ((Ownable) voorwerp).getValue();
				}
				if (voorwerp instanceof Ducat){
					totaleWaarde3 += ((Ducat) voorwerp).getValue();
				}
			}
		}
		System.out.println("Nieuwe waarde bezittingen " + winnaar.getName() + ": " + totaleWaarde3);
		
	}
}
