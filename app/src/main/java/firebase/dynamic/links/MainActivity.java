package firebase.dynamic.links;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView mGreetings, mShortUrl, mFlowchart;
    Button mShare;
    String mShortLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        // Generate Dynamic link
        final DynamicLink dynamicLink = MainActivity.createDynamicLink();
        Uri dynamicLinkUri = dynamicLink.getUri();

        mGreetings = (TextView) findViewById(R.id.greetings);
        mGreetings.setText(dynamicLinkUri.toString());

        mShare = (Button) findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                displayShortUrl();
            }
        });
    }

    private void displayShortUrl() {
        mShortUrl = (TextView) findViewById(R.id.short_url);
        mFlowchart = (TextView) findViewById(R.id.flowchart);

        final DynamicLink dynamicLink = MainActivity.createDynamicLink();
        Uri dynamicLinkUri = dynamicLink.getUri();

        final Context context = getApplicationContext();

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLongLink(Uri.parse(dynamicLinkUri.toString()))
            .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
            .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                @Override
                public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                    if (task.isSuccessful()) {
                        // Short link created
                        Uri shortLink = task.getResult().getShortLink();
                        Uri flowchartLink = task.getResult().getPreviewLink();

                        mShortUrl.setText(shortLink.toString());
                        mFlowchart.setText(flowchartLink.toString());

                        MainActivity.loadShareIntent(context, shortLink.toString());

                    } else {
                        // Error
                        // ...
                        mShortUrl.setText("ShortDynamicLinkError ...." + task.getException());
                    }
                }
            });
    }

    private static void loadShareIntent(Context context, String dynamicLink) {
        Intent sendIntent = new Intent();

        String msg = "Hey, check this out: " + dynamicLink;
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    private static DynamicLink createDynamicLink() {
        Uri mySocialImage = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Facebook_New_Logo_%282015%29.svg/2000px-Facebook_New_Logo_%282015%29.svg.png");

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://15togo.com/tokensale/"))
                .setDynamicLinkDomain("codelab.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("firebase.dynamic.links")
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
                                .setTitle("We're 15ToGo")
                                .setImageUrl(mySocialImage)
                                .setDescription("This link works whether the app is installed or not!")
                                .build())
                .buildDynamicLink();

        return dynamicLink;
    }
}
