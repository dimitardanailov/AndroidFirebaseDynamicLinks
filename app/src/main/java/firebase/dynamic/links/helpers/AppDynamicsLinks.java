package firebase.dynamic.links.helpers;

import android.net.Uri;

import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class AppDynamicsLinks {

    public static DynamicLink createDynamicLink() {
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
