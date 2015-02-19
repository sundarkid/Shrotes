package shrotes.tyrdevs.com.shrotes;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


public class QrCode extends ActionBarActivity {

    ImageView imageViewQrCode;
    EditText editTextQrText;
    Button generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        init();

    }

    private void init() {

        imageViewQrCode = (ImageView) findViewById(R.id.imageViewQrCode);
        editTextQrText = (EditText) findViewById(R.id.editTextQrText);
        generate = (Button) findViewById(R.id.buttonGenerate);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The string to generate the QR code for.
                String qrInputText = editTextQrText.getText().toString(); // Qr string

                //Find screen size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3/4;

                //Encode with a QR Code image
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);
                try {
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    imageViewQrCode.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }




}
