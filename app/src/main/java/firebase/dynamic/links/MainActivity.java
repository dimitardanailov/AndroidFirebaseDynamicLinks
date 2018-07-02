package firebase.dynamic.links;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView mGreetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        DynamicLink dynamicLink = MainActivity.createDynamicLink();

        mGreetings = (TextView) findViewById(R.id.greetings);
        mGreetings.setText(dynamicLink.toString());
    }

    private void loadShareIntent(DynamicLink dynamicLink) {
        Intent sendIntent = new Intent();
        String msg = "Hey, check this out: " + dynamicLink;
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private static DynamicLink createDynamicLink() {
        Uri mySocialImage = Uri.parse("https://15togo.com/tokensale/assets/images/logo.svg");

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com/"))
                .setDynamicLinkDomain("example.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.example.android")
                                .setMinimumVersion(125)
                                .build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.example.ios")
                                .setAppStoreId("123456789")
                                .setMinimumVersion("1.0.1")
                                .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource("orkut")
                                .setMedium("social")
                                .setCampaign("example-promo")
                                .build())
                .setItunesConnectAnalyticsParameters(
                        new DynamicLink.ItunesConnectAnalyticsParameters.Builder()
                                .setProviderToken("123456")
                                .setCampaignToken("example-promo")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Example of a Dynamic Link")
                                .setImageUrl(mySocialImage)
                                .setDescription("This link works whether the app is installed or not!")
                                .build())
                .buildDynamicLink();

        return dynamicLink;
    }
}
