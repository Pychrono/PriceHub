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

public class ZalandoScraper {

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
        String url = "https://www.zalando.co.uk/mens-watches/armani/";

        scrapeData(url);

        // Close the browser and Hibernate SessionFactory
        driver.quit();
        factory.close();
    }

    private static void scrapeData(String url) {
        // Navigate to the website
        driver.get(url);

        // Find all the product elements
        List<WebElement> productElements = driver.findElements(By.cssSelector("article.z5x6ht"));

        for (WebElement productElement : productElements) {
            try {
                // Extract brand, name, image, and price
                String name = productElement.findElement(By.cssSelector("h3.sDq_FX")).getText();

                // Extract correct image URL directly from the 'src' attribute of the 'img' tag
                String imageUrl = productElement.findElement(By.cssSelector("img.sDq_FX")).getAttribute("src");

                String price = productElement.findElement(By.cssSelector("p.sDq_FX span")).getText();

                // Create instances of your model classes
                Name nameEntity = new Name();
                nameEntity.setName(name);
                nameEntity.setImageUrl(imageUrl);

                Price priceEntity = new Price();
                priceEntity.setPrice(price); // Use the extracted price value
                priceEntity.setWebsiteUrl(url);
                priceEntity.setName(nameEntity);

                // Save to the database
                saveToDatabase(nameEntity, priceEntity);

                // Optionally, you can include a delay between each product scrape
                Thread.sleep(1500);
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                // Handle stale element reference by refreshing the list of product elements
                productElements = driver.findElements(By.cssSelector("article.z5x6ht"));
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
