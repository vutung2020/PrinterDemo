package net.printer.printdemo16;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Printer;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.TaskCallback;
import net.posprinter.utils.BitmapProcess;
import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterTSC;

import java.util.ArrayList;
import java.util.List;

public class TscActivity extends AppCompatActivity implements View.OnClickListener{

    private Button content,text,barcode,qrcode,bitmap;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsc);
        initView();
    }

    private void initView(){
        content = findViewById(R.id.bt_tsp);
        text = findViewById(R.id.bt_text);
        barcode =findViewById(R.id.bt_barcode);
        qrcode = findViewById(R.id.bt_qr);
        bitmap = findViewById(R.id.bt_bitmap);

        content.setOnClickListener(this);
        text.setOnClickListener(this);
        barcode.setOnClickListener(this);
        qrcode.setOnClickListener(this);
        bitmap.setOnClickListener(this);
//        WebView myWebView = new WebView(this);
        webView = findViewById(R.id.webView);
        webView.setWebChromeClient( new MyWebChromeClient());
        webView.setWebViewClient( new webClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://digitalize.viettelpost.vn/DigitalizePrint/report.do?type=100&bill=QCajQ4cwyyNgqA0KszV2xhBCtPjviQK9UiuWSPw1FII=");
    }

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(newProgress);
        }
    }

    public class webClient extends WebViewClient {
        public boolean  shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bt_tsp:
                printContent();
                break;
            case R.id.bt_text:
                printText();
                break;
            case   R.id.bt_barcode:
                printBarcode();
                break;
            case R.id.bt_qr:
                printQR();
                break;
            case R.id.bt_bitmap:
                printbitmap();
                break;
        }

    }


    //打印文本
    private void printContent(){
        if (MainActivity.ISCONNECT){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_success),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFailed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_failed),Toast.LENGTH_SHORT).show();

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,30));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //线条
                    list.add(DataForSendToPrinterTSC.bar(10,10,200,3));
                    //条码
                    list.add(DataForSendToPrinterTSC.barCode(10,45,"128",100,1,0,2,2,"abcdef12345"));
                    //文本,简体中文是TSS24.BF2,可参考编程手册中字体的代号
                    list.add(DataForSendToPrinterTSC.text(220,10,"TSS24.BF2",0,1,1,"这是测试文本"));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            Toast.makeText(getApplicationContext(),getString(R.string.connect_first),Toast.LENGTH_SHORT).show();
        }
    }

    //打印文本
    private void printText(){

        if (MainActivity.ISCONNECT){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_success),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFailed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_failed),Toast.LENGTH_SHORT).show();

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,30));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //线条
//                    list.add(DataForSendToPrinterTSC.bar(10,10,200,3));
                    //条码
//                    list.add(DataForSendToPrinterTSC.barCode(10,15,"128",100,1,0,2,2,"abcdef12345"));
                    //文本
                    list.add(DataForSendToPrinterTSC.text(10,30,"TSS24.BF2",0,1,1,"abcasdjknf"));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            Toast.makeText(getApplicationContext(),getString(R.string.connect_first),Toast.LENGTH_SHORT).show();
        }

    }

    private void printBarcode(){
        if (MainActivity.ISCONNECT){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_success),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFailed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_failed),Toast.LENGTH_SHORT).show();

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,30));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //线条
//                    list.add(DataForSendToPrinterTSC.bar(10,10,200,3));
                    //条码
                    list.add(DataForSendToPrinterTSC.barCode(10,15,"128",100,1,0,2,2,"abcdef12345"));
                    //文本
//                    list.add(DataForSendToPrinterTSC.text(10,30,"1",0,1,1,"abcasdjknf"));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            Toast.makeText(getApplicationContext(),getString(R.string.connect_first),Toast.LENGTH_SHORT).show();
        }
    }

    private void printQR(){
        if (MainActivity.ISCONNECT){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_success),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFailed() {
                    Toast.makeText(getApplicationContext(),getString(R.string.send_failed),Toast.LENGTH_SHORT).show();

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,30));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //具体参数值请参看编程手册
                    list.add(DataForSendToPrinterTSC.qrCode(10,20,"M",3,"A",0,"M1","S3","123456789"));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            Toast.makeText(getApplicationContext(),getString(R.string.connect_first),Toast.LENGTH_SHORT).show();
        }
    }

    private void printbitmap(){

//        final Bitmap bitmap1 =  BitmapProcess.compressBmpByYourWidth
//                (BitmapFactory.decodeResource(getResources(), R.drawable.test),150);

        final Bitmap bitmap1 =  getBitmapOfWebView(webView);


        MainActivity.myBinder.WriteSendData(new TaskCallback() {
            @Override
            public void OnSucceed() {
                Toast.makeText(getApplicationContext(),getString(R.string.send_success),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void OnFailed() {
                Toast.makeText(getApplicationContext(),getString(R.string.send_failed),Toast.LENGTH_SHORT).show();

            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {

                List<byte[]> list = new ArrayList<>();
                //设置标签纸大小
                list.add(DataForSendToPrinterTSC.sizeBymm(50,80));
                //设置间隙
                list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                //清除缓存
                list.add(DataForSendToPrinterTSC.cls());
                list.add(DataForSendToPrinterTSC.bitmap(-100,0,0,bitmap1, BitmapToByteData.BmpType.Threshold));
                list.add(DataForSendToPrinterTSC.print(1));

                return list;
            }
        });
    }

    private Bitmap getBitmapOfWebView(final WebView webView){
        float scale = webView.getScale();
        int webViewHeight = Math.round(webView.getContentHeight() * scale);
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);

        float ratio = 460f / bitmap.getWidth();

        float width = 460f;
        float height = ratio * bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(90f);
        Bitmap bmp =  Bitmap.createBitmap(bitmap, 0, 0, webView.getWidth(), webViewHeight, matrix, true);



        Bitmap newBitmap = Bitmap.createScaledBitmap(bmp, (int)width,
                (int)height, true);


        return newBitmap;
    }
}
