package com.zubizaza.albumapp.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.zubizaza.albumapp.AlbumApp;
import com.zubizaza.albumapp.R;
import com.zubizaza.albumapp.data.model.Album;
import com.zubizaza.albumapp.viewmodels.AlbumViewModel;

import java.util.Objects;

import javax.inject.Inject;

public class AlbumDetailFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    static final String ARG_ALBUM_PARAM = "album_id";

    private View mProgressBar;
    private TextView mMessageHolder;
    private TextView mAlbumNameView;
    private TextView mArtistNameView;
    private TextView mAlbumURLView;
    private String mAlbumParam;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbumParam = getArguments().getString(ARG_ALBUM_PARAM);
            Log.d("passed album ID", mAlbumParam);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AlbumApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent().injectFragment(this);

        AlbumViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumViewModel.class);

        mViewModel.getAlbum(mAlbumParam).observe(getViewLifecycleOwner(), album -> {
            setView(album);
        });

        mViewModel.getStatusMessage().observe(getViewLifecycleOwner(), message ->{
            showMessage(message);
        });

        mViewModel.progressBarState.observe(getViewLifecycleOwner(), progressbarStatus -> {
            mProgressBar.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_album_detail, container, false);
        mMessageHolder = rootView.findViewById(R.id.empty_placeholder);

        mAlbumNameView = rootView.findViewById(R.id.album_name);
        mArtistNameView = rootView.findViewById(R.id.artist_name);
        mAlbumURLView = rootView.findViewById(R.id.album_url);

        mProgressBar = rootView.findViewById(R.id.progress_circular);

        return rootView;
    }


    void setView(Album album){

        String mAlbumName =  String.format(this.getResources().getString(R.string.album_title), album.getName());
        mAlbumNameView.setText(mAlbumName);

        String mArtistName =  String.format(this.getResources().getString(R.string.artist_name), album.getArtist());
        mArtistNameView.setText(mArtistName);

        String mAlbumURL =  String.format(this.getResources().getString(R.string.album_url), album.getUrl());
        mAlbumURLView.setText(mAlbumURL);

    }


    private void showMessage(String message) {

        Snackbar.make(getView().getRootView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

    }


}
