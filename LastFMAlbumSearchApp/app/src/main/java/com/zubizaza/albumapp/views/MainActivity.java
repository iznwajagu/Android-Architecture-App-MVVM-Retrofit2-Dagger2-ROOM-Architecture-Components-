package com.zubizaza.albumapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.zubizaza.albumapp.R;

public class MainActivity extends AppCompatActivity implements AlbumListFragment.OnAlbumSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AlbumListFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onAlbumSelected(String albumId) {

        AlbumDetailFragment albumDetailFragment =
                (AlbumDetailFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.album_detail_container);
        if(albumDetailFragment != null){

        }else{
            AlbumDetailFragment newAlbumDetailFragment = new AlbumDetailFragment();
            Bundle args = new Bundle();
            args.putString(AlbumDetailFragment.ARG_ALBUM_PARAM, albumId);
            newAlbumDetailFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, newAlbumDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public void onAttachFragment(Fragment fragment){
        if (fragment instanceof AlbumListFragment){
            AlbumListFragment albumListFragment =(AlbumListFragment) fragment;
            albumListFragment.setOnAlbumSelectedListener(this);
        }

    }

}
