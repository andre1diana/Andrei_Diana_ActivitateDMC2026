package mta.computers.lab9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CatAdapter extends BaseAdapter {
    private Context context;
    private List<CatImage> catImages;
    private ExecutorService executorService;
    private Handler mainHandler;

    public CatAdapter(Context context, List<CatImage> catImages) {
        this.context = context;
        this.catImages = catImages;
        this.executorService = Executors.newFixedThreadPool(4);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int getCount() { return catImages.size(); }

    @Override
    public Object getItem(int position) { return catImages.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cat_item, parent, false);
        }

        CatImage currentCat = catImages.get(position);
        ImageView ivCat = convertView.findViewById(R.id.ivCat);
        TextView tvDescription = convertView.findViewById(R.id.tvCatDescription);

        tvDescription.setText(currentCat.getDescription());
        ivCat.setImageResource(android.R.drawable.ic_menu_gallery); // Placeholder

        if (currentCat.getBitmap() != null) {
            ivCat.setImageBitmap(currentCat.getBitmap());
        } else {
            executorService.execute(() -> {
                Bitmap bitmap = downloadImage(currentCat.getImageUrl());
                if (bitmap != null) {
                    currentCat.setBitmap(bitmap);
                    mainHandler.post(() -> notifyDataSetChanged());
                }
            });
        }

        return convertView;
    }

    private Bitmap downloadImage(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}