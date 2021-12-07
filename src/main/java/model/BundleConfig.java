package model;

import java.util.HashMap;

public class BundleConfig {
    public Bundle[] setBundleConfig() {
        Bundle image = new Bundle("IMG", new HashMap<Integer, Double>() {{
            put(5, 450.0);
            put(10, 800.0);
        }});
        Bundle audio = new Bundle("FLAC", new HashMap<Integer, Double>() {{
            put(3, 427.5);
            put(6, 810.0);
            put(9, 1147.5);
        }});
        Bundle video = new Bundle("VID", new HashMap<Integer, Double>() {{
            put(3, 570.0);
            put(5, 900.0);
            put(9, 1530.0);
        }});
        Bundle[] bundles = {image, audio, video};
        return bundles;
    }
}
