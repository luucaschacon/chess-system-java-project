package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece { // PEÇA: RAINHA

	public Queen(Board board, Color color) {
		super(board, color);
	} 

	@Override
	public String toString() {
		return "Q"; 
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0); 
		
		// ABOVE (A CIMA)
		p.setValues(position.getRow() -1, position.getColumn()); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setRow(p.getRow() -1); 
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// LEFT (A ESQUERDA)
		p.setValues(position.getRow(), position.getColumn() -1); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// RIGHT (A DIREITA)
		p.setValues(position.getRow(), position.getColumn() +1); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// BELOW (PARA BAIXO)
		p.setValues(position.getRow() +1, position.getColumn()); 
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setRow(p.getRow() + 1); 
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
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
