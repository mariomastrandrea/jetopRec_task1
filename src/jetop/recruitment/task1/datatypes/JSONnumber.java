package jetop.recruitment.task1.datatypes;

/**
 * A concrete implementation of a generic JSON object. It represent a numeric value, 
 * either an integer or a floating point value
 * @author mariomastrandrea
 *
 */
public class JSONnumber extends JSONcomponent
{
	private Double doubleNumber;
	private Long longNumber;
	
	
	public JSONnumber(double number)
	{
		this.doubleNumber = number;
		this.longNumber = null;
	}
	
	public JSONnumber(long number)
	{
		this.longNumber = number;
		this.doubleNumber = null;
	}
	
	public double getDouble() { return this.doubleNumber; }
	public long getLong() { return Math.round(this.longNumber); }
	public double get() { return this.doubleNumber != null ? this.doubleNumber : (double)this.longNumber; }
	
	@Override
	public String print(int indentation) 
	{
		StringBuilder sb = new StringBuilder();
		
		if(this.doubleNumber != null)
			return sb.append(String.format("%f", this.doubleNumber)).toString();
		
		if(this.longNumber != null)
			return sb.append(String.format("%d", this.longNumber)).toString();
		
		return null;
	}

	@Override
	public JSONcomponent removeProperties(String... properties)
	{
		return this;
	}
}
