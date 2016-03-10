package com.keyrelations.suggestmesomemovies;


import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyLibraryAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movies;
    private Context mContext;

    public MyLibraryAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        //AssetManager mngr = context.getAssets();
        movies = objects;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.mylibrary_list,parent,false);
        }

        final Movie movie = movies.get(position);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

        final Button suggestButton = (Button) convertView.findViewById(R.id.buttonEditSuggestion);
        suggestButton.setTypeface(font);

        if(movie.getIsSuggested().equals("Y")){
            suggestButton.setTextColor(ContextCompat.getColor(mContext,R.color.colorMovieSuggested));
        }
        else{
            suggestButton.setTextColor(ContextCompat.getColor(mContext,R.color.colorMovieUnSuggested));
        }

        suggestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mContext instanceof HomeActivity) {
                    if(movie.getIsSuggested().equals("Y")){
                        movie.putIsSuggested("N");
                        //suggestButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorMovieUnSuggested));
                        ((HomeActivity) mContext).editMovieSuggestion(movie.getId(), "0");
                    }
                    else{
                        movie.putIsSuggested("Y");
                        //suggestButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorMovieSuggested));
                        ((HomeActivity) mContext).editMovieSuggestion(movie.getId(), "1");
                    }
                }
            }
        });

        TextView text1 = (TextView) convertView.findViewById(R.id.textViewMovieName);
        text1.setText(movie.getTitle());

        TextView text2 = (TextView) convertView.findViewById(R.id.textViewMovieReleaseYear);
        text2.setText(movie.getReleaseYear());

        TextView text3 = (TextView) convertView.findViewById(R.id.textViewIMDBRating);
        text3.setText(movie.getImdbRating());

        TextView text4 = (TextView) convertView.findViewById(R.id.textViewSuggestedCnt);
        text4.setTypeface(font);
        text4.setText(getContext().getString(R.string.icon_people)+" "+movie.getSuggestedCount());

        Uri uri = Uri.parse("http://image.tmdb.org/t/p/w342/" + movie.getPosterPath());
        SimpleDraweeView draweeView = (SimpleDraweeView) convertView.findViewById(R.id.imageViewMoviePoster);

        if(movie.getPosterPath()!=null) {
            draweeView.getHierarchy().setProgressBarImage(new ImageLoadProgressBar());
            draweeView.setImageURI(uri);
        }
        else{
            draweeView.getHierarchy().setPlaceholderImage(R.drawable.noimage);
        }
        return convertView;
    }
}
