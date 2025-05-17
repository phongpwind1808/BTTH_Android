package com.example.lab14_2_tabselector_music;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabHost tabHost;
    private ListView listViewTab1, listViewTab2, listViewTab3;
    private EditText editTextSearch1, editTextSearch2, editTextSearch3;

    private ArrayList<Song> songsTab1, songsTab2, songsTab3;
    private ArrayList<Song> filteredSongsTab1, filteredSongsTab2, filteredSongsTab3;
    private SongAdapter adapterTab1, adapterTab2, adapterTab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo TabHost
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        // Tạo Tab 1
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator(getString(R.string.tab1_name));
        tabHost.addTab(tab1);

        // Tạo Tab 2
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator(getString(R.string.tab2_name));
        tabHost.addTab(tab2);

        // Tạo Tab 3
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator(getString(R.string.tab3_name));
        tabHost.addTab(tab3);

        // Khởi tạo Views
        listViewTab1 = findViewById(R.id.listViewTab1);
        listViewTab2 = findViewById(R.id.listViewTab2);
        listViewTab3 = findViewById(R.id.listViewTab3);

        editTextSearch1 = findViewById(R.id.editTextSearch1);
        editTextSearch2 = findViewById(R.id.editTextSearch2);
        editTextSearch3 = findViewById(R.id.editTextSearch3);

        // Khởi tạo danh sách bài hát cho mỗi tab
        initSongLists();

        // Thiết lập adapter cho các ListView
        setupListViews();

        // Thiết lập sự kiện tìm kiếm
        setupSearchListeners();
    }

    private void initSongLists() {
        // Khởi tạo danh sách bài hát cho Tab 1
        songsTab1 = new ArrayList<>();
        songsTab1.add(new Song("52300", getString(R.string.song_52300)));
        songsTab1.add(new Song("52600", getString(R.string.song_52600)));
        songsTab1.add(new Song("52567", getString(R.string.song_52567)));
        filteredSongsTab1 = new ArrayList<>(songsTab1);

        // Khởi tạo danh sách bài hát cho Tab 2
        songsTab2 = new ArrayList<>();
        songsTab2.add(new Song("57236", getString(R.string.song_57236)));
        songsTab2.add(new Song("51548", getString(R.string.song_51548)));
        songsTab2.add(new Song("51748", getString(R.string.song_51748)));
        filteredSongsTab2 = new ArrayList<>(songsTab2);

        // Khởi tạo danh sách bài hát cho Tab 3
        songsTab3 = new ArrayList<>();
        songsTab3.add(new Song("57689", getString(R.string.song_57689)));
        songsTab3.add(new Song("58716", getString(R.string.song_58716)));
        songsTab3.add(new Song("58916", getString(R.string.song_58916)));
        filteredSongsTab3 = new ArrayList<>(songsTab3);
    }

    private void setupListViews() {
        // Thiết lập adapter cho Tab 1
        adapterTab1 = new SongAdapter(this, filteredSongsTab1);
        listViewTab1.setAdapter(adapterTab1);

        // Thiết lập adapter cho Tab 2
        adapterTab2 = new SongAdapter(this, filteredSongsTab2);
        listViewTab2.setAdapter(adapterTab2);

        // Thiết lập adapter cho Tab 3
        adapterTab3 = new SongAdapter(this, filteredSongsTab3);
        listViewTab3.setAdapter(adapterTab3);
    }

    private void setupSearchListeners() {
        // Thiết lập chức năng tìm kiếm cho Tab 1
        editTextSearch1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSongs(s.toString(), songsTab1, filteredSongsTab1, adapterTab1);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Thiết lập chức năng tìm kiếm cho Tab 2
        editTextSearch2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSongs(s.toString(), songsTab2, filteredSongsTab2, adapterTab2);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Thiết lập chức năng tìm kiếm cho Tab 3
        editTextSearch3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSongs(s.toString(), songsTab3, filteredSongsTab3, adapterTab3);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Lọc bài hát theo từ khóa
    private void filterSongs(String keyword, ArrayList<Song> originalList, ArrayList<Song> filteredList, SongAdapter adapter) {
        filteredList.clear();

        if (keyword.isEmpty()) {
            // Nếu từ khóa rỗng, hiển thị tất cả bài hát
            filteredList.addAll(originalList);
        } else {
            // Lọc bài hát theo tên hoặc ID chứa từ khóa
            keyword = keyword.toLowerCase();
            for (Song song : originalList) {
                if (song.getName().toLowerCase().contains(keyword) ||
                        song.getId().toLowerCase().contains(keyword)) {
                    filteredList.add(song);
                }
            }
        }

        // Cập nhật adapter
        adapter.notifyDataSetChanged();
    }
}