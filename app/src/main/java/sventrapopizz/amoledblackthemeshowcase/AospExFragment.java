package sventrapopizz.amoledblackthemeshowcase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AospExFragment extends Fragment {
    public static boolean aospIsInFront;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aospex, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        aospIsInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        aospIsInFront = false;
    }
}
