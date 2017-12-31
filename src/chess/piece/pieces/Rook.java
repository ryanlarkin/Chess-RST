package chess.piece.pieces;

import chess.board.Coordinates;
import chess.piece.Piece;
import chess.piece.PieceType;
import chess.piece.Team;

public class Rook extends Piece
{
	public Rook(PieceType pieceType, Team team) {
		super(pieceType, team);
	}

	@Override
	protected boolean canMove(Coordinates newCoords)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void doMove(Coordinates newCoords)
	{
		// TODO Auto-generated method stub
		
	}
}
