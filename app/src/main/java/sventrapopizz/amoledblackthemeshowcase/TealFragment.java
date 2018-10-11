package sventrapopizz.amoledblackthemeshowcase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TealFragment extends Fragment {
    public static boolean tealIsInFront;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teal, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        tealIsInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        tealIsInFront = false;
    }
}
