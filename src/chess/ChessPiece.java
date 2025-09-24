package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}	
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position); // CONVERTENDO O 'POSITION' PARA 'CHESSPOSITION'
	}
	
	// O 'PROTECTED' É PARA QUE A OPERAÇÃO SEJA ACESSÍVEL SOMENTE PELO MESMO PACOTE E PELAS SUBCLASSES (QUE SÃO AS PEÇAS)
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position); // VARIAVEL RECEBENDO A PEÇA QUE ESTIVER NA POSIÇÃO
		return p != null && p.getColor() != color; 
		// SE O 'P' É DIFERENTE DE NULO E SE O P.GETCOLOR É DIFERENTE DA COR DA MINHA PEÇA ONDE ESTOU
	}
}
