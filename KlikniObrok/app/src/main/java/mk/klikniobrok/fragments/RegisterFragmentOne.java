package mk.klikniobrok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.klikniobrok.R;
import mk.klikniobrok.fragments.listeners.OnFragmentChangeListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;

/**
 * Created by gjorgjim on 11/27/16.
 */

public class RegisterFragmentOne extends Fragment {
    OnFragmentChangeListener fragmentChangeListener;
    UserManagementListener userManagementListener;
    TypefaceChangeListener typefaceChangeListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentChangeListener = (OnFragmentChangeListener) context;
        userManagementListener = (UserManagementListener) context;
        typefaceChangeListener = (TypefaceChangeListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_layout_one, container, false);

        final TextInputEditText firstName = (TextInputEditText) view.findViewById(R.id.registerFirstName);
        final TextInputEditText lastName = (TextInputEditText) view.findViewById(R.id.registerLastName);
        final TextInputEditText username = (TextInputEditText) view.findViewById(R.id.registerUsername);
        AppCompatTextView alreadyRegistered = (AppCompatTextView) view.findViewById(R.id.alreadyRegistered);

        typefaceChangeListener.changeTypeface("fonts/RobotoSlab-Regular.ttf", alreadyRegistered);

        AppCompatButton nextButton = (AppCompatButton)view.findViewById(R.id.next);
        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstName.getText().toString().isEmpty()
                        && !lastName.getText().toString().isEmpty()
                        && !username.getText().toString().isEmpty()) {
                    userManagementListener.saveUserInfo(firstName.getText().toString(), lastName.getText().toString(), username.getText().toString());
                    fragmentChangeListener.onFragmentChanged(RegisterFragmentOne.class, true);
                } else {
                    //TODO: Implement error on empty fields
                }
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentChangeListener.onFragmentChanged(RegisterFragmentOne.class, false);
            }
        });


        return view;
    }
}
