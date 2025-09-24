package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check; // OBS.: PROPRIEDADES BOOLEAN COMEÇAM COM 'FALSE'
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); // CRIANDO A LISTA DE PEÇAS NO TABULEIRO
	private List<Piece> capturedPieces = new ArrayList<>(); // CRIANDO A LISTA DE PEÇAS CAPTURADAS
	
	public ChessMatch() {
		board = new Board(8, 8); // CLASSE CHESSMATCH QUE SABE A DIMENSÃO DO TABULEIRO DE XADREZ 
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup(); // CHAMANDO O MÉTODO 'INITIAL SETUP' AQUI NO CONSTRUTOR DA PARTIDA
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition(); // CONVERTENDO UMA POSIÇÃO DE XADREZ PARA UMA POSIÇÃO DE MATRIZ NORMAL
		validateSourcePosition(position); // VALIDANDO A POSIÇÃO DE ORIGEM
		return board.piece(position).possibleMoves(); // RETORNANDO OS MOVIMENTOS POSSÍVEIS DA PEÇA DESSA POSIÇÃO
	}
	
	// OPERAÇÃO PARA MOVER A PEÇA:
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { // RECEBENDO A POSIÇÃO DE ORIGEM E DESTINO
		Position source = sourcePosition.toPosition(); // CONVERTENDO AS POSIÇÕES PARA POSIÇÕES DA MATRIZ 
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // VALIDANDO SE NA POSIÇÃO DE ORIGEM HAVIA UMA PEÇA
		validateTargetPosition(source, target); // VALIDANDO A POSIÇÃO DE DESTINO
		Piece capturedPiece = makeMove(source, target); // DECLARANDO VARIAVEL PARA RECEBER O RESULTADO DA OPERAÇÃO 'MAKEMOVE' QUE REALIZA O MOVIMENTO DA PEÇA
		
		// TESTANDO SE O MOVIMENTO DO JOGADOR ATUAL COLOCOU O PROPRIO JOGADOR EM CHECK:
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece); // DESFAZENDO O MOVIMENTO
			throw new ChessException("You can't put yourself in check"); // LANCANDO UMA EXCEÇÃO 
		}
		
		// SE O IF FALHAR RESTA TESTAR SE O OPONENTE FICOU EM CHECK:
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // ATUALIZANDO A PROPRIEDADE CHECK COMO VERDADEIRA, (SE NÃO FALSE)
		
		// TESTANDO SE A JOGADA QUE FEZ DEIXOU O OPONENTE EM CHECKMATE:
		
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn(); // TROCANDO O TURNO
		}
		return (ChessPiece)capturedPiece;
	}
	
	// OPERAÇÃO PARA REALIZAR O MOVIMENTO DA PEÇA:
	
	private Piece makeMove(Position source, Position target) { // RECEBENDO A POSIÇÃO DE ORIGEM E DESTINO
		ChessPiece p = (ChessPiece)board.removePiece(source); // VARIAVEL 'P' RECEBENDO A PEÇA QUE ESTAVA NA POSIÇÃO DE ORIGEM
		p.increaseMoveCount(); // TODA VEZ QUE MOVIMENTAR > INCREMENTANDO O MOVECOUNT DA PEÇA
		Piece capturedPiece = board.removePiece(target); // REMOVENDO A POSSÍVEL PEÇA QUE ESTEJA NA POSIÇÃO DE DESTINO QUE SERÁ A PEÇA CAPTURADA 
		board.placePiece(p, target); // COLOCANDO A POSIÇÃO DE ORIGEM NA POSIÇÃO DE DESTINO
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
			// SE ESSA PEÇA CAPTURADA FOR DIFERENTE DE NULLO (UMA PEÇA FOI CAPTURADA)
			// REMOVENDO ESSA PEÇA DA LISTA DE PEÇAS NO TABULEIRO
			// E ADICIONANDO A PEÇA NA LISTA DE PEÇAS CAPTURADAS
		}
		
		return capturedPiece;
	}
	
	// DESFAZER UM MOVIMENTO: 
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { 
		ChessPiece p = (ChessPiece)board.removePiece(target); // TIRANDO A PEÇA QUE MOVEU DO DESTINO
		p.decreaseMoveCount(); // TODA VEZ QUE DESFAZER O MOVIMENTO > DECREMENTAR O MOVECOUNT DA PEÇA
		board.placePiece(p, source); // DEVOLVENDO A PEÇA PARA A POSIÇÃO DE ORIGEM
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
			// SE A PEÇA CAPTURADA FOR DIFERENTE DE NULO
			// VOLTAR A PEÇA PRO TABULEIRO NA POSIÇÃO DE DESTINO
			// TIRANDO A PEÇA DA LISTA DE PEÇAS CAPTURADAS
			// E VOLTANDO A PEÇA NA LISTA DE PEÇA DO TABULEIRO
		}
	}
	
	// VALIDAÇÃO DA POSIÇÃO DE ORIGEM:
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
			// SE NÃO EXISTIR UMA PEÇA NA POSIÇÃO ELE TRAZ A EXCEÇÃO
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
			// SE A COR DA PEÇA FOR DIFERENTE DO JOGADOR ATUAL SIGNIFICA QUE É UMA PEÇA DO ADVERSÁRIO E Ñ PODE SER MOVIDA  
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Ther is no possible moves for the chosen piece");
			// TESTANDO SE NÃO TIVER NENHUM MOVIMENTO POSSÍVEL EU TENHO QUE LANÇAR UMA EXCEÇÃO
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
			// SE PARA A PEÇA DE ORIGEM A POSIÇÃO DE DESTINO N É UM MOVIMENTO POSSÍVEL (SIGNIFICA QUE N POSSO MOVIMENTAR PARA LÁ)
		}
	}
	
	private void nextTurn() { // TROCA DE TURNO
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		// SE O JOGADOR ATUAL FOR IGUAL A BRANCO ENTÃO AGORA ELE VAI SER O PRETO, CASO CONTRARIO VAI SER O BRANCO
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
		// SE A COR PASSADA COMO ARGUMENTO FOR IGUAL A WHITE ENTÃO ELA RETORNA BLACK, CASO CONTRÁRIO RETORNA WHITE
	}
	
	private ChessPiece king(Color color) { // PROCURANDO NA LISTA DE PEÇAS EM JOGO, QUAL QUE É O REI DESSA COR
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		// FILTRAGEM DA LISTA DAS PEÇAS EM JOGO, PROCURANDO AS PEÇAS 'X' TAL QUE A COR SEJA DA COR INFORMADA NO ARGUMENTO
		
		for (Piece p : list) { // PROCURAR A CADA PEÇA NA LISTA
			if (p instanceof King) { // TESTANDO SE A PEÇA É UMA INSTANCIA DE REI SIGNIFICA QUE ENCONTROU O REI
				return (ChessPiece)p; // RETORNANDO O REI (PEÇA 'P')
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board");
		// SE ESGOTAR O FOR E NÃO ENCONTRAR NENHUM REI, LANCA UMA EXCEÇÃO
	}
	
	// TESTE SE O REI DA COR ESTÁ EM CHECK:
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); // PEGA A POSIÇÃO DO REI NO FORMATO DE MATRIZ
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		// LISTA DAS PEÇAS DO OPONENTE NO TABULEIRO FILTRADAS COM A COR DO OPONENTE DESSE REI 
		
		// PARA CADA PEÇA CONTIDA NA LISTA, VAI TER QUE TESTAR SE EXISTE ALGUM MOVIMENTO POSSÍVEL QUE LEVA A POSIÇÃO DO MEU REI:
		
		for (Piece p : opponentPieces) {
			boolean [][] mat = p.possibleMoves(); // PEGANDO OS MOVIMENTOS POSSÍVEIS DENTRO DE UMA MATRIZ
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { 
				return true;
				// SE NA MATRIZ A POSIÇÃO CORRESPONDENTE A POSIÇÃO DO REI FOR TRUE, SIGNIFICA QUE O REI ESTÁ EM CHECK
			}
		}
		return false; 
		// SE ESGOTAR TODAS AS PEÇAS ADVERSÁRIAS E NENHUMA DELAS ESTIVER NA MATRIZ DE MOVIMENTOS POSSÍVEIS A POSIÇÃO DO REI MARCADA COMO TRUE,
		// SIGNIFICA QUE O REI NÃO ESTÁ EM CHECK, RETORNANDO 'FALSE'	
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { // SE ESSA COR Ñ ESTIVER EM CHECK, SIGNIFICA QUE TAMBÉM NÃO ESTÁ EM CHECKMATE
			return false;
		}
		// SE PASSAR DO IF, ENTÃO TESTAR TODAS AS PEÇAS DA COR Ñ TIVEREM MOVIMENTOS POSSÍVEIS QUE TIRE DO CHECK, ELA ESTÁ EM CHECKMATE
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		
		for (Piece p : list) { // PERCORRER TODAS AS PEÇAS DA LISTA
			boolean[][] mat = p.possibleMoves(); // MATRIZ RECEBE OS MOVIMENTOS POSSÍVEIS
			for (int i=0; i<board.getRows();i++) { // PERCORRENDO AS LINHAS DA MATRIZ
				for (int j=0; j<board.getColumns(); j++) { // PERCORRENDO AS COLUNAS DA MATRIZ
					
					// TESTAR SE ESSA POSIÇÃO DA MATRIZ É UM MOVIMENTO POSSÍVEL, SE TIVER UM MOVIMENTO POSSÍVEL VOU TESTAR, 
					// ESSE MOVIMENTO POSSÍVEL TIRA DO CHECK? SE TIRA DO CHECK RETORNA FALSE, OU SEJA NÃO ESTÁ EM CHECKMATE
					if (mat [i][j]) { 
						Position source = ((ChessPiece)p).getChessPosition().toPosition(); // POSIÇÃO DE ORIGEM
						Position target = new Position(i, j); // INSTANCIANDO A POSIÇÃO DE DESTINO 
						// MOVENDO A PEÇA 'P' DA ORIGEM PARA O DESTINO:
						Piece capturedPiece = makeMove(source, target); // CRIANDO VARIAVEL RECEBENDO O MOVIMENTO DE ORIGEM P/ DESTINO
						boolean testCheck = testCheck(color); // TESTANDO SE O REI DA COR AINDA ESTÁ EM CHECK
						undoMove(source, target, capturedPiece); // DESFAZER O MOVIMENTO DO TESTE ACIMA
						if (!testCheck) {
							return false; // SE NÃO ESTAVA EM CHECK SIGNIFICA QUE O MOVIMENTO A CIMA TIROU O REI DO CHECK
						}
					}
				}
			}
		}
		return true; // SE NÃO EXISTIR NENHUM MOVIMENTO POSSÍVEL QUE TIRE DO CHECK, RETORNA TRUE (ESTÁ EM CHECKMATE)
		
	}
	
	// MÉTODO PARA RECEBER AS COORDENADAS DO XADREZ (INSTANCIANDO UMA NOVA PEÇA NO JOGO DE XADREZ:
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // O '.TOPOSITION' CONVERTE PARA POSIÇÃO DE MATRIZ
		piecesOnTheBoard.add(piece); // COLOCANDO A PEÇA NA LISTA DE PEÇAS NO TABULEIRO
	}
	
	// MÉTODO PARA INICIAR A PARTIDA DE XADREZ, COLOCANDO AS PEÇAS NO TABULEIRO:
	
	private void initialSetup() {
		
		//placeNewPiece('b', 6, new Rook(board, Color.WHITE)); // (ROOK RECEBENDO O TABULEIRO E A COR) DEPOIS A POSIÇÃO
		//placeNewPiece('e', 8, new King(board, Color.BLACK));
		//placeNewPiece('e', 1, new King(board, Color.WHITE));
		
		//placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        //placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        //placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        //placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        //placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        //placeNewPiece('d', 1, new King(board, Color.WHITE));

        //placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        //placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        //placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        //placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        //placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        //placeNewPiece('d', 8, new King(board, Color.BLACK));
		
		//placeNewPiece('h', 7, new Rook(board, Color.WHITE));
		//placeNewPiece('d', 1, new Rook(board, Color.WHITE));
		//placeNewPiece('e', 1, new King(board, Color.WHITE));
		
		//placeNewPiece('b', 8, new Rook(board, Color.BLACK));
		//placeNewPiece('a', 8, new King(board, Color.BLACK));
		
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
