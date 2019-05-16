package ke.tang.slidemenu.sample.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import ke.tang.slidemenu.sample.BaseSlideMenuActivity;
import ke.tang.slidemenu.sample.R;

public class SlideMenuWithWebView extends BaseSlideMenuActivity {
    private WebViewClient mViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        ;
    };

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setSlideRole(R.layout.layout_slidemenu_with_web_view);
        setSlideRole(R.layout.layout_primary_menu);
        setSlideRole(R.layout.layout_secondary_menu);

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(mViewClient);
        webView.loadUrl("http://www.github.com/TangKe/SlideMenu");
    }
}
