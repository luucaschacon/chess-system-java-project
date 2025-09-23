package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	
	// OPERAÇÃO PARA MOVER A PEÇA:
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { // RECEBENDO A POSIÇÃO DE ORIGEM E DESTINO
		Position source = sourcePosition.toPosition(); // CONVERTENDO AS POSIÇÕES PARA POSIÇÕES DA MATRIZ 
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // VALIDANDO SE NA POSIÇÃO DE ORIGEM HAVIA UMA PEÇA
		Piece capturedPiece = makeMove(source, target); // DECLARANDO VARIAVEL PARA RECEBER O RESULTADO DA OPERAÇÃO 'MAKEMOVE' QUE REALIZA O MOVIMENTO DA PEÇA
		return (ChessPiece)capturedPiece;
	}
	
	// OPERAÇÃO PARA REALIZAR O MOVIMENTO DA PEÇA:
	
	private Piece makeMove(Position source, Position target) { // RECEBENDO A POSIÇÃO DE ORIGEM E DESTINO
		Piece p = board.removePiece(source); // VARIAVEL 'P' RECEBENDO A PEÇA QUE ESTAVA NA POSIÇÃO DE ORIGEM
		Piece capturedPiece = board.removePiece(target); // REMOVENDO A POSSÍVEL PEÇA QUE ESTEJA NA POSIÇÃO DE DESTINO QUE SERÁ A PEÇA CAPTURADA 
		board.placePiece(p, target); // COLOCANDO A POSIÇÃO DE ORIGEM NA POSIÇÃO DE DESTINO
		return capturedPiece;
	}
	
	// VALIDAÇÃO DA POSIÇÃO DE ORIGEM:
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
			// SE NÃO EXISTIR UMA PEÇA NA POSIÇÃO ELE TRAZ A EXCEÇÃO
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Ther is no possible moves for the chosen piece");
			// TESTANDO SE NÃO TIVER NENHUM MOVIMENTO POSSÍVEL EU TENHO QUE LANÇAR UMA EXCEÇÃO
		}
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
