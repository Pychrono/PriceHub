package org.example.scraper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.example.models.Name;
import org.example.models.Price;

import java.util.List;

public class GoldsmithScraper {

    private static SessionFactory factory;
    private static WebDriver driver;

    public static void main(String[] args) {
        // Set up Hibernate SessionFactory
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Set the path to your Microsoft Edge WebDriver executable
        System.setProperty("webdriver.edge.driver", "C:\\Users\\chino\\Downloads\\edgedriver_win64\\msedgedriver.exe");

        // Create a new instance of the Microsoft Edge driver
        driver = new EdgeDriver();

        // Replace this URL with the actual URL you have
        String url = "https://www.goldsmiths.co.uk/c/Brands/Emporio-Armani/Watches?q=fh_view_size%3D48%26fh_view%3Dlister%26wosg_site%3Dgoldsmiths_uk%26fh_refpath%3Dac29ea81-1abb-4109-8e76-2a993f7f2e1c%26fh_refview%3Dlister%26fh_reffacet%3Drecipient%26fh_location%3D%252f%252fcatalog01%252fen_GB%252fcategories%253c%257bcatalog01_brands%257d%252fcategories%253c%257bcatalog01_brands_emporioarmani%257d%252fcategories%253c%257bcatalog01_brands_emporioarmani_emporioarmaniwatches%257d%252frecipient%253e%257bforhim%257d&sort=";

        scrapeData(url);

        // Close the browser and Hibernate SessionFactory
        driver.quit();
        factory.close();
    }

    private static void scrapeData(String url) {
        // Navigate to the website
        driver.get(url);

        // Find all the product elements
        List<WebElement> productElements = driver.findElements(By.cssSelector("div.productTile"));

        for (WebElement productElement : productElements) {
            try {
                // Extract name, image, and price
                String name = productElement.findElement(By.cssSelector("div.productTileName")).getText();

                // Extract correct image URL directly from the 'src' attribute of the 'img' tag
                String imageUrl = productElement.findElement(By.cssSelector("img.productListerPrimary")).getAttribute("src");

                String price = productElement.findElement(By.cssSelector("div.productTilePrice")).getText();
                // Extract only the original price (assuming the original price comes before the discounted price)
                String originalPrice = price.split(" ")[0];

                // Create instances of your model classes
                Name nameEntity = new Name();
                nameEntity.setName(name);
                nameEntity.setImageUrl(imageUrl);

                Price priceEntity = new Price();
                priceEntity.setPrice(originalPrice);
                priceEntity.setWebsiteUrl(url);
                priceEntity.setName(nameEntity);

                // Save to the database
                saveToDatabase(nameEntity, priceEntity);

                // Optionally, you can include a delay between each product scrape
                Thread.sleep(4000);
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                // Handle stale element reference by refreshing the list of product elements
                productElements = driver.findElements(By.cssSelector("div.productTile"));
                continue;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Handle any other NoSuchElementException if needed
                System.out.println("Some element not found: " + e.getMessage());
            }
        }
    }

    private static void saveToDatabase(Name nameEntity, Price priceEntity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // Save the entities to the database
            session.save(nameEntity);
            session.save(priceEntity);

            // Commit the transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
