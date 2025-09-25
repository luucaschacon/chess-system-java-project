package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{ // PEÇA: PEÃO

	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			
			// # SPECIALMOVE EN PASSANT WHITE:
			
			if (position.getRow() == 3) { // TESTANDO SE A POSIÇÃO DA PEÇA É IGUAL A '3'
				Position left = new Position(position.getRow(), position.getColumn() - 1); // CRIANDO VARIAVEL DA POSIÇÃO PARA VERIFICAR SE TEM UM PEÃO DO LADO ESQUERDO E SE É UM OPONENTE VULNERAVEL A TOMAR UM EN PASSANT:
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
					// MOVENDO PARA A POSIÇÃO DA MATRIZ (DIAGONAL ESQUERDA DO PEÃO)
				}
				
				Position right = new Position(position.getRow(), position.getColumn() + 1); // CRIANDO VARIAVEL DA POSIÇÃO PARA VERIFICAR SE TEM UM PEÃO DO LADO DIREITO E SE É UM OPONENTE VULNERAVEL A TOMAR UM EN PASSANT:
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
					// MOVENDO PARA A POSIÇÃO DA MATRIZ (DIAGONAL DIREITA DO PEÃO)
				}
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
			
			// # SPECIALMOVE EN PASSANT BLACK:

			if (position.getRow() == 4) { // TESTANDO SE A POSIÇÃO DA PEÇA É IGUAL A '4'
				Position left = new Position(position.getRow(), position.getColumn() - 1); // CRIANDO VARIAVEL DA POSIÇÃO PARA VERIFICAR SE TEM UM PEÃO DO LADO ESQUERDO E SE É UM OPONENTE VULNERAVEL A TOMAR UM EN PASSANT:
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
					// MOVENDO PARA A POSIÇÃO DA MATRIZ (DIAGONAL ESQUERDA DO PEÃO)
				}

				Position right = new Position(position.getRow(), position.getColumn() + 1); // CRIANDO VARIAVEL DA POSIÇÃO PARA VERIFICAR SE TEM UM PEÃO DO LADO DIREITO E SE É UM OPONENTE VULNERAVEL A TOMAR UM EN PASSANT:
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
					// MOVENDO PARA A POSIÇÃO DA MATRIZ (DIAGONAL DIREITA DO PEÃO)
				}
			}
		}	
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
