package com.ikhlast.warehouseipb.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ikhlast.warehouseipb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    WebView wv;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wv = view.findViewById(R.id.webv);
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(Uri.parse("https://metrikgfm.com").toString());
    }
}
