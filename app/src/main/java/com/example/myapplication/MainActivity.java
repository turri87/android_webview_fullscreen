package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

  private View mCustomView;
  private WebView webView;
  private FrameLayout customViewContainer;
  private WebChromeClient.CustomViewCallback customViewCallback;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String html = "<html><body><iframe width=\"400\" height=\"200\" src=\"https://www.youtube.com/embed/xF_QkfZI1mM\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

    webView = findViewById(R.id.webView);
    customViewContainer = findViewById(R.id.customViewContainer);

    webView.setWebChromeClient(new WChromeClient());
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setAllowFileAccess(true);
    webView.getSettings().setAppCacheEnabled(true);
    webView.getSettings().setDomStorageEnabled(true);
    webView.loadData(html, "text/html", "utf-8");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }



  class WChromeClient extends WebChromeClient {
    @Override
    public void onShowCustomView(View view,CustomViewCallback callback) {

      if (mCustomView != null) {
        callback.onCustomViewHidden();
        return;
      }

      mCustomView = view;
      webView.setVisibility(View.GONE);
      customViewContainer.setVisibility(View.VISIBLE);
      customViewContainer.addView(view);
      customViewCallback = callback;
    }


    @Override
    public void onHideCustomView() {
      super.onHideCustomView();
      if (mCustomView == null)
        return;

      webView.setVisibility(View.VISIBLE);
      customViewContainer.setVisibility(View.GONE);
      mCustomView.setVisibility(View.GONE);
      customViewContainer.removeView(mCustomView);
      customViewCallback.onCustomViewHidden();

      mCustomView = null;
    }
  }

}



