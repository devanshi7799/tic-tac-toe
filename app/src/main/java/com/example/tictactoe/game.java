package com.example.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class game extends AppCompatActivity implements View.OnClickListener {

    TextView x,o;
    Button reset;
    Button [][] btns=new Button[3][3];

    private int p1points=0,p2points=0,count=0;
    private String p1name,p2name;
    private Boolean p1turn=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        o=(TextView)findViewById(R.id.o);
        x=(TextView)findViewById(R.id.x);
        reset=(Button)findViewById(R.id.reset);
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String idname="b"+i+j;
                int btnid=this.getResources().getIdentifier(idname,"id",getPackageName());
                btns[i][j]=findViewById(btnid);
                btns[i][j].setOnClickListener(this);

            }
        }


        p1name=getIntent().getStringExtra("player1name");
        p2name=getIntent().getStringExtra("player2name");
        o.setText(p2name+"(O): 0");
        x.setText(p1name+"(X): 0");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetboard();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(p1turn)
        {
            ((Button)v).setText("X");
            ((Button)v).setTextColor(this.getResources().getColor(R.color.Red));
            ((Button)v).setEnabled(false);
        }
        else
        {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(this.getResources().getColor(R.color.Green));
            ((Button)v).setEnabled(false);
        }

        if(CheckforWin())
        {
            if(p1turn)
            {
                Player1wins();
            }
            else if(!p1turn)
            {
                Player2wins();
            }
            else if(count==9)
            {
                draw();
            }
        }
        else
        {
            count++;
            p1turn = !p1turn;
        }
    }

    private Boolean CheckforWin()
    {
        String field[][] = new String[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                field[i][j]=btns[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++)
        {
            if(field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&!field[i][0].equals(" "))
            {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&!field[0][i].equals(" "))
            {
                return true;
            }
        }

        for(int i=0;i<3;i++)
        {
            if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(" ")) {
                return true;
            }
        }

        for(int i=0;i<3;i++)
        {
            if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && field[0][2].equals(" ")) {
                return true;
            }

        }
            return false;
    }

    private void Player1wins()
    {
        p1points++;
        updatePointText();
        askForAnotherGame(p1name+"Wins!");
    }
    private void Player2wins()
    {
        p2points++;
        updatePointText();
        askForAnotherGame(p2name+"Wins!");
    }

    private void draw()
    {
        Toast.makeText(this, "Match Draw!", Toast.LENGTH_SHORT).show();
        askForAnotherGame("Match Draw!");
    }

    private void askForAnotherGame(String msg)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(game.this);
        builder.setTitle(msg);
        builder.setMessage("Play Again?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetboard();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(game.this,MainActivity.class));
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void resetgame()
    {
        p1points=0;
        p2points=0;
    }

    private void resetboard()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                btns[i][j].setText(" ");
                btns[i][j].setEnabled(true);
            }
        }
    }

    private void updatePointText()
    {
        x.setText(p1name+":"+p1points);
        o.setText(p2name+":"+p2points);
    }
}
