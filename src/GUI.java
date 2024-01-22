import java.awt.Frame;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class GUI extends Frame{
    private Dictionary<Button, Cell> button_cell = new Hashtable<>();
    int grid_size;
    Cell[][] grid;

    Button[][] button_grid;

    public static void print_grid(Cell[][] grid, int grid_size){
        for (int i =0; i<grid_size; i++){
            String line = "";
            for (int j =0; j<grid_size; j++){
                if(grid[i][j].c == 'O'){
                    line+= 'O';
                }
                else if (grid[i][j].state_hidden){
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
    public static Cell[][] cell_revealer(Cell[][] grid, int x, int y, int grid_size){
        Cell xy = grid[x][y];

        if (xy instanceof Empty &&
                xy.state_hidden){
            grid[x][y].state_hidden = false;


            int x_not_min = (x>0)?1:0;
            int x_not_max = (x<grid_size-1)?1:0;
            int y_not_min = (y>0)?1:0;
            int y_not_max = (y<grid_size-1)?1:0;

            for (int a= x - x_not_min ; a<= x + x_not_max ; a++){
                for (int b= y - y_not_min; b<= y + y_not_max ; b++){
                    if(a!=x || b != y) {
                        grid = cell_revealer(grid, a, b, grid_size);
                    }
                }
            }
        }
        else {//It must be a next to bomb cell in this case if it is not empty
            grid[x][y].state_hidden = false;
        }
        return grid;
    }
    public GUI(Cell[][] grid, int grid_size) throws InterruptedException {
        this.grid = grid;
        this.grid_size  = grid_size;
        GridLayout gridLayout = new GridLayout(grid_size,grid_size);
        setLayout(gridLayout);

        this.button_grid = new Button[grid_size][grid_size];


        for (int i =0; i<grid_size;i++){
            for (int j=0; j<grid_size; j++){
                String label_for_button = "";
                if (grid[i][j].state_hidden){
                    label_for_button = "X";
                }
                else if(grid[i][j] instanceof Next_to_bomb){
                    label_for_button += ((Next_to_bomb) grid[i][j]).num_bombs_next_to;
                }else{
                    label_for_button += grid[i][j].c;
                }
                button_grid[i][j] = new Button(label_for_button);
                this.button_cell.put(button_grid[i][j], grid[i][j]);
                add(button_grid[i][j]);
            }
        }
//        button1 = new Button("X");
//        add(button1);
//        button1L l1 = new button1L();
//        button1.addActionListener(l1);

        //button1.addMouseListener(mouseAdapter);
        //add(button1);
        for (int i =0 ; i<grid_size; i++){
            for (int j =0; j<grid_size; j++){
                button_grid[i][j].addMouseListener(mouseAdapter);
            }
        }

        setTitle("MINESWEEPERRRRRRRRRRRRRRRRRRRRR");
        setSize(600,600);
        setLocation(500,200);
        setVisible(true);

        while (true){
            for (int i= 0; i< grid_size; i++){
                for (int j = 0; j< grid_size; j++){
                    if (!grid[i][j].state_hidden) {
                        if (grid[i][j] instanceof Empty) {
                            button_grid[i][j].setLabel("" + grid[i][j].c);
                            button_grid[i][j].setEnabled(false);
                        } else if (grid[i][j] instanceof Next_to_bomb) {
                            button_grid[i][j].setLabel(""+((Next_to_bomb) grid[i][j]).num_bombs_next_to);
                            button_grid[i][j].setEnabled(false);
                        }
                    }else{
                        if (grid[i][j].c == 'O'){
                            button_grid[i][j].setLabel("O");
                        }
                    }
                }
            }
            print_grid(grid,grid_size);
            //Thread.sleep(500);
        }
    }
    public static void main(String[] args){
    }

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override

        public void mouseClicked(MouseEvent e) {
            Button clickedButton = (Button) e.getSource();
            Cell clickedCell = button_cell.get(clickedButton);
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (clickedCell.c == 'O') {
                    System.out.println("YAY!");
                    if (clickedCell instanceof Next_to_bomb) {
                        button_cell.get(clickedButton).c = 'N';
                    } else if (clickedCell instanceof Empty) {
                        button_cell.get(clickedButton).c = 'E';
                    } else {
                        button_cell.get(clickedButton).c = 'B';
                    }
                    //clickedCell.c = (clickedCell instanceof Next_to_bomb)? 'N': (clickedCell instanceof Empty)? 'E': 'B';
                } else {
                    button_cell.get(clickedButton).c = 'O';
                }
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                if (clickedCell instanceof Bomb && clickedCell.c != 'O') {
                    for (int i = 0; i < grid_size; i++) {
                        for (int j = 0; j < grid_size; j++) {
                            grid[i][j].state_hidden = false;
                            button_grid[i][j].setEnabled(false);
                            if (grid[i][j] instanceof Empty || grid[i][j] instanceof Bomb) {
                                button_grid[i][j].setLabel("" + grid[i][j].c);
                            } else if (grid[i][j] instanceof Next_to_bomb) {
                                button_grid[i][j].setLabel("" + ((Next_to_bomb) grid[i][j]).num_bombs_next_to);
                            }
                        }
                        System.out.println("GAME OVER YOU LOSE HAHAHAHAHA!");
                    }
                } else {
                    grid = cell_revealer(grid, clickedCell.row, clickedCell.column, grid_size);
                }
            }
        }
    };
}
