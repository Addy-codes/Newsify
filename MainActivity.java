package com.example.newsify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{

    //552d330012be42ed99a5053de4f43a8a
    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPS;
    private ArrayList<Articles>articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById((R.id.idRVCategories));
        loadingPS=findViewById(R.id.idPBLoading);
        articlesArrayList=new ArrayList<>();
        categoryRVModalArrayList=new ArrayList<>();
        newsRVAdapter=new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories()
    {
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://images.unsplash.com/photo-1498644035638-2c3357894b10?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8am91cm5hbGlzbXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8dGVjaHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1576086213369-97a306d36557?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8c2NpZW5jZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://images.unsplash.com/photo-1510925758641-869d353cecc7?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fHNwb3J0c3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8bmV3c3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://images.unsplash.com/photo-1460925895917-afdab827c52f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8YnVzaW5lc3N8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://images.unsplash.com/photo-1499364615650-ec38552f4f34?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://images.unsplash.com/photo-1530497610245-94d3c16cda28?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=1000&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPS.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL ="https://newsapi.org/v2/top-headlines?country=in&category="+ category +"&apikey=552d330012be42ed99a5053de4f43a8a";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=552d330012be42ed99a5053de4f43a8a";
        String BASE_URL= "https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI= retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);}
        else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal= response.body();
                loadingPS.setVisibility(View.GONE);
                ArrayList<Articles>articles = newsModal.getArticles();
                for(int i=0;i<articles.size();i++)
                {
                    articlesArrayList.add(new Articles(articles.get(i).getTittle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed to get news",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCategoryClick(int position) {
        String category=categoryRVModalArrayList.get(position).getCategory();

        getNews(category);

    }
}