import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

public class Main {
    public static void main(String[] args){
        Board board = Board.createStandardBoard();
        System.out.println(board.toString());
    }
}
