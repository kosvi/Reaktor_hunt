import java.util.Scanner;
import ui.Cli;
import logic.Grid;
import logic.Pathfinder;

public class Main {
	public static void main(String[] args) {
		System.out.println("Reaktor Puzzle 3 - rewrite");
		Scanner reader = new Scanner(System.in);
		Grid grid = new Grid();
		Pathfinder pathfinder = new Pathfinder();
		Cli cli = new Cli(reader, grid, pathfinder);
		cli.start();
	}
}
