package heroes_and_monsters;

public abstract class Storage extends Ownable implements Capacity {

	protected Storage(){
		
	}
	
	protected abstract void setIdentification(long identification);
}

