package com.zubizaza.albumapp.views;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zubizaza.albumapp.AlbumApp;
import com.zubizaza.albumapp.R;
import com.zubizaza.albumapp.data.model.Album;
import com.zubizaza.albumapp.viewmodels.AlbumViewModel;

import java.util.List;

import javax.inject.Inject;

public class AlbumListFragment extends Fragment implements AdapterCallback {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RecyclerView mRecyclerView;
    private TextView mMessageHolder;
    private AlbumListAdapter mAlbumListAdapter;
    private OnAlbumSelectedListener albumSelectedListener;
    private EditText mSearchInputView;
    private AlbumViewModel mViewModel;

    private View mProgressBar;

    void setOnAlbumSelectedListener(OnAlbumSelectedListener albumSelectedListener){
        this.albumSelectedListener = albumSelectedListener;
    }

    static AlbumListFragment newInstance() {
        return new AlbumListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.album_list_fragment, container, false);

        mSearchInputView = rootView.findViewById(R.id.search_box);

        Button mSearchButton = rootView.findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(v -> startSearch());

        mMessageHolder = rootView.findViewById(R.id.empty_placeholder);

        mProgressBar = rootView.findViewById(R.id.progress_circular);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration( new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerView.setNestedScrollingEnabled(false);

        mAlbumListAdapter = new AlbumListAdapter(getContext(), this);

        mRecyclerView.setAdapter(mAlbumListAdapter);


        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AlbumApp) getActivity().getApplication()).getAppComponent().injectFragment(this);

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumViewModel.class);

        /*mViewModel.getAlbumList().observe(getViewLifecycleOwner(), albumList -> {
            setAdapter(albumList);
        });

        mViewModel.getStatusMessage().observe(getViewLifecycleOwner(), message ->{
            showMessage(message);
        }); */

        mViewModel.getAlbumList().observe(getViewLifecycleOwner(), this::setAdapter);

        mViewModel.getStatusMessage().observe(getViewLifecycleOwner(), this::showMessage);

        mViewModel.progressBarState.observe(getViewLifecycleOwner(), progressbarStatus -> {
            mProgressBar.setVisibility(View.INVISIBLE);
        });

    }


    private void startSearch(){
        String albumName = mSearchInputView.getText().toString();
        mViewModel.searchForAlbum(albumName);
    }


    private void setAdapter(List<Album> albumList) {

        mAlbumListAdapter.swapAlbumList(albumList);
        mRecyclerView.setVisibility(View.VISIBLE);
        mMessageHolder.setVisibility(View.INVISIBLE);

    }


    private void showMessage(String message) {

        Snackbar.make(getView().getRootView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

    }


    @Override
    public void onAdapterAlbumSelected(String albumId) {
        this.albumSelectedListener.onAlbumSelected(albumId);
    }




    public interface OnAlbumSelectedListener{
         void onAlbumSelected(String albumId);
    }


}
