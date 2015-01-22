package net.vannadis.testme.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.vannadis.testme.R;
import net.vannadis.testme.model.User;

/**
 * Created by viktoriala on 9/24/2014.
 */
public class UserProfileFragment extends Fragment {
    private User user;

    public static UserProfileFragment newInstance(User user) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("User", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_simple_text, container, false);
        TextView name = (TextView) rootView.findViewById(R.id.textview);
        if (user == null){
            user = new User("shit", "happens");
        }
        name.setText(Html.fromHtml(getString(R.string.user_profile_text, user.getFirstName(), user.getLastName())));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        user = getArguments().getParcelable("User");
    }

}