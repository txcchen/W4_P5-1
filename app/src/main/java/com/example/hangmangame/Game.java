package com.example.hangmangame;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

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
    private final String[] vegetable = {"bean", "carrot", "ginger", "onion", "pea", "corn", "celery"};
    private final String[] kinds = {"meat", "vegetable"};
    private final String[][] all = {meat, vegetable};
    private gameListener listener;

    int chance = 6;

    int kind, index, count, correct;
    String currentWord, hint;
    LinearLayout linearLayout;
    ImageView imageView;

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
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if(savedInstanceState != null){
            getRetainInstance();
            currentWord = savedInstanceState.getString("word");
            count = savedInstanceState.getInt("count");
            correct = savedInstanceState.getInt("count");
            hint = savedInstanceState.getString("hint");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState ==null){
            view = inflater.inflate(R.layout.fragment_game, container, false);
            imageView = view.findViewById(R.id.progress);
            linearLayout = view.findViewById(R.id.words);
            startUp();
        }
        return view;
    }

    private void startUp() {
        count = 0;
        correct = 0;
        kind = getRandomNumber(2);
        index = getRandomNumber(7);
        hint = kinds[kind];
        currentWord = all[kind][index];
        for (int i = 0; i < currentWord.length(); i++) {
            TextView textView = new TextView(this.getContext());
            textView.setId(i);
            textView.setTextSize(40);
            SpannableString content = new SpannableString("  ");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            textView.setText(content);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            textView.setLayoutParams(params);
            linearLayout.addView(textView);
        }
        System.out.println("Word: " + currentWord);
    }

    private int getRandomNumber(int max) {
        return (int) ((Math.random() * (max)));
    }

    public interface gameListener {
        void getResult(String input);
    }

    public void mainActivityButtonInput(String input) {
        if (currentWord.contains(input)) {
            char in = input.charAt(0);
            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == in) {
                    TextView textView = view.findViewById(i);
                    SpannableString content = new SpannableString(String.valueOf(in).toUpperCase());
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    Log.d("underline", content.toString());
                    textView.setText(content);
                    correct++;
                }
            }
            if (count <= chance && correct == currentWord.length()) {
                Toast.makeText(this.getContext(), "You Win!", Toast.LENGTH_SHORT).show();
                listener.getResult("finish");
            }
        } else {
            if (!(input.equals("Hint")))
                drawHangman(chance);
        }
    }

    public void drawHangman(int chance){
        count++;
        switch (count) {
            case 1:
                imageView.setImageResource(R.drawable.img_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.img_2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.img_3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.img_4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.img_5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.img_6);
                break;
        }
        if (count == chance) {
            Toast.makeText(this.getContext(), "You Lose!", Toast.LENGTH_SHORT).show();
            listener.getResult("finish");
        }
    }

    public void newGame() {
        linearLayout.removeAllViews();
        imageView.setImageResource(R.drawable.img_0);
        startUp();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("word", currentWord);
        outState.putString("hint", hint);
        outState.putInt("count", count);
        outState.putInt("correct",correct);
        super.onSaveInstanceState(outState);
    }

}



