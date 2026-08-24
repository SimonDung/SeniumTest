package org.example.utils;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.UUID;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class VideoManager {

    private static final ThreadLocal<SpecialScreenRecorder> screenRecorder = new ThreadLocal<>();
    private static final ThreadLocal<String> currentSessionFolder = new ThreadLocal<>();

    public static void startRecording(Method method) {
        try {
            // Generate unique session ID for folder structure
            if (currentSessionFolder.get() == null) {
                String sessionId = UUID.randomUUID().toString().substring(0, 8);
                currentSessionFolder.set("target/videos/test-session-" + sessionId);
            }

            File file = new File(currentSessionFolder.get());
            String testName = method.getName(); // Extracts @Test method name

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle captureSize = new Rectangle(0, 0, screenSize.width, screenSize.height);

            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

            SpecialScreenRecorder recorder = new SpecialScreenRecorder(gc, captureSize,
                    new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, ENCODING_AVI_DIB,
                            CompressorNameKey, ENCODING_AVI_DIB, DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                            QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, testName);

            screenRecorder.set(recorder);
            screenRecorder.get().start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopRecording() {
        try {
            if (screenRecorder.get() != null) {
                screenRecorder.get().stop();
                screenRecorder.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}