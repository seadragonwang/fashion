package com.seadragon.apps.fashion.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by hwang001c on 5/11/16.
 */
public class FashionUtil {
    private static final Logger logger = LoggerFactory.getLogger(FashionUtil.class);
    public static void saveScreenShot(WebDriver driver){
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        System.out.println("File:" + srcFile);
        try {
            FileUtils.copyFile(srcFile, new File("screenshot.png"));
        }catch(IOException ex){
            logger.error(ex.getLocalizedMessage(), ex);
        }
    }

    public static void waitForLoad(WebDriver driver) {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
