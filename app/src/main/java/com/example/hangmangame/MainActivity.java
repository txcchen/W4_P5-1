package com.example.hangmangame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Game game;
    Button q, w, e, r, t, y, u, i, o, p;
    Button a, s, d, f, g, h, j, k, l;
    Button z, x, c, v, b, n, m;
    Button restart;
    Button hint;
    TextView giveHint;
    ArrayList<Button> inactive;
    ArrayList<Button> keyboard;
    private int numHint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        giveHint = findViewById(R.id.giveHint);

        game = new Game();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, game).commit();
        inactive = new ArrayList<>();

        q = setButton(R.id.q);
        w = setButton(R.id.w);
        e = setButton(R.id.e);
        r = setButton(R.id.r);
        t = setButton(R.id.t);
        y = setButton(R.id.y);
        u = setButton(R.id.u);
        i = setButton(R.id.i);
        o = setButton(R.id.o);
        p = setButton(R.id.p);
        a = setButton(R.id.a);
        s = setButton(R.id.s);
        d = setButton(R.id.d);
        f = setButton(R.id.f);
        g = setButton(R.id.g);
        h = setButton(R.id.h);
        j = setButton(R.id.j);
        k = setButton(R.id.k);
        l = setButton(R.id.l);
        z = setButton(R.id.z);
        x = setButton(R.id.x);
        c = setButton(R.id.c);
        v = setButton(R.id.v);
        b = setButton(R.id.b);
        n = setButton(R.id.n);
        m = setButton(R.id.m);
        restart = setButton(R.id.restart);
        hint = setButton(R.id.hint);

        keyboard=new ArrayList<Button>();
        keyboard.addAll(Arrays.asList(q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m));
        Log.d("LOOK", keyboard.toString());
    }

    private Button setButton(int id) {
        Button button = findViewById(id);
        button.setOnClickListener(this);
        return button;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        game.mainActivityButtonInput(buttonText);

        if (buttonText.charAt(0) >= 97 && buttonText.charAt(0) <= 122) {
            button.setEnabled(false);
            inactive.add(button);
        }
        if (buttonText.equals("New Game")) {
            game.newGame();
            for (int i = 0; i < inactive.size(); i++) {
                inactive.get(i).setEnabled(true);
            }
            inactive.removeAll(inactive);
            giveHint.setText("");
            numHint = 0;
        }

        //hint
        if (buttonText.equals("Hint")) {
            //if count <= game.chance then we can get hint, otherwise
            //if count = game.chance - 1, (player only has one life left) toast= “Hint not available”

            if(numHint>0 && game.count == game.chance - 1){
                Toast.makeText(this, "Hint not available", Toast.LENGTH_SHORT).show();
            }
            else if(numHint == 3){
                Toast.makeText(this, "No more hints", Toast.LENGTH_SHORT).show();
            }
            else{
                if (numHint == 0) {
                    giveHint.setText(game.hint);
                    numHint++;
                }
                else if (numHint == 1) {
                    //The second time it is clicked it
                    // disables half of the remaining letters (that are not part of the word)
                    // costs the user a turn
                    numHint++;
                    giveHint.setText(game.hint);

                    int iKeys = 0; //keeps track of index in keyboard array
                    int half = (26 - inactive.size())/2; //half of the remaining letters
                    Log.d("numKeysInactive", String.valueOf(inactive.size()));
                    Log.d("half left", String.valueOf(half));
                    int i = half;
                    while(i > 0){
                        String btnVal = keyboard.get(iKeys).getText().toString();
                        if(!inactive.contains(keyboard.get(iKeys)) && !game.currentWord.contains(btnVal)){
                            //if the key is still active, and the letter is not in the word
                            keyboard.get(iKeys).setEnabled(false);
                            inactive.add(keyboard.get(iKeys));
                            i--;
                        }
                        iKeys++;
                    }
                    game.drawHangman(game.chance);
                }
                else if (numHint == 2) {
                    //The third time it is clicked, it shows all the vowels, BUT it costs the user a turn.
                    //Be sure to disable all the vowel buttons so they user doesn’t click them again.
                    numHint++;
                    giveHint.setText(game.hint);

                    ArrayList<String> vowels = new ArrayList<String>();
                    vowels.addAll(Arrays.asList("a", "e", "i", "o", "u"));
                    Log.d("vowel List", vowels.toString());

                    for (int i = 0; i <keyboard.size(); i++){
                        String letter =  keyboard.get(i).getText().toString();
                        Log.d("test", "Look here");
                        //iterate through all the letters
                        //if not inactive, and is a vowel in the word, place in the guessed word and disable the key
                        if(!inactive.contains(keyboard.get(i)) && vowels.contains(letter) && game.currentWord.contains(letter)){
                            game.mainActivityButtonInput(letter);
                            Log.d("Vowel Input", keyboard.get(i).getText().toString());
                            keyboard.get(i).setEnabled(false);
                            inactive.add(keyboard.get(i));
                        }
                    }
                    game.drawHangman(game.chance);
                }
            } //end of outermost else statement
        }//end of if buttontext = "Hint"
    }
}