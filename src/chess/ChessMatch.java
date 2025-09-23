package chess;

import boardgame.Board;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); // CLASSE CHESSMATCH QUE SABE A DIMENSÃO DO TABULEIRO DE XADREZ 
	}
	
	public ChessPiece[][] getPieces() { // MÉTODO PARA RETORNAR UMA MATRIZ DE PEÇAS DE XADREZ CORRESPONDENTES A PARTIDA
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) { // PERCORRENDO AS LINHAS DA MATRIZ
			for (int j=0; j<board.getColumns(); j++) { // PERCORRENDO AS COLUNAS DA MATRIZ
				mat[i][j] = (ChessPiece) board.piece(i, j); // RECEBENDO NA LINHA 'I' COLUNA 'J' RECEBA A PEÇA DE XADREZ NAS POSIÇÕES
			}
		}
		return mat; // RETORNANDO A MINHA MATRIZ MAT (RETORNA A MATRIZ DE PEÇAS DA PARTIDA DE XADREZ)
	}

}
