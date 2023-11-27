package model;

import javax.imageio.IIOException;
import java.io.IOException;

public class ExportFile extends Manager {
    private static ExportFile exportFile;
    private final String EXPORT_PATH = "src/main/resources/data/export.dat";

    public static ExportFile getExportFile() {
        if (exportFile == null) {
            exportFile = new ExportFile();
        }
        return exportFile;
    }

    private ExportFile() {
        super();
    }

    @Override
    public void save() throws IOException {
        super.save(EXPORT_PATH);
    }
}
