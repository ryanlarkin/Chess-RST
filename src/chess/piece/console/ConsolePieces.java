package chess.piece.console;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import chess.piece.PieceType;
import chess.piece.Team;

/**
 * The String representation of each piece
 */
public enum ConsolePieces {
	
	/**
	 * Use Unicode pieces that may no be supported
	 */
	UTF8(1, ofEntries(
		entry(PieceType.BISHOP,	ofEntries(	entry(Team.BLACK, "♝"), 
											entry(Team.WHITE, "♗"))),
		entry(PieceType.KING,	ofEntries(	entry(Team.BLACK, "♚"), 
											entry(Team.WHITE, "♔"))),
		entry(PieceType.KNIGHT,	ofEntries(	entry(Team.BLACK, "♞"), 
											entry(Team.WHITE, "♘"))),
		entry(PieceType.PAWN,	ofEntries(	entry(Team.BLACK, "♟"), 
											entry(Team.WHITE, "♙"))),
		entry(PieceType.ROOK,	ofEntries(	entry(Team.BLACK, "♜"),
											entry(Team.WHITE, "♖"))),
		entry(PieceType.QUEEN,	ofEntries(	entry(Team.BLACK, "♛"),
											entry(Team.WHITE, "♕"))),
		entry(PieceType.EMPTY,	ofEntries(	entry(Team.NONE, " ")))  
	)),
	
	/**
	 * Use ASCII pieces that are supported anywhere
	 */
	ASCII(2, ofEntries(
		entry(PieceType.BISHOP,	ofEntries(	entry(Team.BLACK, "Bb"), 
											entry(Team.WHITE, "Bw"))),
		entry(PieceType.KING,	ofEntries(	entry(Team.BLACK, "Kb"), 
											entry(Team.WHITE, "Kw"))),
		entry(PieceType.KNIGHT,	ofEntries(	entry(Team.BLACK, "Nb"), 
											entry(Team.WHITE, "Nw"))),
		entry(PieceType.PAWN,	ofEntries(	entry(Team.BLACK, "Pb"), 
											entry(Team.WHITE, "Pw"))),
		entry(PieceType.ROOK,	ofEntries(	entry(Team.BLACK, "Rb"),
											entry(Team.WHITE, "Rw"))),
		entry(PieceType.QUEEN,	ofEntries(	entry(Team.BLACK, "Qb"),
											entry(Team.WHITE, "Qw"))),
		entry(PieceType.EMPTY,	ofEntries(	entry(Team.NONE,  "  ")))
	));
	
	/**
	 * The number of characters in each piece
	 */
	public final int length;
	
	/**
	 * Each piece and their String representation
	 */
	public final Map<PieceType, Map<Team, String>> pieces;
	
	/**
	 * Creates the holder for all of the console pieces
	 * 
	 * pre: length The length of all Strings
	 * pre: pieces The map of pieces and Strings
	 */
	private ConsolePieces(int length, Map<PieceType, Map<Team, String>> pieces) {
		this.length = length;
		this.pieces = safeMap(length, pieces);
	}
	
	/**
	 * Makes an unmodifiable map with a String with a certain length from another map
	 * 
	 * pre: length The length that each String must be
	 * pre: pieces The unsafe map
	 * post: An unmodifiable map with Strings of only the specified length
	 */
	private static Map<PieceType, Map<Team, String>> safeMap(int length, Map<PieceType, Map<Team, String>> pieces) {
		// Copy pieces to different Map so they don't edit the originals
		 Map<PieceType, Map<Team, String>> temp = new HashMap<>(pieces);
		 
		 // Set each String to the correct length
		 for(Map.Entry<PieceType, Map<Team, String>> piece : temp.entrySet()) {
			 // Copy pieces to different Map so they don't edit the originals
			 Map<Team, String> tempPiece = new HashMap<>(piece.getValue());
			 
			 // Set each String to the correct length
			 for(Map.Entry<Team, String> team : tempPiece.entrySet()) {	 
				 tempPiece.put(team.getKey(), cutToLength(length, team.getValue()));
			 }
			 
			 temp.put(piece.getKey(), Collections.unmodifiableMap(tempPiece));
		}
		 
		 // Return an unmodifiable version of the map
		 return Collections.unmodifiableMap(temp);
	}
	
	/**
	 * Makes the String the correct length
	 * pre: length The length of the String
	 * pre: s The String to change
	 * post: The String made the correct length
	 */
	private static String cutToLength(int length, String s) {
		// Figure out how many spaces must be added to make it the correct length
		int difference = s.length() - length;
		
		// String is too short
		if(difference < 0) {
			// Add the number of spaces that are required
			for(int i = 0; i < Math.abs(difference); i++) {
				s += " ";
			}
		}
		// String is too long
		if(difference > 0) {
			// Cut the String down
			s = s.substring(0, length);
		}
		
		// Return the correctly sized String
		return s;
	}
	/**
	 * Get the String representation of a piece
	 * 
	 * pre: piece The piece to get
	 * post: The String representation of the piece
	 */
	public String get(PieceType pieceType, Team team) {
		return pieces.get(pieceType).get(team);
	}
	
	/**
	 * Get the String representation of a piece or another value if it can't be found
	 * 
	 * pre: piece The piece to get
	 * pre: defaultValue The value to return if the key can't be found
	 * post: The String representation of the piece or default
	 */
	public String getOrDefault(PieceType pieceType, Team team, String defaultValue) {
		Map<Team, String> map = pieces.get(pieceType);
		
		if(map != null) {
			String value = map.get(team);
			
			if(value != null) {
				return value;
			}
		}
		
		return defaultValue;
	}
	
	/**
	 * Creates a map from an array of entries
	 * 
	 * pre: entries The entries to make a map of
	 * post: The map
	 */
	@SafeVarargs
	private static <K, V> Map<K, V> ofEntries(Map.Entry<K, V>...entries) {
		// Create a new map with size for each entry
		Map<K, V> map = new HashMap<>(entries.length);
		
		// Get each entry
		for(Map.Entry<K, V> entry : entries) {
			// Add the entry to the map
			map.put(entry.getKey(), entry.getValue());
		}
		
		// Return the map
		return map;
	}
	
	/**
	 * Creates a new Map entry
	 * 
	 * pre: key The key of the entry
	 * pre: value The value of the entry
	 * post: The entry
	 */
	private static <K, V> Entry<K, V> entry(final K key, final V value) {
		// Create a new Map entry that holds the key and value
		return new Map.Entry<K, V>() {
			    @Override
			    public K getKey() {
			        return key;
			    }
			    
			    @Override
			    public V getValue() {
			        return value;
			    }

			    @Override
			    public V setValue(V value) {
			        throw new UnsupportedOperationException("not supported");
			    }
		};
	}
}
