
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void print_grid(Cell[][] grid, int grid_size){
        for (int i =0; i<grid_size; i++){
            String line = "";
            for (int j =0; j<grid_size; j++){
                if (grid[i][j].state_hidden){
                    line+= "X";
                }
                else if(grid[i][j] instanceof Next_to_bomb){
                    line+= ((Next_to_bomb) grid[i][j]).num_bombs_next_to;
                }else{
                    line += grid[i][j].c;
                }
                line += " ";
            }
            System.out.println(line);
        }
        System.out.println("\n");
    }
//    public static Cell[][] cell_revealer(Cell[][] grid, int x, int y, int grid_size){
//        Cell xy = grid[x][y];
//
//        if (xy instanceof Empty &&
//                xy.state_hidden){
//            grid[x][y].state_hidden = false;
//
//            int x_not_min = (x>0)?1:0;
//            int x_not_max = (x<grid_size-1)?1:0;
//            int y_not_min = (y>0)?1:0;
//            int y_not_max = (y<grid_size-1)?1:0;
//
//            for (int a= x - x_not_min ; a<= x + x_not_max ; a++){
//                for (int b= y - y_not_min; b<= y + y_not_max ; b++){
//                    if(a!=x || b != y) {
//                        grid = cell_revealer(grid, a, b, grid_size);
//                    }
//                }
//            }
//        }
//        else {//It must be a next to bomb cell in this case if it is not empty
//            grid[x][y].state_hidden = false;
//        }
//        return grid;
//    }

    public static void main(String[] args) throws InterruptedException {
        //Grid - made up of cells, each cell has a state
        //cell can be - Bomb or number or
        //
        Random rand = new Random();
        //Scanner input = new Scanner(System.in);

        int grid_size = 5;
        int num_bombs = 3;

        Cell[][] grid = new Cell[grid_size][grid_size];


        for (int i = 0; i < grid_size; i++){
            for (int j = 0; j < grid_size; j++){
                grid[i][j] = new Empty(i,j);
            }
        }

        int x,y;
        for (int i =0 ; i<num_bombs; i++){
            x = rand.nextInt(grid_size);
            y = rand.nextInt(grid_size);
            if(grid[x][y] instanceof Bomb){
                i--;
                continue;
            }
            else{
                grid[x][y] = new Bomb(x,y);
            }

            int x_non_min = (x>0)?1:0;
            int x_not_max = (x<grid_size-1)?1:0;
            int y_not_min = (y>0)?1:0;
            int y_not_max = (y<grid_size-1)?1:0;

            for (int a=x-x_non_min ; a<=x+x_not_max ; a++){
                for (int b=y-y_not_min; b<=y+y_not_max ; b++){
                    if (grid[a][b] instanceof Next_to_bomb){
                        Next_to_bomb N = (Next_to_bomb) grid[a][b];
                        N.increment_num_bombs_next_to();

                        grid[a][b] = N;
                    } else if (grid[a][b] instanceof Empty) {
                        grid[a][b] = new Next_to_bomb(a,b);
                    }
                }
            }
        }

        GUI m = new GUI(grid,grid_size);
//        while (true){
//            print_grid(grid,grid_size);
//            System.out.println("Please pick the x followed by the y: ");
//            int user_pick_y = input.nextInt();
//            int user_pick_x = input.nextInt();
//            user_pick_x = grid_size - user_pick_x - 1;
//
//            if (grid[user_pick_x][user_pick_y] instanceof Bomb) {
//                //grid[user_pick_x][user_pick_y].state_hidden = false;
//                for (int i =0; i<grid_size; i++){
//                    for (int j= 0; j< grid_size; j++){
//                        grid[i][j].state_hidden = false;
//                    }
//                }
//                print_grid(grid,grid_size);
//                System.out.println("GAME OVER YOU LOSE HAHAHAHAHA!");
//                break;
//            }
//            else {
//                grid = cell_revealer(grid,user_pick_x,user_pick_y, grid_size);
//            }
//        }
    }
}