package mk.klikniobrok.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import mk.klikniobrok.MainActivity;
import mk.klikniobrok.R;

import mk.klikniobrok.fragments.listeners.OnFragmentChangeListener;
import mk.klikniobrok.fragments.listeners.TypefaceChangeListener;
import mk.klikniobrok.fragments.listeners.UserManagementListener;
import mk.klikniobrok.models.User;

/**
 * Created by gjorgjim on 11/27/16.
 */

public class LoginFragment extends Fragment {
    private TypefaceChangeListener typefaceChangeListener;
    private UserManagementListener userManagementListener;
    private OnFragmentChangeListener fragmentChangeListener;
    private CallbackManager callbackManager;
    private String email;
    private User user = new User();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);
        Log.d("login", "login");
        FacebookSdk.sdkInitialize(getContext());

        final AppCompatButton login = (AppCompatButton) view.findViewById(R.id.loginButton);
        callbackManager = CallbackManager.Factory.create();
        LoginButton fbLoginButton = (LoginButton)view.findViewById(R.id.fbLoginButton);
        fbLoginButton.setFragment(this);
        fbLoginButton.setPadding(8, 24, 8, 24);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            fbLoginButton.setBackgroundResource(R.drawable.ripple_fb);
            fbLoginButton.setElevation(4);
        }

        AppCompatTextView register = (AppCompatTextView) view.findViewById(R.id.register);
        AppCompatTextView forgotPassword = (AppCompatTextView) view.findViewById(R.id.forgotPwText);


        typefaceChangeListener.changeTypeface("fonts/RobotoSlab-Regular.ttf", register, forgotPassword);


        final TextInputEditText username = (TextInputEditText) view.findViewById(R.id.loginUsername);
        final TextInputEditText password = (TextInputEditText) view.findViewById(R.id.loginPassword);

        fbLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                user.setFirstName(profile.getFirstName());
                user.setLastName(profile.getLastName());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    user.setEmail(response.getJSONObject().get("email").toString());
                                    userManagementListener.loginFbUser(user);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("cancel", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("error", "error");
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(getActivity().getCurrentFocus().getWindowToken() != null) {
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        login.callOnClick();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    userManagementListener.loginUser(username.getText().toString(), password.getText().toString());
                } else {
                    if(username.getText().toString().isEmpty()) {
                        username.setError(getResources().getString(R.string.emptyField));
                    }
                    if(password.getText().toString().isEmpty()) {
                        password.setError(getResources().getString(R.string.emptyField));
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentChangeListener.onFragmentChanged(LoginFragment.class, false);
            }
        });

        typefaceChangeListener.changeTypeface("fonts/Exo2-ExtraLight.otf", login, fbLoginButton);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        typefaceChangeListener = (TypefaceChangeListener) context;
        userManagementListener = (UserManagementListener) context;
        fragmentChangeListener = (OnFragmentChangeListener) context;
    }

}
