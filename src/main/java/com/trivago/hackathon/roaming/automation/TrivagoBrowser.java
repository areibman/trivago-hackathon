package com.trivago.hackathon.roaming.automation;

import com.google.common.base.Predicate;
import com.trivago.hackathon.roaming.model.SearchResult;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class TrivagoBrowser {

    WebDriver driver;

    final String BASE_URL = "https://www.trivago.com";

    public TrivagoBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    public List<SearchResult> getSearchResults(String query) {
        driver.get(BASE_URL);
        WebElement element = driver.findElement(By.id("horus-querytext"));
        element.sendKeys(query);
        element.submit();
/*
        new WebDriverWait(driver, 10).until(
                (Predicate<WebDriver>) webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
*/
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // WebElement body = driver.findElement(By.tagName("body"));
       // body.sendKeys(Keys.ESCAPE);

//        JavascriptExecutor js = ((JavascriptExecutor) driver);
//        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        List<WebElement> rawResults = driver.findElements(By.className("item__flex-column"));

        return rawResults.stream().map(this::parseResult).collect(Collectors.toList());
    }

    public void clickLink(SearchResult result) {
        Actions builder = new Actions(driver);
        WebElement parent = driver.findElement(By.xpath(String.format("//*[@data-id='%s']", result.getLinkElementId())));
        builder.moveToElement(parent);
        builder.click();
        builder.build().perform();
    }

    private SearchResult parseResult(WebElement html) {


        String title = null;
        try {
            title = html.findElement(By.xpath(".//div/div/h3")).getText();
        } catch (Exception e) {

        }
        String location = null;
        try {
            location = html.findElements(By.className("details__paragraph")).get(0).getText();
        } catch (Exception e) {

        }
        String stars = null;
        try {
            stars = String.valueOf(html.findElements(By.className("item__star")).size());
        }catch (Exception e) {

        }
        String rating = null;
        try {
            rating = html.findElement(By.className("rating-box__value")).getText();
        } catch (Exception e) {

        }
        String ratingText = null;
        try {
            ratingText = html.findElements(By.className("details__paragraph")).get(1).getText();
        } catch (Exception e) {

        }
        String provider = null;
        try {
            provider = html.findElement(By.className("item__deal-best-ota")).getText();
        } catch (Exception e) {

        }
        String maxPrice = null;
        try {
            maxPrice = html.findElement(By.xpath(".//section/div/div/div/meta[1]")).getAttribute("content");
        } catch (Exception e) {

        }
        String minPrice = null;
        try {
           minPrice =  html.findElement(By.xpath(".//section/div/div/div/meta[2]")).getAttribute("content");
        }catch (Exception e) {

        }
        String linkId = null;
        try {
            linkId = html.findElement(By.xpath(".//section[2]/div")).getAttribute("data-id");
        } catch (Exception e) {

        }

        return new SearchResult(title, location, stars, rating, ratingText, provider, maxPrice, minPrice, linkId);
    };

    public void quit() {
        driver.quit();
    }
}
