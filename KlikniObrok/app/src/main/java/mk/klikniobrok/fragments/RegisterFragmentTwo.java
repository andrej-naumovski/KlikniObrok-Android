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

public class RegisterFragmentTwo extends Fragment {
    private UserManagementListener userManagementListener;
    private OnFragmentChangeListener onFragmentChangeListener;
    private TypefaceChangeListener typefaceChangeListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_layout_two, container, false);

        final TextInputEditText password = (TextInputEditText) view.findViewById(R.id.registerPassword);
        final TextInputEditText repeatPassword = (TextInputEditText) view.findViewById(R.id.registerRepeatPassword);
        final TextInputEditText email = (TextInputEditText) view.findViewById(R.id.registerEmail);
        AppCompatTextView alreadyRegistered = (AppCompatTextView) view.findViewById(R.id.alreadyRegistered);

        typefaceChangeListener.changeTypeface("fonts/RobotoSlab-Regular.ttf", alreadyRegistered);

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentChangeListener.onFragmentChanged(RegisterFragmentTwo.class, false);
            }
        });

        AppCompatButton registerButton = (AppCompatButton) view.findViewById(R.id.registerButton);
        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!password.getText().toString().isEmpty()
                        && !repeatPassword.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()) {
                    if(password.getText().toString().equals(repeatPassword.getText().toString())) {
                        userManagementListener.registerUser(password.getText().toString(), email.getText().toString());
                        onFragmentChangeListener.onFragmentChanged(RegisterFragmentTwo.class, true);

                    } else {
                        //TODO: Implement error on different password
                    }
                } else {
                    //TODO: Implement error on empty fields
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userManagementListener = (UserManagementListener) context;
        onFragmentChangeListener = (OnFragmentChangeListener) context;
        typefaceChangeListener = (TypefaceChangeListener) context;
    }
}
