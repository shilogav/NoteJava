package com.shilo.notejava.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.shilo.notejava.MainActivity;
import com.shilo.notejava.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ColorDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.choose_color_lay_color,null);

        builder.setView(view)
                .setTitle("pick up note color");

        ////////////////////////////////////////
        /*
        buttons
         */
        Button buttonPurple = view.findViewById(R.id.purple_button);
        Button buttonGreen = view.findViewById(R.id.green_button);
        Button buttonBlue = view.findViewById(R.id.blue_button);
        Button buttonOrange = view.findViewById(R.id.orange_button);
        Button buttonYellow = view.findViewById(R.id.yellow_button);
        Button buttonRed = view.findViewById(R.id.red_button);
        Button buttonWhite = view.findViewById(R.id.white_button);
        //
        View.OnClickListener listener = (View.OnClickListener) getActivity();
        buttonPurple.setOnClickListener(listener);
        buttonGreen.setOnClickListener(listener);
        buttonBlue.setOnClickListener(listener);
        buttonOrange.setOnClickListener(listener);
        buttonYellow.setOnClickListener(listener);
        buttonRed.setOnClickListener(listener);
        buttonWhite.setOnClickListener(listener);
        //////////////////////////////////////////



        return builder.create();
    }
}
