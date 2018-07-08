package firebase.dynamic.links;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import firebase.dynamic.links.helpers.AppDynamicsLinks;

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
        final DynamicLink dynamicLink = AppDynamicsLinks.createDynamicLink();
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

        final DynamicLink dynamicLink = AppDynamicsLinks.createDynamicLink();
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


}
