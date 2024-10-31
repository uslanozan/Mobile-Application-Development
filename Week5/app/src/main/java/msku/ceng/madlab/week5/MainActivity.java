package msku.ceng.madlab.week5;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";
    boolean player1Turn = true;
    byte [] [] board = new byte[3][3];
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.board), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        table = findViewById(R.id.board);
        for (int i = 0 ; i < 3 ; i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0 ; j < 3; j++){
                Button button = (Button)row.getChildAt(j);
                button.setOnClickListener(new CellListener(i,j));
            }
        }
    }

    class CellListener implements View.OnClickListener{

        int row, col;

        public CellListener(int row, int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View view) {
            if (!isValidMove(row,col)){
                Toast.makeText(MainActivity.this,
                        "Cell is already occupied", Toast.LENGTH_LONG).show();
                return;
            }
            if (player1Turn){
                ((Button)view).setText(PLAYER_1);
                board[row][col] = 1;
            }
            else {
                ((Button)view).setText(PLAYER_2);
                board[row][col] = 2;
            }

            if (gameEnd(row,col) == -1){
                player1Turn =! player1Turn;
            } else if (gameEnd(row,col) == 0) {
                Toast.makeText(MainActivity.this,"It is a Draw",Toast.LENGTH_LONG).show();
            } else if (gameEnd(row,col) == 1) {
                Toast.makeText(MainActivity.this,"Player 1 is Winner !",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MainActivity.this,"Player 1 is Winner !",Toast.LENGTH_LONG).show();
            }

        }

        //-1 devam, 0 eşit, 1 bitti gibi
        public int gameEnd(int row,int col){

            int symbol = board[row][col];
            boolean win = true;
            for (int i = 0; i < 3 ; i++){
                if (board[i][col] != symbol){
                    win = false;
                    break;
                }
            }
            if (win){
                return symbol;
            }

            //check rows

            //check diagonals

            return -1;
        }

        public boolean isValidMove(int row, int col){
            return board[row][col] == 0;
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putBoolean("player1Turn",player1Turn);
        byte [] boardSingle = new byte[9];
        for (int i = 0 ; i < 3 ;i++){
            for (int j = 0; j < 3; j++){
                boardSingle[3+i+j] = board[i][j];
            }
        }
        outState.putByteArray("board",boardSingle);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        byte[] boardSingle = savedInstanceState.getByteArray("board");
        for (int i = 0; i < 0; i++){
            board[i/3][i%3] = boardSingle[i];
        }

        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++ ){
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++){
                Button button =(Button) row.getChildAt(j);
                if (board[i][j] == 1){
                    button.setText("X");
                } else if (board[i][j] == 2) {
                    button.setText("O");
                }
            }
        }
    }
}