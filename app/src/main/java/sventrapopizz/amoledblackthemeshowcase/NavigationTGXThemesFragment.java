package sventrapopizz.amoledblackthemeshowcase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NavigationTGXThemesFragment extends Fragment {
    public static boolean TGXIsInFront;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_tgxthemes, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        TGXIsInFront = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        TGXIsInFront = false;
    }
}
