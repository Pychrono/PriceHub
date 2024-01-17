package org.example.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NextScraper {

    public static void main(String[] args) {
        // Set the path to your Microsoft Edge WebDriver executable
        System.setProperty("webdriver.edge.driver", "C:\\Users\\chino\\Downloads\\edgedriver_win64\\msedgedriver.exe");

        // Create a new instance of the Microsoft Edge driver
        WebDriver driver = new EdgeDriver();

        // Replace this URL with the actual URL you have
        String url = "https://www.next.co.uk/shop/productaffiliation-accessories/brand-armaniexchange-category-watches-gender-men?dp=a&gclid=290492068ee81ed29c6ef77ede456373&gclsrc=3p.ds&msclkid=290492068ee81ed29c6ef77ede456373&p=1#107.19999694824219";

        scrapeData(driver, url);

        // Close the browser
        driver.quit();
    }

    private static void scrapeData(WebDriver driver, String url) {
        // Navigate to the website
        driver.get(url);

//        // Wait for product elements to load
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.ProductCard")));

        // Find all the product elements
        List<WebElement> productElements = driver.findElements(By.cssSelector("div.ProductCard"));

        for (WebElement productElement : productElements) {
            try {
                // Extract name, image, and price
                String name = productElement.findElement(By.cssSelector("h2[data-testid='product_summary_title'] > p")).getText();
                String imageUrl = productElement.findElement(By.cssSelector("a[data-testid='product_summary_image_media'] > img")).getAttribute("src");
                String price = productElement.findElement(By.cssSelector("p[data-testid='product_summary_was_price'] > span")).getText();

                // Print or store the data as needed
                System.out.println("Name: " + name);
                System.out.println("Image URL: " + imageUrl);
                System.out.println("Price: " + price);
                System.out.println("----------------------");

                // Optionally, you can include a delay between each product scrape
                Thread.sleep(1500);
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                // Handle stale element reference by refreshing the list of product elements
                productElements = driver.findElements(By.cssSelector("div.ProductCard"));
                continue;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}