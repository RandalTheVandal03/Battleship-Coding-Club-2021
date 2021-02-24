
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Random;
public class Battleship2021REWORK {
	
	Random ai = new Random();
	Random VertHor = new Random();
	static Scanner scan = new Scanner(System.in);
	static Scanner scanWord = new Scanner(System.in);
	static Scanner scanChoice = new Scanner(System.in);
	private static int EnemyShips = 0;
	private static int PlayerShips = 0;
	private static int SeasDimX = 10;
	private static int SeasDimY = 10;
	private static int[] TorF = {0,1};
	private static String[][] EnemySeas = new String[SeasDimX][SeasDimY];
	private static String[][] PlayerSeas = new String[SeasDimX][SeasDimY]; 
	private static int[] RandDim = {0, 1, 2, 3, 4};
	private static boolean PlayerTurn = true;
	private static boolean EnemyTurn = false;
	private static int[] EnemyGuess = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static String[][] EnemyGuessesSheet = new String[SeasDimX][SeasDimY];
	private static String[][] PlayerGuessesSheet = new String[SeasDimX][SeasDimY];
	private static int EnemyTiles = 17;
	public static boolean GameDone = false;

	
	public static void main(String[] args) {

		Intro();
		
	}
	
	public static void Intro() {
		out("Welcome to new and improved battleship! Your objective is to sink your opponent's ships before they sink you. Good luck!");
		SetUpSeas();
		
	}
	
	public static void SetUpSeas() {
		
		for(int j = 0; j < EnemySeas.length; j++) {
			for(int x = 0; x < EnemySeas[j].length; x++) {
			EnemySeas[j][x] = "~";  
				
			}			
		}
		
		for(int j = 0; j < EnemyGuessesSheet.length; j++) {
			for(int x = 0; x < EnemyGuessesSheet[j].length; x++) {
			EnemyGuessesSheet[j][x] = "~";  
				
			}			
		}
		
		for(int j = 0; j < PlayerGuessesSheet.length; j++) {
			for(int x = 0; x < PlayerGuessesSheet[j].length; x++) {
			PlayerGuessesSheet[j][x] = "~";  
				
			}			
		}
		
		for(int j = 0; j < SeasDimY; j++) {
			for(int x = 0; x < SeasDimX; x++) {
			PlayerSeas[j][x] = "~"; 
			System.out.print(PlayerSeas[j][x]);
				
			}			
			System.out.println();
		}
		
		
	
		Wait(1000);
		out("");
		out("This is your board, where would you like to populate?");
		Wait(500);
		out("Please note in order to populate your board, list your topmost or leftmost point of your ship (0,0 is top left corner). The formatting should just be 2 integers. (1 2)"
			+ "\nplease count whether your ship will go out of bounds, the game will not work if this happens.");
		Populate();
		
	}
	
	public static void Populate() {
		Wait(500);
		

		switch (PlayerShips) {
		case 0:
			out("You will begin with your carrier (5 spaces)");
			PlayerPopulate(5);
			break;
		case 1:
			out("Now, your battleship (4 spaces)");
			PlayerPopulate(4);
			break;
		case 2:
			out("Now, your Cruiser (3 spaces)");
			PlayerPopulate(3);
			break;
		case 3:
			out("Now, your Submarine (3 spaces)");
			PlayerPopulate(3);
			break;
		case 4:
			out("Now, your Cruiser (2 spaces)");
			PlayerPopulate(2);
			break;
		}
		
		//Wait(1000);
		//out("The enemy board will now be populated...");
		EnemyPopulate();
		
	}
	
	public static void EnemyPopulate() {
		
		switch(EnemyShips) {
		case 0:
			//Carrier
			EnemyPopulation(5);
			break;
		case 1:
			//Battleship
			EnemyPopulation(4);
			break;
		case 2:
			//Cruiser
			EnemyPopulation(3);
			break;
		case 3:
			//Submarine
			EnemyPopulation(3);
			break;
		
		case 4:
			//Destroyer
			EnemyPopulation(2);
			break;
		}
		
		Game();
		
	}
	
	//The actual game itself
	
	public static void Game() {
		
		while(GameDone == false) {
		if(PlayerTurn == true) {
			DebugEnemyBoard();
			out("Here is your updated board.");
			ShowBoard();
			Wait(1000);
			out(" ");			
			out("It is now your turn, please guess a point to strike...");	
			PlayersTurn();
			
		}
		else if(EnemyTurn == true) {
			out("It is now the enemy's turn, they will now guess a point to strike...");
			EnemysTurn();
			
		}
		GameOver();
		}
		
	}
	
	public static void PlayersTurn() {
		
		out("Before you guess a coordinate point, would you like to see your guess chart? (Yes or No)");
		String choice = scanChoice.nextLine();
		if(choice.equalsIgnoreCase("Yes")) {
			ShowGuesses();
			Wait(1000);
		}
		out("Please input your points now...");
		int XGuess = scan.nextInt();
		int YGuess = scan.nextInt();
		
	if(PlayerGuessesSheet[YGuess][XGuess] != "X") {
		 PlayerGuessesSheet[YGuess][XGuess] = "X";
		
		if(EnemySeas[YGuess][XGuess] == "X") { 
			out("You have hit an enemy ship!");
			EnemySeas[YGuess][XGuess] = "O";
			PlayerGuessesSheet[YGuess][XGuess] = "O";
			EnemyTiles--;
		}
		else {
			out("You have missed a ship, you have " + EnemyTiles + " ship tiles left to destroy.");
		}
		
	}
	else {
		out("You have input coordinates you have used in the past, please put in new coordinates.");
		PlayersTurn();
	}
	}
	
	public static void EnemysTurn() {
		Wait(1000);
		 int XGuess = new Random().nextInt(EnemyGuess.length);
		 int YGuess = new Random().nextInt(EnemyGuess.length);
	
	if(EnemyGuessesSheet[YGuess][XGuess] != "X") {
		 EnemyGuessesSheet[YGuess][XGuess] = "X";
		 out("The enemy has guessed these two points: " + XGuess + " " + YGuess);
		 Wait(1000);
		 if(PlayerSeas[YGuess][XGuess] == "X") {
			 out("The enemy has hit a portion of your ship... Did this sink it?");
			 PlayerSeas[YGuess][XGuess] = "O";
			 
			 String SinkOrNah = scanChoice.nextLine();
			 if(SinkOrNah.equalsIgnoreCase("Yes")) {
				 PlayerShips--;
			 }
			 out("You have " + PlayerShips + " ships remaining.");
		 }
		 else {
			 out("The Enemy has missed, your turn!");
			 Wait(3000);
		 }
		 
		 EnemyTurn = false;
		 PlayerTurn = true;
		 Game();
		
		}
	else {
		EnemysTurn();
	}
	}
	
	
	
	public static void GameOver() {
		
		if(EnemyTiles <= 0) {
			out("You have successfully taken control of the seas, good job!");
			GameDone = true;
		}
		else if (PlayerShips <= 0) {
			out("The enemy has taken control of the seas, better luck next time!");
			GameDone = true;
		}
		
	}
	
	
	//Player Ship Population Function

	public static void PlayerPopulate(int ShipType) {
		
		int PlayerXCoord = scan.nextInt();
		int PlayerYCoord = scan.nextInt();
		boolean GoodLength = false;
		
		out("Great, will it be vertical or horizontal?");
		
		String VH = scanWord.nextLine();
		
		if(VH.equalsIgnoreCase("Vertical")) {
			
			 while(GoodLength == false) {
				 if(PlayerSeas[PlayerYCoord][PlayerXCoord] == "X") {
					 out("Ships overlap, please move your ship.");
					 Populate();				 
				 }
				 else {
					 
					 for(int x = 0; x < ShipType; x++) {
						if(EnemySeas[PlayerYCoord+x][PlayerXCoord] == "X") {
							out("Ships overlap, please move your ship.");
							Populate();
						}
					 }
					GoodLength = true; 
				 } 
			 }
			
			PlayerSeas[PlayerYCoord][PlayerXCoord] = "X";
			for(int x = 0; x < ShipType; x++) {
				
					PlayerSeas[PlayerYCoord + x][PlayerXCoord] = "X";
					
			}
			PlayerShips++;
			
		}
		else if (VH.equalsIgnoreCase("Horizontal")) {
			
			 while(GoodLength == false) {
				 if(PlayerSeas[PlayerYCoord][PlayerXCoord] == "X") {
					 out("Ships overlap, please move your ship.");
					 Populate();				 
				 }
				 else {
					 
					 for(int x = 0; x < ShipType; x++) {
						if(EnemySeas[PlayerYCoord][PlayerXCoord+x] == "X") {
							 out("Ships overlap, please move your ship.");
							Populate();
						}
					 }
					GoodLength = true; 
				 } 
			 }
			
			
			PlayerSeas[PlayerYCoord][PlayerXCoord] = "X";
			for(int x = 0; x < ShipType; x++) {
				
				PlayerSeas[PlayerYCoord][PlayerXCoord + x] = "X";
				
			}
			PlayerShips++;
		}
		else {
			out("Not a proper input, please try again.");
			Populate();
		}
		ShowBoard();
		Populate();
	}
	

	//Enemy Population
	
	public static void EnemyPopulation(int EnemyShipType) {
		//1 is Vert
		//0 is Hor
		boolean GoodLength = false;
		
		
		 int EnemyX = new Random().nextInt(RandDim.length);
		 int EnemyY = new Random().nextInt(RandDim.length);
		 int VertHor = new Random().nextInt(TorF.length);
		
		 
		if(VertHor==1) {
			
			 while(GoodLength == false) {
				 if(EnemySeas[EnemyY][EnemyX] == "X") {
					 EnemyPopulate();				 
				 }
				 else {
					 for(int x = 0; x < EnemyShipType; x++) {
						if(EnemySeas[EnemyY+x][EnemyX] == "X") {
							EnemyPopulate();
						}
					 }
					GoodLength = true; 
				 } 
			 }
			
			EnemySeas[EnemyY][EnemyX] = "X";
			for(int x = 0; x < EnemyShipType; x++) {
	
					EnemySeas[EnemyY + x][EnemyX] = "X";
					
			}
			EnemyShips++;
			
		}
		else if (VertHor == 0) {
			
			 while(GoodLength == false) {
				 if(EnemySeas[EnemyY][EnemyX] == "X") {
					 EnemyPopulate();				 
				 }
				 else {
					 for(int x = 0; x < EnemyShipType; x++) {
						if(EnemySeas[EnemyY][EnemyX+x] == "X") {
							EnemyPopulate();
						}
					 }
					GoodLength = true; 
				 } 
			 }
			
			EnemySeas[EnemyY][EnemyX] = "X";
			for(int x = 0; x < EnemyShipType; x++) {
				EnemySeas[EnemyY][EnemyX + x] = "X";
			}
			EnemyShips++;
		}
		out(" ");
		EnemyPopulate();
	}
	
	
	//Quality of Life Functions
	
	
	public static void out(String s) {
		System.out.println(s);
	}
	
	
			//Wait must be in MS
	public static void Wait(int time) {
		try
		{
		    Thread.sleep(time);
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
	}
	
	public static void ShowBoard() {

		for(int j = 0; j < SeasDimY; j++) {
			for(int x = 0; x < SeasDimX; x++) {
			System.out.print(PlayerSeas[j][x]);
				
			}			
			System.out.println();
		}
		
	}
	
	public static void ShowGuesses() {
		
		for(int j = 0; j < SeasDimY; j++) {
			for(int x = 0; x < SeasDimX; x++) {
			System.out.print(PlayerGuessesSheet[j][x]);
				
			}			
			System.out.println();
		}
		
	}
	
	
	public static void DebugEnemyBoard() {

		for(int j = 0; j < SeasDimY; j++) {
			for(int x = 0; x < SeasDimX; x++) {
			System.out.print(EnemySeas[j][x]);
				
			}			
			System.out.println();
		}
		
	}

}
