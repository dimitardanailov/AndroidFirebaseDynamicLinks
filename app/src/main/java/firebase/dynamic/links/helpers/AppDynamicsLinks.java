package firebase.dynamic.links.helpers;

import android.net.Uri;

import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class AppDynamicsLinks {


    private static final String DOMAIN = "codelab.page.link";
    private static final String WEBSITE = "https://15togo.com/tokensale/";

    // Android
    private static final String ANDROID_PACKAGE = "firebase.dynamic.links";
    private static final int ANDROID_MIN_VERSION = 125;

    // iOS
    private static final String IOS_PACKAGE = "com.example.ios";
    private static final String IOS_MIN_VERSION = "1.0.1";
    private static final String IOS_STORE_ID = "123456789";

    // Google Analytics
    private static final String GOOGLE_ANALYTICS_SOURCE = "orkut";
    private static final String GOOGLE_ANALYTICS_MEDIUM = "social";
    private static final String GOOGLE_ANALYTICS_CAMPAIGN = "example-promo";

    // Itunes
    private static final String ITUNES_TOKEN = "123456";
    private static final String ITUNES_CAMPAIGN_TOKEN = "example-promo";

    // Social
    private static final String SOCIAL_TITLE = "We're 15ToGo";
    private static final String SOCIAL_DESCRIPTION = "This link works whether the app is installed or not!";
    private static final String SOCIAL_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Facebook_New_Logo_%282015%29.svg/2000px-Facebook_New_Logo_%282015%29.svg.png";


    public static DynamicLink createDynamicLink() {
        Uri mySocialImage = Uri.parse(SOCIAL_IMAGE);

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(WEBSITE))
                .setDynamicLinkDomain(DOMAIN)
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder(ANDROID_PACKAGE)
                                .setMinimumVersion(ANDROID_MIN_VERSION)
                                .build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder(IOS_PACKAGE)
                                .setAppStoreId(IOS_STORE_ID)
                                .setMinimumVersion(IOS_MIN_VERSION)
                                .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource(GOOGLE_ANALYTICS_SOURCE)
                                .setMedium(GOOGLE_ANALYTICS_MEDIUM)
                                .setCampaign(GOOGLE_ANALYTICS_CAMPAIGN)
                                .build())
                .setItunesConnectAnalyticsParameters(
                        new DynamicLink.ItunesConnectAnalyticsParameters.Builder()
                                .setProviderToken(ITUNES_TOKEN)
                                .setCampaignToken(ITUNES_CAMPAIGN_TOKEN)
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle(SOCIAL_TITLE)
                                .setImageUrl(mySocialImage)
                                .setDescription(SOCIAL_DESCRIPTION)
                                .build())
                .buildDynamicLink();

        return dynamicLink;
    }
}
