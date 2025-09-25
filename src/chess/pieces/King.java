package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece { // PEÇA: REI
	
	private ChessMatch chessMatch; // DEPENDENCIA PARA A PARTIDA

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
		// SE A PEÇA 'P' NÃO É NULA E TAMBÉM É UMA PEÇA ADVERSÁRIA (NESSES DOIS CASOS VOU PODER MOVER PARA OUTRA POSIÇÃO)
	}
	
	// MÉTODO PARA TESTAR SE A TORRE ESTÁ APTA PARA O ROOK:
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
		// SE A PEÇA FOR DIFERENTE DE NULO, E TAMBÉM SE A PEÇA É UMA TORRE
		// E TAMBÉM SE A COR DESSA TORRE É IGUAL A COR DO MEU REI, E TAMBÉM SE A CONTA DE MOVIMENTOS É IGUAL A '0'
		// SE ISSO TUDO ACONTECER, SIGNIFICA QUE NESSA POSIÇÃO EU TENHO UMA TORRE, E ELA ESTÁ APTA A JOGADA 'ROOK'
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		// ABOVE (A CIMA)
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// BELOW (ABAIXO)
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// LEFT (A ESQUERDA)
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// RIGHT (A DIREITA)
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// NW (NOROESTE)
		p.setValues(position.getRow() -1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// NE (NORDESTE)
		p.setValues(position.getRow() -1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// SW (SUDOESTE)
		p.setValues(position.getRow() +1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// SE (SUDESTE)
		p.setValues(position.getRow() +1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// #SPECIALMOVE CASTLING - (VERIFICAÇÃO DO MOVIMENTO ESPECIAL):
		
		if (getMoveCount() == 0 && !chessMatch.getCheck()) { // VERIFICANDO SE A CONTAGEM DE MOVIMENTO É IGUAL A '0' E TAMBÉM SE A PARTIDA NÃO ESTÁ EM 'CHECK'
			// #SPECIALMOVE CASTLING KINGSIDE ROOK (ROQUE PEQUENO):
			Position posT1 = new Position(position.getRow(), position.getColumn() +3); 
			// INSTANCIANDO A POSIÇÃO DA TORRE DO REI (A MESMA LINHA DO REI, E A COLUNA DO REI + 3)
			if (testRookCastling(posT1)) {// SE NA POSIÇÃO EXISTE UMA TORRE
				Position p1 = new Position(position.getRow(), position.getColumn() + 1); // TESTANO SE AS DUAS CASAS DO LADO DIREITO DO REI ESTÃO VAZIAS
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat [position.getRow()][position.getColumn() + 2] = true;
				}
					// SE NO TABULEIRO NESSA POSIÇÃO 'P1' FOR IGUAL A NULO 
					// E TAMBÉM NO TABULEIRO A PEÇA NA POSIÇÃO 'P2' TAMBÉM FOR IGUAL A NULO, POSSO FAZER O ROQUE PEQUENO
			}
			
			// #SPECIALMOVE CASTLING QUEENSIDE ROOK (ROQUE GRANDE):
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // -4 COLUNAS A ESQUERDA DO REI
			if (testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1); 
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3); // VERIFICANDO AS 3 CASAS ESTÃO VAZIAS
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat [position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}
