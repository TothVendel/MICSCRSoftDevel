package item;

public class NumberPoints {
	private int numberPoints;
	private long id;
	private String name;
	
	/**
    * Used to return the name in string format
    *
    * @return name
    *         returns the name
    * 
    */
	public String toString() {
		return name;
	}
    
    /**
    * Gets number points
    *
    * @return numberPoints
    *         returns the number points
    * 
    */
	public int getNumberPoints() {
		return numberPoints;
	}
    
    /**
    * Sets number points
    *
    * @param numberPoints
    * 
    */
	public void setNumberPoints(int numberPoints) {
		this.numberPoints = numberPoints;
	}
    
    /**
    * Gets name
    *
    * @return name
    *         returns the name
    * 
    */
	public String getName() {
		return name;
	}
    
    /**
    * Sets name
    *
    * @param name
    * 
    */
	public void setName(String name) {
		this.name = name;
	}

    /**
    * Gets id
    *
    * @return id
    *         returns the id
    * 
    */
	public long getId() {
		return id;
	}
    
    /**
    * Sets id
    *
    * @param id
    * 
    */
	public void setId(long id) {
		this.id = id;
	}
	
}
