package sventrapopizz.amoledblackthemeshowcase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NavigationOtherThemesFragment extends Fragment {
    public static boolean otherIsInFront;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_other, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        otherIsInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        otherIsInFront = false;
    }
}
