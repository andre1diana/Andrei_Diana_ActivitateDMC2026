package mta.computers.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Locale;

public class BarChartView extends View {
    private float[] values;
    private final Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF barRect = new RectF();

    // Paleta de culori Material (Vibrante)
    private final int[] chartColors = {
            Color.parseColor("#EF5350"), // Rosu
            Color.parseColor("#66BB6A"), // Verde
            Color.parseColor("#42A5F5"), // Albastru
            Color.parseColor("#FFA726"), // Portocaliu
            Color.parseColor("#AB47BC")  // Mov
    };

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        barPaint.setStyle(Paint.Style.FILL);

        // Culori fixe pentru Light Theme (Fundal alb)
        textPaint.setColor(Color.parseColor("#212121"));
        textPaint.setTextSize(spToPx(12));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        axisPaint.setColor(Color.parseColor("#424242"));
        axisPaint.setStrokeWidth(5f);

        gridPaint.setColor(Color.parseColor("#E0E0E0"));
        gridPaint.setStrokeWidth(2f);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setPathEffect(new DashPathEffect(new float[]{15, 15}, 0));
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    public void setValues(float[] values) {
        this.values = values;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Fundal alb curat forat din cod
        canvas.drawColor(Color.WHITE);

        if (values == null || values.length == 0) return;

        float width = getWidth();
        float height = getHeight();
        float paddingLeft = 140f;
        float paddingBottom = 140f;
        float paddingTop = 120f;
        float paddingRight = 60f;

        float chartWidth = width - paddingLeft - paddingRight;
        float chartHeight = height - paddingTop - paddingBottom;
        
        float maxValue = 0;
        for (float val : values) {
            if (val > maxValue) maxValue = val;
        }
        if (maxValue == 0) maxValue = 1;

        // Desenare linii orizontale (Grid) si valori Y
        int gridLines = 5;
        for (int i = 0; i <= gridLines; i++) {
            float y = paddingTop + chartHeight - (chartHeight * i / gridLines);
            if (i > 0) {
                canvas.drawLine(paddingLeft, y, width - paddingRight, y, gridPaint);
            }
            // Valori axa Y
            float labelVal = (maxValue * i / gridLines);
            canvas.drawText(String.format(Locale.getDefault(), "%.1f", labelVal), paddingLeft - 60, y + 10, textPaint);
        }

        // Desenare axe
        canvas.drawLine(paddingLeft, height - paddingBottom, width - paddingRight, height - paddingBottom, axisPaint); // Axa X
        canvas.drawLine(paddingLeft, paddingTop, paddingLeft, height - paddingBottom, axisPaint); // Axa Y

        float totalBars = values.length;
        float barWidth = (chartWidth / totalBars) * 0.65f;
        float spacing = (chartWidth / totalBars) * 0.35f;

        for (int i = 0; i < values.length; i++) {
            float barHeight = (values[i] / maxValue) * chartHeight;
            
            barPaint.setColor(chartColors[i % chartColors.length]);

            float left = paddingLeft + (spacing / 2) + i * (barWidth + spacing);
            float top = paddingTop + chartHeight - barHeight;
            float right = left + barWidth;
            float bottom = height - paddingBottom;

            // Bare cu colturi rotunjite elegante si umbra
            barPaint.setShadowLayer(8, 4, 4, Color.argb(60, 0, 0, 0));
            barRect.set(left, top, right, bottom);
            canvas.drawRoundRect(barRect, 25, 25, barPaint);
            barPaint.clearShadowLayer();
            
            // Valoare afisata deasupra barei
            canvas.drawText(String.valueOf(values[i]), left + (barWidth / 2), top - 25, textPaint);
            
            // Eticheta sub bara (V1, V2...)
            canvas.drawText("V" + (i + 1), left + (barWidth / 2), bottom + 60, textPaint);
        }
        
        // Titlu grafic
        textPaint.setTextSize(spToPx(18));
        canvas.drawText("Grafic Valori Introduse", width / 2, paddingTop / 2 + 20, textPaint);
        textPaint.setTextSize(spToPx(12)); // Resetam pentru restul textelor
    }
}