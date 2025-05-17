package com.example.lab14_2_tabselector_music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private Context context;
    private ArrayList<Song> songs;

    public SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, R.layout.song_item, songs);
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.song_item, parent, false);

            holder = new ViewHolder( );
            holder.textViewSongId = convertView.findViewById(R.id.textViewSongId);
            holder.textViewSongName = convertView.findViewById(R.id.textViewSongName);
            holder.imageButtonFavorite = convertView.findViewById(R.id.imageButtonFavorite);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag( );
        }

        // Lấy bài hát tại vị trí position
        final Song song = songs.get(position);

        // Thiết lập dữ liệu cho item
        holder.textViewSongId.setText(song.getId( ));
        holder.textViewSongName.setText(song.getName( ));

        // Thiết lập icon trái tim (yêu thích)
        updateFavoriteButton(holder.imageButtonFavorite, song.isFavorite( ));

        // Xử lý sự kiện khi nhấn nút yêu thích
        holder.imageButtonFavorite.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                song.toggleFavorite( );
                updateFavoriteButton((ImageButton) v, song.isFavorite( ));
            }
        });

        return convertView;
    }

    // Cập nhật icon trái tim theo trạng thái yêu thích
    private void updateFavoriteButton(ImageButton button, boolean isFavorite) {
        if (isFavorite) {
            button.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    // ViewHolder để tối ưu hiệu suất ListView
    private static class ViewHolder {
        TextView textViewSongId;
        TextView textViewSongName;
        ImageButton imageButtonFavorite;
    }
}