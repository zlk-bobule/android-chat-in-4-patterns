package nju.androidchat.client.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import nju.androidchat.client.R;

public class ItemPicSend extends LinearLayout {
    private ImageView imageView;
    private Context context;
    private UUID messageId;

    public ItemPicSend(Context context, String picURL, UUID messageId){
        super(context);
        this.context = context;
        inflate(context, R.layout.item_text_receive, this);
        LinearLayout linearLayout = findViewById(R.id.chat_item_layout_content);
//        View view = linearLayout.getChildAt(0);
//        linearLayout.removeView(view);
        linearLayout.removeViewAt(0);
        this.imageView = findViewById(R.id.view);
        this.messageId = messageId;

        setPicURL(picURL);
    }

    public void setPicURL(String picURL){
        try {
            URL url = new URL(picURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("GET");
            Bitmap bitmap = null;
            InputStream inputStream = null;

            if (200 == httpURLConnection.getResponseCode()) {
                inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                Message message = Message.obtain();
                message.obj = bitmap;
                message.what = 1;

                //handle处理传入的信息
                handler.sendMessage(message);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            if(message.what == 1 ){
                imageView.setImageBitmap((Bitmap) message.obj);
            } else {
                Toast.makeText(getContext(),"发生错误",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
