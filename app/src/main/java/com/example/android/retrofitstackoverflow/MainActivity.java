package com.example.android.retrofitstackoverflow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.retrofitstackoverflow.models.Question;
import com.example.android.retrofitstackoverflow.models.StackOverflowQuestions;
import com.example.android.retrofitstackoverflow.retrofit.StackOverflowAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etTag;
    private ListView lvResult;

    private ProgressDialog progressDialog;
    private ArrayAdapter<Question> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTag = (EditText) findViewById(R.id.et_tag);
        lvResult = (ListView) findViewById(R.id.lv_result);

        arrayAdapter = new ArrayAdapter<Question>(this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                new ArrayList<Question>());
        lvResult.setAdapter(arrayAdapter);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Should reload
        search(null);

        return false;
    }

    public void search(View view) {
        String tagText = etTag.getText().toString();

        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);

        Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions(tagText);

        call.enqueue(new Callback<StackOverflowQuestions>() {
            @Override
            public void onResponse(Call<StackOverflowQuestions> call, Response<StackOverflowQuestions> response) {
                arrayAdapter = (ArrayAdapter<Question>) lvResult.getAdapter();
                arrayAdapter.clear();
                arrayAdapter.addAll(response.body().items);
            }

            @Override
            public void onFailure(Call<StackOverflowQuestions> call, Throwable t) {
                Toast.makeText(
                        MainActivity.this,
                        "Error! " + t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        progressDialog.dismiss();
    }
}