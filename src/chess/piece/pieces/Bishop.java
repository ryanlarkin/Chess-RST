package chess.piece.pieces;

import chess.board.Coordinates;
import chess.piece.Piece;
import chess.piece.PieceType;
import chess.piece.Team;

public class Bishop extends Piece
{
	private Team oppositeTeam;

	public Bishop(PieceType pieceType, Team team)
	{
		super(pieceType, team);
	}

	@Override
	public boolean canMove(Coordinates newCoords)
	{
		//By default the move invalid
		boolean valid = false;
		//Gets difference between new coordinates and old coordinates
		int xDifference = newCoords.getX() - getCoords().getX();
		int yDifference = newCoords.getY() - getCoords().getY();
		//Gets absolute value of xDifference and yDifference
		int absYDifference = Math.abs(yDifference);
		int absXDifference = Math.abs(xDifference);
		//Variable to store the team of the player
		Team sameTeam;

		// If it is the black team's turn
		if (getTeam().equals(Team.BLACK))
		{
			// The same team is the black team
			sameTeam = Team.BLACK;
		} else
		// If it is the white team's turn
		{
			// The same team is the white team
			sameTeam = Team.WHITE;
		}

		// If the new coordinate is not diagonal from the current location
		if (absXDifference - absYDifference != 0 || xDifference == 0 || yDifference == 0)
		{
			// Return false
			return false;
		} else
		{
			//Moves 1 tile towards the final destination
			for (int i = xDifference / absXDifference, j = yDifference / absYDifference; 
					i != xDifference + absXDifference / xDifference; 
					i += xDifference / absXDifference, j += yDifference / absYDifference)
			{
				//If there is a piece in the way and it is not the final location
				if (i != xDifference && j != yDifference
						&& !getBoard().getPiece(getCoords().add(i, j)).getTeam().equals(Team.NONE))
				{
					//Return false
					return false;
					//If it is checking the final location
				} else if (i == xDifference && j == yDifference)
				{
					//If there is not a piece from the same team
					if (!getBoard().getPiece(newCoords).getTeam().equals(sameTeam))
					{
						//Returns true
						return true;
					}
					else
					{
						//Returns false
						return false;
					}
				}

			}
		}

		//Returns valid
		return valid;
	}

	@Override
	protected void doMove(Coordinates newCoords)
	{
		// If the piece is on the black team
		if (getTeam().equals(Team.BLACK))
		{
			// The white team is the enemy team
			oppositeTeam = Team.WHITE;
		}
		// Otherwise...
		else
		{
			// The black team is the enemy team
			oppositeTeam = Team.BLACK;
		}

		// If the new location is on the opposite team
		if (getBoard().getPiece(newCoords).getTeam().equals(oppositeTeam))
		{
			// Captures the designated piece
			getBoard().capture(newCoords);
		}
		
		setCoords(newCoords);
	}
}
