package com.example.hangmangame;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Game#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Game extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    private final String[] meat = {"beef", "pork", "salmon", "turkey", "lamb", "duck", "boar"};
    private final String[] vegetable = {"beans", "carrot", "inger", "onion", "pea", "corn", "celery"};
    private final String[] kinds = {"meat", "vegetable"};
    private final String[][] all = {meat, vegetable};
    private final int chance = 6;
    private gameListener listener;
    int kind, index, count;
    String underscoreWord, currentWord, hint;
    StringBuilder stringBuilder;
    TextView textView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Game() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Game.
     */
    // TODO: Rename and change types and number of parameters
    public static Game newInstance(String param1, String param2) {
        Game fragment = new Game();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);
        textView = view.findViewById(R.id.text);
        startUp();
        return view;
    }

    private void startUp() {
        count = 0;
        kind = getRandomNumber(2);
        index = getRandomNumber(7);
        hint = kinds[kind];
        currentWord = all[kind][index];
        stringBuilder = new StringBuilder();
        for (int i = 0; i < currentWord.length(); i++) {
            stringBuilder.append("_");
            stringBuilder.append(" ");
        }
        underscoreWord = stringBuilder.toString();
        textView.setText(underscoreWord);
        stringBuilder.setLength(0);
        System.out.println("Word: " + currentWord);
    }

    private int getRandomNumber(int max) {
        return (int) ((Math.random() * (max)));
    }

    public interface gameListener {
        void getResult(String input);
    }

    public void mainActivityButtonInput(String input) {
        count++;
        if (currentWord.contains(input)) {
            char in = input.charAt(0);
            stringBuilder.append(underscoreWord);
            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == in) {
                    stringBuilder.setCharAt(i * 2, in);
                }
            }
            underscoreWord = stringBuilder.toString();
            textView.setText(underscoreWord);
            stringBuilder.setLength(0);
            if (!underscoreWord.contains("_")) {
                Toast.makeText(this.getContext(), "You Win!", Toast.LENGTH_SHORT).show();
            }
        }

        if (count == chance && underscoreWord.contains("_")) {
            Toast.makeText(this.getContext(), "You Lose!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof gameListener)
            listener = (gameListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}