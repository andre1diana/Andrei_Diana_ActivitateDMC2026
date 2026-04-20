package mta.computers.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_list);

        ListView lvCats = findViewById(R.id.lvCats);
        List<CatImage> catList = new ArrayList<>();

        catList.add(new CatImage(
                "https://hips.hearstapps.com/hmg-prod/images/smartest-cat-breeds-siamese-68a36c1e7add7.jpg",
                "Siamese Cat - Known for its intelligence and sleek look.",
                "https://en.wikipedia.org/wiki/Siamese_cat"));
        
        catList.add(new CatImage(
                "https://www.diamondpet.com/wp-content/uploads/2022/02/close-up-white-cat-with-blue-eyes-121224.jpg",
                "White Cat - Stunning blue eyes and pure white fur.",
                "https://en.wikipedia.org/wiki/White_cat"));

        catList.add(new CatImage(
                "https://pet-health-content-media.chewy.com/wp-content/uploads/2025/04/16180654/202503bec-spotted-cat-breed-bengal-1024x576.jpg",
                "Bengal Cat - Exotic spotted coat resembling a leopard.",
                "https://en.wikipedia.org/wiki/Bengal_cat"));

        catList.add(new CatImage(
                "https://www.trupanion.com/images/trupanionwebsitelibraries/pet-blogs/persian-cat-in-yard-1-.jpg",
                "Persian Cat - Famous for its long hair and flat face.",
                "https://en.wikipedia.org/wiki/Persian_cat"));

        catList.add(new CatImage(
                "https://images.saymedia-content.com/.image/t_share/MjA2ODU0NTcxMzkyMjQ2ODM1/the-11-rarest-cat-in-the-world.jpg",
                "Rare Breed - One of the rarest cat breeds globally.",
                "https://en.wikipedia.org/wiki/List_of_cat_breeds"));

        CatAdapter adapter = new CatAdapter(this, catList);
        lvCats.setAdapter(adapter);

        lvCats.setOnItemClickListener((parent, view, position, id) -> {
            CatImage selected = catList.get(position);
            Intent intent = new Intent(CatListActivity.this, CatWebViewActivity.class);
            intent.putExtra("URL", selected.getWebUrl());
            startActivity(intent);
        });
    }
}