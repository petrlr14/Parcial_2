package com.pdm00057616.gamenews.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.R;

public class ConfirmationFragment extends Fragment {

    private TextInputEditText editTextUser, editTextPassword;
    private Button button;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        bindViews(view);
        return view;
    }

    private void bindViews(View view) {
        editTextUser = view.findViewById(R.id.edit_text_username);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        button = view.findViewById(R.id.login_button);
        relativeLayout = view.findViewById(R.id.relative_progress);
        progressBar = view.findViewById(R.id.login_progress);
        button.setText("CHANGE PASSWORD");
        button.setOnClickListener(v -> buttonAction());
    }

    private void buttonAction() {
        if (editTextUser.getText().toString().equals("") || editTextPassword.getText().toString().equals("")) {
            Toast.makeText(context, "Fields Must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        ClientRequest.login(editTextUser.getText().toString(), editTextPassword.getText().toString(), (Activity) context, relativeLayout, progressBar, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        relativeLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        editTextPassword.setText("");
        editTextUser.setText("");
    }
}
