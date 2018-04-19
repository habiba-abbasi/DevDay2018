package dev18.noobs.com.noobsdev18;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev18.noobs.com.noobsdev18.databinding.FragmentWelcomeBinding;
import dev18.noobs.com.noobsdev18.model.UserModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WelcomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    ProgressDialog mProgress;

    String skill = null;

    FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_welcome, container, false);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
        mProgress = new ProgressDialog(getContext());
        mProgress.setMessage("Uploading");


        setupListeners();

        return binding.getRoot();
    }

    private void setupListeners() {
        binding.tick.setOnClickListener(this);
        binding.batting.setOnClickListener(this);
        binding.bowling.setOnClickListener(this);
        binding.keeping.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        if (view == binding.tick) {

            mProgress.show();

            if (binding.screenNameEditText.length() > 0 &&
                    skill != null &&
                    binding.favoriteNet.length() > 0
                    ) {

                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mProgress.dismiss();
                        if (dataSnapshot.exists()) {

                            UserModel model = dataSnapshot.getValue(UserModel.class);

                            model.setFavoriteNet(binding.favoriteNet.getText().toString());
                            model.setSkill(skill.toString());
                            model.setScreenName(binding.screenNameEditText.getText().toString());

                            mRef.setValue(model);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mProgress.dismiss();
                    }
                });
            } else {
                mProgress.dismiss();
                Toast.makeText(getContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }


        } else if (binding.bowling == view) {
            skill = binding.bowling.getText().toString();
            Toast.makeText(getContext(), "Bowling selected", Toast.LENGTH_SHORT).show();
        } else if (binding.batting == view) {
            skill = binding.batting.getText().toString();
            Toast.makeText(getContext(), "Batting selected", Toast.LENGTH_SHORT).show();
        } else if (binding.keeping == view) {
            skill = binding.keeping.getText().toString();
            Toast.makeText(getContext(), "Keeping selected", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        int GO_NETS = 0;

        // TODO: Update argument type and name
        void onWelcomeFragmentInteraction(int i);
    }
}
