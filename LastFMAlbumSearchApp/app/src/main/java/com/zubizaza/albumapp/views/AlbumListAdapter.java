package com.zubizaza.albumapp.views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zubizaza.albumapp.R;
import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumAdapterViewHolder> {

    private final Context mContext;
    private List<Album> mAlbum;
    private AdapterCallback mAdapterCallback;


    AlbumListAdapter(@NonNull Context context, AdapterCallback callback) {
        mContext = context;
        this.mAdapterCallback = callback;
    }


    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, viewGroup, false);
        view.setFocusable(true);
        return new AlbumAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AlbumAdapterViewHolder albumAdapterViewHolder, int position) {

        Album currentAlbum = mAlbum.get(position);

        albumAdapterViewHolder.titleView.setText(currentAlbum.getName());

        albumAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onAdapterAlbumSelected(currentAlbum.getMbid());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (null == mAlbum) return 0;
        return mAlbum.size();
    }


    void swapAlbumList(final List<Album> newAlbums) {
        if (mAlbum == null) {
            mAlbum = newAlbums;
            notifyDataSetChanged();
        } else {

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mAlbum.size();
                }

                @Override
                public int getNewListSize() {
                    return newAlbums.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mAlbum.get(oldItemPosition).getArtist() ==
                            newAlbums.get(newItemPosition).getArtist();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Album newC = newAlbums.get(newItemPosition);
                    Album oldC = mAlbum.get(oldItemPosition);
                    return newC.getArtist() == oldC.getArtist()
                            && newC.getArtist().equals(oldC.getArtist());
                }
            });
            mAlbum = newAlbums;
            result.dispatchUpdatesTo(this);
        }
    }




    class AlbumAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView titleView;

        AlbumAdapterViewHolder(View view) {

            super(view);
            titleView = view.findViewById(R.id.album_name);

        }

    }

}