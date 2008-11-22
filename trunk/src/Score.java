
public class Score {
	private int protect;
	private int kill;
	private int possibleKill;
	private int die;
	private int position;
	private int escape;

//Constructor
	public Score()
	{
		this.protect = 0;
		this.kill = 0;
		this.possibleKill = 0;
		this.die = 0;
		this.position = 0;
		this.escape = 0;
	}
	
	public Score(int protect,int kill,int possibleKill,int die,int position,int escape)
	{
		this.protect = protect;
		this.kill = kill;
		this.possibleKill = possibleKill;
		this.die = die;
		this.position = position;
		this.escape = escape;
	}
	
	
//Mutator	
	public void setProtect(int p)
	{
		protect = p;
	}
	
	public void setKill(int k)
	{
		kill = k;
	}
	
	public void setPossibleKill(int k)
	{
		possibleKill = k;
	}
	
	public void setDie(int d)
	{
		die = d;
	}
	
	public void setPosition(int p)
	{
		position = p;
	}
	
	public void setEscape(int e)
	{
		escape = e;
	}
	
//Accessor
	public int getProtect()
	{
		return protect;
	}
	
	public int getKill()
	{
		return kill;
	}
	
	public int  getPossibleKill()
	{
		return possibleKill;
	}
	
	public int getDie()
	{
		return die;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public int getEscape()
	{
		return escape;
	}
	
//Methods	
	public void add(Score s)
	{
		protect += s.getProtect();
		kill += s.getKill();
		die += s.getDie();
		position += s.getPosition();
		escape +=s.getEscape();
	}
	
	public boolean isBetter(Score s, AI.Strategy x)
	{
		/*
		if (x = AI.strategy.aggresive) //kill
		else if (x = AI.strategy.defensive) //protect
		else if (x = AI.strategy.safe) //die
		else if (x = AI.strategy.suicide) 
	*/
		return false;
	}
	
}
