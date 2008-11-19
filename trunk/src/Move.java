public class Move {

	private int destR;
	private int destC;
	private int srcR;
	private int srcC;

//Constructors
	public Move()
	{
		srcR = -1;
		srcC = -1;
		destR = -1;
		destC = -1;
	}
	
	public Move(int srcR, int srcC, int destR, int destC)
	{
		this.srcR = srcR;
		this.srcC = srcC;
		this.destR = destR;
		this.destC = destC;
	}
	
	public Move(Move m)
	{
		this(m.getSrcR(),m.getSrcC(),m.getDestR(),m.getDestC());
	}
	
//Accessors
	public int getSrcR()
	{
		return srcR;
	}
	
	public int getSrcC()
	{
		return srcC;
	}
	
	public int getDestR()
	{
		return destR;
	}
	
	public int getDestC()
	{
		return destC;
	}
	
//Mutators
	public void setSrcR(int srcR)
	{
		this.srcR = srcR;
	}
	
	public void setSrcC(int srcC)
	{
		this.srcC = srcC;
	}
	
	public void setDestR(int r)
	{
		destR = r;
	}
	
	public void setDestC(int c)
	{
		destC = c;
	}
	
//Methods
	
	public boolean isSrcSet()
	{
		if (srcR >-1 && srcC >-1)
			return true;
		else
			return false;
	}
		
	public boolean isDestSet()
	{
		if (destR >-1 && destC >-1)
			return true;
		else
			return false;
	}
	
	public boolean compareTo(Move m)
	{
		if ( m.getSrcR() != srcR)
			return false;
		else if (m.getSrcC() != srcC)
			return false;
		else if (m.getDestR() != destR)
			return false;
		else if (m.getDestC() != destC)
			return false;
		else
			return true;	
	}
	
}
