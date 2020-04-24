package exam.defencepreparation.Login_SetUp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import dmax.dialog.SpotsDialog;
import exam.defencepreparation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Instagram_view_web extends Fragment {

    WebView webView;
    AlertDialog dialog;


    public Instagram_view_web() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_instagram_view_web, container, false);
        dialog = new SpotsDialog(getActivity());
        dialog.show();

     webView=(WebView)view.findViewById(R.id.webview);
     WebSettings webSettings = webView.getSettings();
     webSettings.setJavaScriptEnabled(true);
     webView.loadUrl("https://www.instagram.com/indian_army_inspirational/?hl=en");
     dialog.dismiss();
     return  view;

    }

}
