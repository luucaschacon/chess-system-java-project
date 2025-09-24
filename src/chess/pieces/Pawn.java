package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0); 
		
		// MOVIMENTOS PARA O PEÃO DE COR BRANCA:
		
		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() -1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				// SE A POSIÇÃO DE UMA LINHA ACIMA DELE EXISTIR E TIVER VAZIA, ELE PODE MOVER PARA LÁ
			}
			
			// SE FOR A PRIMEIRA JOGADA DO PEÃO, ELE PODE ANDAR DUAS CASAS PARA CIMA (SE ESTIVER LIVRE)
			p.setValues(position.getRow() -2, position.getColumn());
			Position p2 = new Position(position.getRow() -1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			// MOVENDO PARA A DIAGONAL ESQUERDA
			p.setValues(position.getRow() -1, position.getColumn() -1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // TESTANDO SE A POSIÇÃO EXISTE, E SE TEM ADVERSÁRIO LÁ
				mat[p.getRow()][p.getColumn()] = true;
				// SE A POSIÇÃO DE UMA LINHA ACIMA DELE EXISTIR E TIVER VAZIA, ELE PODE MOVER PARA LÁ
			}
			
			// MOVENDO PARA A DIAGONAL DIREITA
			p.setValues(position.getRow() -1, position.getColumn() +1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // TESTANDO SE A POSIÇÃO EXISTE, E SE TEM ADVERSÁRIO LÁ
				mat[p.getRow()][p.getColumn()] = true;
				// SE A POSIÇÃO DE UMA LINHA ACIMA DELE EXISTIR E TIVER VAZIA, ELE PODE MOVER PARA LÁ
			}
		}
		else { // SE Ñ É PEÇA BRANCA, AGORA É UMA PEÇA PRETA:
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() +1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}	
			p.setValues(position.getRow() +1, position.getColumn() -1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() +1, position.getColumn() +1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { 
				mat[p.getRow()][p.getColumn()] = true;
			}
		}	
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
