package boardgame;

public class Board { // TABULEIRO
	
	private int rows;
	private int columns;
	private Piece[][] pieces; // MATRIZ DE PEÇAS
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Erro creating board: there must be at least 1 row and 1 column");
			// QUANDO FOR CRIAR O TABULEIRO A QUANTIDADE DE LINHAS E COLUNAS TEM QUE SER PELO MENOS 1
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece (int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
			// SE A POSIÇÃO NÃO EXISTIR LANÇA A EXCEÇÃO
		}
		return pieces[row][column];
	}
	
	public Piece piece (Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
			// SE A POSIÇÃO NÃO EXISTIR LANÇA A EXCEÇÃO
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	// CRIANDO O MÉTODO PARA COLOCAR A PEÇA NO TABLEIRO NA POSIÇÃO INFORMADA (LINHA E COLUNA):
	
	public void placePiece (Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
			// TESTANDO SE JÁ EXISTE UMA PEÇA NA POSIÇÃO
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // INFORMANDO QUE A PEÇA NÃO ESTÁ MAIS NA POSIÇÃO NULA E SIM NA POSIÇÃO 'position'
	}
	
	// CRIANDO UM MÉTODO AUXILIAR PARA VERIFICAR SE A POSIÇÃO EXISTE:
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
		
		// LINHA TEM QUE SER MAIOR OU IGUAL A 0
		// LINHA TEM QUE SER MENOR QUE A ALTURA DO TABULEIRO
		// COLUNA TEM QUE SER MAIOR OU IGUAL A 0
		// COLUNA TEM QUE SER MENOR DO QUE A QUANTIDADE DE COLUNAS DO TABULEIRO
	}
	
	// MÉTODO PARA TESTAR SE A POSIÇÃO EXISTE (REAPROVEITANDO O MÉTODO DE CIMA):
	
	public boolean positionExists (Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	// MÉTODO PARA TESTAR QUE TEM UMA PEÇA NO TABULEIRO:
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
			// SE A POSIÇÃO NÃO EXISTIR LANÇA A EXCEÇÃO
		}
		return piece(position) != null; // TESTANDO SE A PEÇA QUE ESTIVER NESSA POSIÇÃO É DIFERENTE DE NULO (SIGNIFICANDO QUE TEM UMA PEÇA NA POSIÇÃO)
	}
}
