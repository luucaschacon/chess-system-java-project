package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); // CLASSE CHESSMATCH QUE SABE A DIMENSÃO DO TABULEIRO DE XADREZ 
		initialSetup(); // CHAMANDO O MÉTODO 'INITIAL SETUP' AQUI NO CONSTRUTOR DA PARTIDA
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
	
	// MÉTODO PARA RECEBER AS COORDENADAS DO XADREZ:
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // O '.TOPOSITION' CONVERTE PARA POSIÇÃO DE MATRIZ
	}
	
	// MÉTODO PARA INICIAR A PARTIDA DE XADREZ, COLOCANDO AS PEÇAS NO TABULEIRO:
	
	private void initialSetup() {
		
		//placeNewPiece('b', 6, new Rook(board, Color.WHITE)); // (ROOK RECEBENDO O TABULEIRO E A COR) DEPOIS A POSIÇÃO
		//placeNewPiece('e', 8, new King(board, Color.BLACK));
		//placeNewPiece('e', 1, new King(board, Color.WHITE));
		
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
