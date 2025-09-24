package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece { // PEÇA: BISPO

	public Bishop(Board board, Color color) {
		super(board, color);
	} 

	@Override
	public String toString() {
		return "B"; 
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0); 
		
		// NW (DIAGONAL NOROESTE)
		p.setValues(position.getRow() -1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setValues(p.getRow() -1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// NE (DIAGONAL NORDESTE
		p.setValues(position.getRow() - 1, position.getColumn() + 1); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  
			mat[p.getRow()][p.getColumn()] = true; 
			p.setValues(p.getRow() -1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// SE (DIAGONAL SUDESTE)
		p.setValues(position.getRow() + 1, position.getColumn() + 1); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {  
			mat[p.getRow()][p.getColumn()] = true; 
			p.setValues(p.getRow() +1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// SW (DIAGONAL SUDOESTE)
		p.setValues(position.getRow() +1, position.getColumn() - 1); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
}
