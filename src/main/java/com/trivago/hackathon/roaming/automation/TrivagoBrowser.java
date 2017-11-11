package com.trivago.hackathon.roaming.automation;

import com.google.common.base.Predicate;
import com.trivago.hackathon.roaming.model.SearchResult;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        new WebDriverWait(driver, 10).until(
                (Predicate<WebDriver>) webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

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


        String title = html.findElement(By.xpath(".//div/div/h3")).getText();
        String location = html.findElements(By.className("details__paragraph")).get(0).getText();
        String stars = String.valueOf(html.findElements(By.className("item__star")).size());
        String rating = html.findElement(By.className("rating-box__value")).getText();
        String ratingText = html.findElements(By.className("details__paragraph")).get(1).getText();
        String provider = html.findElement(By.className("item__deal-best-ota")).getText();
        String maxPrice = html.findElement(By.xpath(".//section/div/div/div/meta[1]")).getAttribute("content");
        String minPrice = html.findElement(By.xpath(".//section/div/div/div/meta[2]")).getAttribute("content");
        String linkId = html.findElement(By.xpath(".//section[2]/div")).getAttribute("data-id");

        return new SearchResult(title, location, stars, rating, ratingText, provider, maxPrice, minPrice, linkId);
    };

    public void quit() {
        driver.quit();
    }
}
