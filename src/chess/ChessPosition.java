package chess;

import boardgame.Position;

public class ChessPosition {
	
	// OBSERVAÇÃO: A LINHA DA MATRIZ VAI SER IGUAL A 8 MENOS A LINHA DO XADREZ = (MATRIX_ROW = 8 - CHESS_ROW)
	
	// OBSERVAÇÃO: A COLUNA DO XADREZ SUBTRAIDO DO CARACTERE 'A' = (MATRIX_COLUMN = CHESS_COLUMN - 'A')
	
	// 'a' - 'a' = 0
	// 'b' - 'a' = 1
	// 'c' - 'a' = 2
	
	// a = 0
	// b = 1
	// c = 2
	// d = 3
	// ...
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Erro instantiating ChessPosition. Valid values are from a1 to h8.");	
			// SE A COLUNA FOR MENOR QUE O CARACTER 'A' OU A COLUNA FOR MAIS QUE O CARACTER 'H'
			// OU SE A LINHA FOR MENOR QUE '1' OU MENOR QUE '8'
			// SE ACONTECER ALGUMA DESSAS CONDIÇÕES NÃO VAI ACEITAR A INSTANCIAÇÃO DO CHESS POSITION	
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	protected Position toPosition() {
		return new Position(8 - row,  column - 'a');
	}
	
	// OPERAÇÃO PARA DADO UMA POSIÇÃO NA MATRIZ EU TENHO QUE CONVERETELA EM UMA POSIÇÃO DE XADREZ:
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}
}
