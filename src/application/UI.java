package application;

import chess.ChessPiece;

public class UI {
	
	public static void printBoard(ChessPiece[][] pieces) {
		
		// IMPRIMINDO O TABULEIRO PERCORRENDO AS LINHAS E AS COLUNAS:
		
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " "); // IMPRIME OS NUMEROS DAS COLUNAS (1 A 8)
			
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]); // IMPRIME A PEÇA
			}
			System.out.println(); // QUEBRA DE LINHA PARA PASSAR PARA A PROXIMA LINHA
		}
		System.out.println("  a b c d e f g h"); // IMPRIMINDO A LINHA ESPECIAL QUE INDICA A POSIÇÃO DE 'A' ATÉ 'H'
	}
	
	// CRIANDO UM MÉTODO AUXILIAR PARA IMPRIMIR 'UMA' PEÇA:
	
	private static void printPiece(ChessPiece piece) {
		if (piece == null) {
			System.out.print("-"); // SIGNIFICA QUE NÃO TINHA PEÇA NESSA POSIÇÃO DO TABULEIRO
		}
		else {
			System.out.print(piece); // SE NÃO, IMPRIME A PEÇA
		}
		System.out.print(" "); // DE TODA FORMA NO FIM, IMPRIME UM ESPAÇO EM BRANCO PARA AS PEÇAS NÃO FICAREM GRUDADAS
	}

}
