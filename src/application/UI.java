package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	
	public static void clearScreen() {   
		System.out.print("\033[H\033[2J");   
		System.out.flush();   
	}  
	
	// MÉTODO PARA LER UMA POSIÇÃO DO USUÁRIO:
	
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
		String s = sc.nextLine();
		char column = s.charAt(0); // PARA LER A COLUNA DA POSIÇÃO QUE É O PRIMEIRO CARACTERE DO STRING
		int row = Integer.parseInt(s.substring(1)); // RECORTANDO O STRING A PARTIR DA POSIÇÃO 1 E CONVERTE O RESULTADO PARA INTEIRO (TENDO A LINHA)
		return new ChessPosition(column, row); // RECEBE A COLUNA E A LINHA 
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition.Valid values are from a1 to h8.");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) { 
		printBoard(chessMatch.getPieces()); // IMPRIMIR O TABULEIRO
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn : " + chessMatch.getTurn()); // IMPRIMIR O TURNO
		System.out.println("Waiting player: " + chessMatch.getCurrentPlayer()); // AGUARDANDO O JOGADOR DA VEZ A JOGAR
		if (chessMatch.getCheck()) {
			System.out.println("CHECK!");
			// SE A MINHA PARTIDA ESTÁ EM CHECK IMPRIMIR O AVISO NA TEL
		}
	}
	
	public static void printBoard(ChessPiece[][] pieces) {
		
		// IMPRIMINDO O TABULEIRO PERCORRENDO AS LINHAS E AS COLUNAS:
		
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " "); // IMPRIME OS NUMEROS DAS COLUNAS (1 A 8)
			
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j], false); // IMPRIME A PEÇA E O FALSE INDICA QUE NENHUMA PEÇA É PRA TER O FUNDO COLORIDO
			}
			System.out.println(); // QUEBRA DE LINHA PARA PASSAR PARA A PROXIMA LINHA
		}
		System.out.println("  a b c d e f g h"); // IMPRIMINDO A LINHA ESPECIAL QUE INDICA A POSIÇÃO DE 'A' ATÉ 'H'
	}
	
	// PINTANDO O FUNDO COLORIDO DEPENDENDO D VARIAVEL DO MOVIMENTO
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {	
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " "); 
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println(); 
		}
		System.out.println("  a b c d e f g h"); 
	}
	
	// CRIANDO UM MÉTODO AUXILIAR PARA IMPRIMIR 'UMA' PEÇA:
	
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET); // SIGNIFICA QUE NÃO TINHA PEÇA NESSA POSIÇÃO DO TABULEIRO, SE NÃO, IMPRIME A PEÇA
		}
		else { // TESTANDO SE A PEÇA É BRANCA OU PRETA:
			
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			}
			else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" "); // DE TODA FORMA NO FIM, IMPRIME UM ESPAÇO EM BRANCO PARA AS PEÇAS NÃO FICAREM GRUDADAS
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured) { // IMPRIMINDO AS PEÇAS CAPTURADAS
		// CRIANDO LISTA DAS PEÇAS CAPTURADAS FILTRANDO PELA COR COM A EXPRESSÃO LAMBDA: 
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		// LÓGICA PARA IMPRIMIR ESSAS LISTAS NA TELA: 
		System.out.println("Captured pieces: ");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE); // PARA GARANTIR QUE A LISTA SEJA IMPRESSA NA COR BRANCA
		System.out.println(Arrays.toString(white.toArray())); // IMPRIMINDO UM ARRAY DE VALORES NO JAVA
		System.out.print(ANSI_RESET); // RESETANDO A COR DA IMPRESSÃO
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW); 
		System.out.println(Arrays.toString(black.toArray())); 
		System.out.print(ANSI_RESET); 
	}
}
