import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
//      LinkedList<Board> boards = new LinkedList<>();
//      boards.add(new Board());
//    for (int queen = 0; queen < 8; queen++) {
//      for (int y = 0; y < 8; y++) {
//        for (int x = 0; x < 8; x++) {
//          Board next = boards.getLast().placeQueenAt(x, y);
//          if (next.isValid()) {
//            boards.add(next);
//          }
//        }
//      }
//    }


//    System.out.println(  boards.getLast());
        try {
            System.out.println(findViableBoard());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Board findViableBoard() throws InterruptedException {

        LinkedList<Board> boards = new LinkedList<>();
        boards.add(new Board());

        int newX = 0;
        int newY = 0;


        boards.getLast().placeQueenAt(newX, newY);

        return recursiveBoards(boards, newX, newY);
    }

    private static Board recursiveBoards(LinkedList<Board> boards, int newX, int newY) throws InterruptedException {

        //placeQueenAt stops me from ever putting something in 8

        Board currentBoard = boards.getLast().placeQueenAt(newX, newY);
        boards.add(currentBoard);

//        Thread.sleep(250);
        System.out.println(currentBoard);

        if (isOutOfBounds(newY, currentBoard)) {
            List<Queen> allQueens = boards.getLast().getAllQueens();
            OptionalInt maxX = allQueens.stream().mapToInt(x -> x.getX()).max();

            if (!maxX.isPresent()) {
                System.out.println("queen did not have exist");
                exit(0);
            }


            OptionalInt oldY = allQueens.stream()
                    .filter(queen -> queen.getX() == maxX.getAsInt())
                    .mapToInt(y -> y.getY())
                    .findFirst();

            boards.removeLast();
            boards.removeLast();
            recursiveBoards(boards, maxX.getAsInt(), oldY.getAsInt() + 1);

        } else if (!currentBoard.isValid()) {
            boards.removeLast();
            recursiveBoards(boards, newX, newY + 1);
        } else if (!isFullBoard(currentBoard)) {
            newX = currentBoard.getAllQueens().size();
            recursiveBoards(boards, newX, 0);
        }


        return boards.getLast();
    }

    private static boolean isFullBoard(Board currentBoard) {
        return currentBoard.getAllQueens().size() == currentBoard.getBoardSize();
    }

    private static boolean isOutOfBounds(int newY, Board board) {

        return newY == board.getBoardSize() ? true : false;
    }


}
