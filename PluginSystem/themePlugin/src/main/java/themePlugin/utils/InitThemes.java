package themePlugin.utils;

import javafx.embed.swing.SwingFXUtils;
import themePlugin.model.CssThemeFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitThemes {

    //singleton pattern
    private static InitThemes defaultInitTheme = null;

    public static synchronized InitThemes defaultInitTheme() {
        if (defaultInitTheme == null) {
            defaultInitTheme = new InitThemes();
        }
        return defaultInitTheme;
    }

    public List<CssThemeFile> initAllThemeData() {

        List<CssThemeFile> allCssFiles = new ArrayList<>();

        BufferedImage img;
        CssThemeFile tempCssClass;

        try {

            tempCssClass = new CssThemeFile(getClass().getResourceAsStream("/themes/Hopeless.css"), "Hopeless","breadcon","1");
            img = ImageIO.read(getClass().getResourceAsStream("/images/hopeless.png"));
            tempCssClass.setThemeImg(SwingFXUtils.toFXImage(img, null));

            allCssFiles.add(tempCssClass);

            tempCssClass = new CssThemeFile(getClass().getResourceAsStream("/themes/GalleryS.css"), "Gallery S","platinum","1");
            img = ImageIO.read(getClass().getResourceAsStream("/images/galleryS.png"));
            tempCssClass.setThemeImg(SwingFXUtils.toFXImage(img, null));

            allCssFiles.add(tempCssClass);

            tempCssClass = new CssThemeFile(getClass().getResourceAsStream("/themes/PersianBlue.css"), "Persian Blue","maryampeiravi","1");
            img = ImageIO.read(getClass().getResourceAsStream("/images/persianBlue.png"));
            tempCssClass.setThemeImg(SwingFXUtils.toFXImage(img, null));

            allCssFiles.add(tempCssClass);

            tempCssClass = new CssThemeFile(getClass().getResourceAsStream("/themes/CaseStudy.css"), "Case Study","kamakhya","1");
            img = ImageIO.read(getClass().getResourceAsStream("/images/caseStudy.png"));
            tempCssClass.setThemeImg(SwingFXUtils.toFXImage(img, null));

            allCssFiles.add(tempCssClass);

            tempCssClass = new CssThemeFile(getClass().getResourceAsStream("/themes/SpicyCilantroMargarita.css"), "Spicy Cilantro Margarita","hootsewers","1");
            img = ImageIO.read(getClass().getResourceAsStream("/images/spicyCilantroMargarita.png"));
            tempCssClass.setThemeImg(SwingFXUtils.toFXImage(img, null));

            allCssFiles.add(tempCssClass);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return allCssFiles;
    }
}
