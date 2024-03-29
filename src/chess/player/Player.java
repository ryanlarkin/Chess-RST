package chess.player;

import chess.board.Board;
import chess.board.Coordinates;
import chess.help.Help;
import chess.piece.Piece;
import chess.piece.Team;
import chess.piece.pieces.Pawn;

/**
 * A player that can take a turn in chess
 */
public abstract class Player
{
	/**
	 * The team of the player
	 */
	private final Team team;

	/**
	 * The board that the player is on
	 */
	private final Board board;

	/**
	 * The help that can be requested by the player
	 */
	private final Help help;

	/**
	 * The opponent of this player
	 */
	private Player opponent;

	/**
	 * Does this player want to draw?
	 */
	public boolean wantsToDraw = false;

	/**
	 * Does this player want to resign?
	 */
	public boolean resign = false;
	/**
	 * The player's name
	 */
	private final String name;

	/**
	 * Creates an instance of Player with a set team and board to use.
	 * 
	 * pre: team The team of this player.
	 * pre: board The board this player will use.
	 * pre: name The name of the player.
	 */
	public Player(Team team, Board board, Help help, Player opponent, String name)
	{
		this.team = team;
		this.board = board;
		this.help = help;
		this.opponent = opponent;
		this.name = name;
	}

	/**
	 * Gets the current team of this player.
	 * 
	 * post: The current team of this player
	 */
	public Team getTeam()
	{
		return team;
	}

	/**
	 * Gets the current board used by this player.
	 * 
	 * post: The board used by this player
	 */
	public Board getBoard()
	{
		return board;
	}

	/**
	 * Gets the name of the player
	 * 
	 * post: The player's name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Takes the player's turn. Asks the player to select a piece, select a destination for that piece, and if the
	 * destination is valid, then moves the piece to that destination.
	 */
	public void takeTurn()
	{
		// Start not done
		boolean done = false;

		// Take turn
		do
		{
			// Asks the user if they want to view help, move a piece.
			switch (turnInit())
			{
			case "help":
				done = false;
				help.helpPrompt();
				break;
			case "play":
				// Gets the location of the piece that the player wants to move
				Coordinates start = selectPiece();

				// Gets the location that the player want to move the piece to
				Coordinates end = selectDestination(board.getPiece(start));

				// Only valid pieces should be selected in the first place, but just in case
				done = board.movePiece(start, end);

				// Tell the user that their move was wrong
				if (!done)
				{
					invalidMove();
				}

				board.showBoard();
				break;
			case "resign":
				resign = true;
				done = true;
				break;
			case "draw":
				wantsToDraw = true;
				break;
			}
		}
		// Continue to take turn until done
		while (!done);
	}

	/**
	 * Asks the user what they want to do on the inital part of their turn.
	 */
	public abstract String turnInit();

	/**
	 * Asks a user if they want to promote a specific pawn. User then chooses between 4 new types, and the game replaces
	 * the pawn with a new piece of the user's selected type.
	 */
	public abstract void pawnPromotion(Pawn pawn);

	/**
	 * Asks user to select a piece on the board that is of the same team as their own.
	 */
	public abstract Coordinates selectPiece();

	/**
	 * Gets the location to move the piece to
	 * 
	 * pre: selected The piece to be moved
	 * post: The new location
	 */
	public abstract Coordinates selectDestination(Piece selected);

	/**
	 * Tells the player that their move was invalid
	 */
	public abstract void invalidMove();

	/**
	 * Finds out whether the player wants to offer a draw
	 * 
	 * post: Whether to offer draw
	 */
	public boolean offerDraw() {
		return wantsToDraw;
	}

	/**
	 * Checks if the opponent wants to draw
	 */
	public boolean checkDrawOffer() {
		if (board.canOfferDraw(getTeam())) {
			wantsToDraw = true;
		}
		return wantsToDraw;
	}

	/**
	 * Set a new opponent for this player.
	 * 
	 * pre: opponent The opponent of this player.
	 */
	public void setOpponent(Player opponent)
	{
		this.opponent = opponent;
	}

	/**
	 * Get the opponent of this player.
	 * 
	 * post: The opponent of this player
	 */
	public Player getOpponent()
	{
		return opponent;
	}
	
	/**
	 * Does this player want to resign
	 * post: Whether the player wants to resign
	 */
	public boolean resign() {
		return resign;
	}
}
