package boardgame;

public abstract class Piece { // PEÇA
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves(); // CRIANDO A VARIAVEL PARA RECEBER OS POSSIBLE MOVES
			for (int i=0; i<mat.length; i++) { // VERIFICANDO SE TEM ALGUMA POSIÇÃO VERDADEIRA NA MATRIZ (LINHAS E COLUNAS)
				for (int j=0; j<mat.length; j++) {
					if (mat[i][j]) { // SE A MATRIZ NA LINHA 'I' E COLUNA 'J' FOR VERDADEIRA (EXISTE UM MOVIMENTO POSSÍVEL E RETORNA TRUE)
						return true;
					}
				}
			}
			return false; // SIGNIFICA QUE NENHUM LUGAR RETORNOU TRUE
		}
}
