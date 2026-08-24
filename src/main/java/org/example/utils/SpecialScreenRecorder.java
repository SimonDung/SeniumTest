package org.example.utils;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SpecialScreenRecorder extends ScreenRecorder {

    private String customFileName;

    public SpecialScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
                                 Format screenFormat, Format mouseFormat, Format audioFormat,
                                 File folder, String customFileName) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, folder);
        this.customFileName = customFileName;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        }
        return new File(movieFolder, customFileName + "." + Registry.getInstance().getExtension(fileFormat));
    }
}
