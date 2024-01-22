public class Next_to_bomb extends Cell{
    public int num_bombs_next_to = 1;
    public Next_to_bomb(int x, int y){
        super(x,y);
        super.c = 'N';
    }

    public void increment_num_bombs_next_to(){
        this.num_bombs_next_to++;
    }

//    public void changeLabelButton(){
//        if (!this.state_hidden){
//            this.button.setLabel(""+this.num_bombs_next_to);
//        }
//    }
}
