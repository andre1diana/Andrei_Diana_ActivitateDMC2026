package mta.computers.lab9;

import android.graphics.Bitmap;

public class CatImage {
    private String imageUrl;
    private String description;
    private String webUrl;
    private Bitmap bitmap;

    public CatImage(String imageUrl, String description, String webUrl) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.webUrl = webUrl;
    }

    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public String getWebUrl() { return webUrl; }
    public Bitmap getBitmap() { return bitmap; }
    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
}