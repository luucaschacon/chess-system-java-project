package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		
		ChessMatch chessMatch = new ChessMatch();
		
		// CRIANDO UMA FUNÇÃO PARA IMPRIMIR AS PEÇAS DA PARTIDA:
		UI.printBoard(chessMatch.getPieces()); // MÉTODO PARA RECEBER A MATRIZ DE PEÇAS DA PARTIDA
		
	}
}
