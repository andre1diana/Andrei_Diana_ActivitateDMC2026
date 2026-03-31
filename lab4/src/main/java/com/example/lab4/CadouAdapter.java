package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CadouAdapter extends ArrayAdapter<Cadou> {

    public CadouAdapter(Context context, List<Cadou> lista) {
        super(context, 0, lista);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_cadou, parent, false);
        }

        Cadou cadou = getItem(position);

        TextView tvMessage = convertView.findViewById(R.id.tvItemMessage);
        TextView tvDetails = convertView.findViewById(R.id.tvItemDetails);

        if (cadou != null) {
            tvMessage.setText(cadou.getMessage());
        }
        tvMessage.setText(cadou.getMessage());

        String data = "-";
        if (cadou.getDeliveryDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            data = sdf.format(cadou.getDeliveryDate());
        }

        tvDetails.setText(
                "• Greutate: " + cadou.getWeight() + "\n" +
                        "• Tip: " + cadou.getObjectType() + "\n" +
                        "• Data: " + data
        );

        return convertView;
    }
}
