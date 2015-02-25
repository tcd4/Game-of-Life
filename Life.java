import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



public class Life 
{
	private static boolean[][] grid ;
	private static int col ;
	private static int turns ;
	
	public static void main(String args[])
	{
		setUp() ;	
	}
	
	public static void setUp() 
	{
		int[] firstLine = new int[3] ;
		
		System.out.println("Please enter the file name.") ;
		Scanner fileName = new Scanner(System.in) ;
		Scanner scan ;
		
		try 
		{
			scan = new Scanner(new File(fileName.next())) ;
		} 
		catch (FileNotFoundException e) 
		{
			scan = retry() ;
		}
		
		for (int x = 0 ; x < 3 ; x++)
		{
			try
			{
				firstLine[x] = scan.nextInt() ;
			}
			catch (Exception e)
			{
				System.out.println("The file contains invalid input. Please correct the error and then try again.") ;
				
				System.exit(0) ;
			}
		}
		
		col = firstLine[1] ;
		grid = new boolean[firstLine[0]][col] ;
		turns = firstLine[2] ;
		
		if (!scan.hasNext())
		{
			System.out.println("File contains no living cells. Please add living cells and then try again.") ;
			
			System.exit(0) ;
		}
		
		while (scan.hasNext())
		{
			try
			{
				grid[scan.nextInt() - 1][scan.nextInt() - 1] = true ;
			}
			catch (Exception e)
			{
				System.out.println("The file contains invalid input. Please correct the error and then try again.") ;
				
				System.exit(0) ;
			}
		}
		
		printGrid() ;
		
		if (turns <= 0)
			noTurnLimit() ;
		else
			turnLimit() ;
	}
	
	public static Scanner retry()
	{
		System.out.println("Please enter a valid file name.") ;
		Scanner scan = new Scanner(System.in) ;
		String fileName = scan.next() ;
		
		try 
		{
			scan = new Scanner(new File(fileName)) ;
			return scan ;
		} 
		catch (FileNotFoundException e) 
		{
			return retry() ;
		}
	}
	
	public static void turnLimit()
	{
		if (turns > 0)
		{
			nextTurn() ;
			printGrid() ;
			turns-- ;
			turnLimit() ;
		}
		
		System.out.println("Would you like to try again? (y/n)") ;
		Scanner scan = new Scanner(System.in) ;
		
		if (scan.next().equals("y"))
			setUp() ;
		else
			System.exit(0) ;
	}
	
	public static void noTurnLimit()
	{
		boolean[][] previous = null ;
		
		while (!sameAsPrevious(previous))
		{			
			previous = gridCopy() ;
			
			nextTurn() ;
			printGrid() ;
		}
		
		System.out.println("There are no more changes to the grid.") ;
		
		System.out.println("Would you like to try again? (y/n)") ;
		Scanner scan = new Scanner(System.in) ;
		
		if (scan.next().equals("y"))
			setUp() ;
		else
			System.exit(0) ;
	}
	
	public static void nextTurn()
	{
		boolean[][] temp = gridCopy() ;
		
		for (int y = 0 ; y < grid.length ; y++)
		{			
			for (int x = 0 ; x < col ; x++)
			{
				temp[y][x] = updateCell(y, x) ;
			}
		}
		
		grid = temp ;
	}
	
	public static boolean updateCell(int row, int col)
	{
		int numLiveNeighbors = getNeighbors(row, col) ;

		if (grid[row][col])
		{
			if (numLiveNeighbors == 2 || numLiveNeighbors == 3)
				return true ;
			else
				return false ;
		}
		else if (numLiveNeighbors == 3)
		{
			return true ;
		}
		else
		{
			return false ;
		}
		
	}
	
	public static int getNeighbors(int row, int col)
	{
		int numLiveNeighbors = 0 ;
		int endRow = row + 1 ;
		int endCol = col + 1 ;

		for (int y = row - 1 ; y <= endRow ; y++)
		{
			for (int x = col - 1 ; x <= endCol ; x++)
			{
				if (x == col && y == row)
					continue ;
				
				try
				{
					if (grid[y][x] == true)
						numLiveNeighbors++ ;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{}
			}
		}
		
		return numLiveNeighbors ;
	}
	
	public static void printGrid()
	{
		for (boolean[] row : grid)
		{
			for (boolean cell : row)
			{
				if (cell)
					System.out.print("X ") ;
				else
					System.out.print("_ ") ;
			}
			
			System.out.print("\n") ;
		}
		
		System.out.print("\n") ;
	}
	
	public static boolean[][] gridCopy()
	{
		boolean[][] temp = new boolean[grid.length][grid[0].length];
		
	    for (int x = 0; x < grid.length; x++) 
	    {
	        System.arraycopy(grid[x], 0, temp[x], 0, grid[x].length);
	    }
	    
	    return temp ;
	}
	
	public static boolean sameAsPrevious(boolean[][] previous)
	{
		if (previous == null)
			return false ;
		
		for (int y = 0 ; y < grid.length ; y ++)
		{
			for (int x = 0 ; x < col ; x++)
			{
				if (grid[y][x] != previous[y][x])
					return false ;
			}
		}
		
		return true ;
	}
}
