package com.example.mapdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by David on 4/5/17.
 */

public class MapUtils {
    public static BitmapDescriptor createBubble(Context context, int style, String title) {
        IconGenerator iconGenerator = new IconGenerator(context);
        // Possible color options:
        // STYLE_WHITE, STYLE_RED, STYLE_BLUE, STYLE_GREEN, STYLE_PURPLE, STYLE_ORANGE
        iconGenerator.setStyle(style);
        // Swap text here to live inside speech bubble
        Bitmap bitmap = iconGenerator.makeIcon(title);
        // Use BitmapDescriptorFactory to create the marker
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

        return bitmapDescriptor;
    }

    public static Marker addMarker(GoogleMap map, LatLng point, String title, String snippet, BitmapDescriptor icon) {
        // Creates and adds marker to the map
        Marker marker = map.addMarker(new MarkerOptions()
                .position(point)
                .title(title)
                .snippet(snippet)
                .icon(icon));

        marker.setDraggable(true);

        return marker;
    }

    public static void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }
}
