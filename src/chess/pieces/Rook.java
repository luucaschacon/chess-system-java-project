package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece { // PEÇA: TORRE

	public Rook(Board board, Color color) {
		super(board, color);
	} 

	@Override
	public String toString() {
		return "R"; // CONVERTENDO A TORRE PARA UMA STRING 'R' (APENAS UMA LETRA, POIS ELA VAI ENTRAR A LETRA NO TABULEIRO)
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0); // POSIÇÃO AUXILIAR COMEÇANDO COM UM VALOR INICIAL
		
		// ABOVE (A CIMA)
		p.setValues(position.getRow() -1, position.getColumn()); // O '-1' É A LINHA ACIMA DA MINHA PEÇA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSIÇÃO 'P' EXISTIR E N TIVER UMA PEÇA LÁ A POSIÇÃO É VERDADEIRA
			mat[p.getRow()][p.getColumn()] = true; // MARCAÇÃO DE VERDADEIRO NA MATRIZ ONDE A PEÇA PODE SE MOVER
			p.setRow(p.getRow() -1); 
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			// SE TEM UM OPONENTE NA POSIÇÃO ACIMA, TAMBÉM MARCA COMO TRUE
		}
		
		// LEFT (A ESQUERDA)
		p.setValues(position.getRow(), position.getColumn() -1); // O '-1' É A COLUNA A ESQUERDA DA MINHA PEÇA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSIÇÃO 'P' EXISTIR E N TIVER UMA PEÇA LÁ A POSIÇÃO É VERDADEIRA
			mat[p.getRow()][p.getColumn()] = true; // MARCAÇÃO DE VERDADEIRO NA MATRIZ ONDE A PEÇA PODE SE MOVER
			p.setColumn(p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			// SE TEM UM OPONENTE NA POSIÇÃO ACIMA, TAMBÉM MARCA COMO TRUE
		}
		
		// RIGHT (A DIREITA)
		p.setValues(position.getRow(), position.getColumn() +1); // O '+1' É A COLUNA A DIREITA DA MINHA PEÇA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSIÇÃO 'P' EXISTIR E N TIVER UMA PEÇA LÁ A POSIÇÃO É VERDADEIRA
			mat[p.getRow()][p.getColumn()] = true; // MARCAÇÃO DE VERDADEIRO NA MATRIZ ONDE A PEÇA PODE SE MOVER
			p.setColumn(p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			// SE TEM UM OPONENTE NA POSIÇÃO ACIMA, TAMBÉM MARCA COMO TRUE
		}
		
		// BELOW (PARA BAIXO)
		p.setValues(position.getRow() +1, position.getColumn()); // O '+1' É A LINHA ABAIXO DA MINHA PEÇA
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // ENQUANTO A POSIÇÃO 'P' EXISTIR E N TIVER UMA PEÇA LÁ A POSIÇÃO É VERDADEIRA
			mat[p.getRow()][p.getColumn()] = true; // MARCAÇÃO DE VERDADEIRO NA MATRIZ ONDE A PEÇA PODE SE MOVER
			p.setRow(p.getRow() + 1); 
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			// SE TEM UM OPONENTE NA POSIÇÃO ACIMA, TAMBÉM MARCA COMO TRUE
		}
		
		return mat;
	}
}
