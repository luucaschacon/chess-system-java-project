package chess.pieces;

import boardgame.Board;
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
}
