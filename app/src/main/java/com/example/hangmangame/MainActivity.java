package com.example.hangmangame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Game game;
    Button q, w, e, r, t, y, u, i, o, p;
    Button a, s, d, f, g, h, j, k, l;
    Button z, x, c, v, b, n, m;
    Button restart;
    ArrayList<Button> inactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }
    }
}