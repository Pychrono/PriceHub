package org.example.scraper;// Import necessary Hibernate classes
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



public class FrazerScraper {
    private static SessionFactory factory;
    private static WebDriver driver; // Declare the driver variable

    public static void main(String[] args) {
        // Set up Hibernate SessionFactory
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Set the path to your Microsoft Edge WebDriver executable
        System.setProperty("webdriver.edge.driver", "C:\\Users\\chino\\Downloads\\edgedriver_win64\\msedgedriver.exe");

        // Create a new instance of the Microsoft Edge driver
        driver = new EdgeDriver();

        // Replace this URL with the actual URL you have
        String url = "https://www.houseoffraser.co.uk/accessories/mens-watches#dcp=1&dppp=59&OrderBy=rank&Filter=ABRA%5EArmani%20Exchange";

        scrapeData(url);

        // Close the browser and Hibernate SessionFactory
        driver.quit();
        factory.close();
    }

    private static void scrapeData(String url) {
        // Navigate to the website
        driver.get(url);

        // Find all the product elements
        List<WebElement> productElements = driver.findElements(By.cssSelector("li[li-productid]"));

        for (WebElement productElement : productElements) {
            try {
                // Extract name, image, and price
                String name = productElement.getAttribute("li-name");
                String imageUrl = productElement.findElement(By.cssSelector("img.MainImage")).getAttribute("src");
                String price = productElement.findElement(By.cssSelector("span.CurrencySizeLarge")).getText();

                // Create instances of your model classes
                Name nameEntity = new Name();
                nameEntity.setName(name);
                nameEntity.setImageUrl(imageUrl);

                Price priceEntity = new Price();
                priceEntity.setPrice(price);
                priceEntity.setWebsiteUrl(url);
                priceEntity.setName(nameEntity);

                // Save to the database
                saveToDatabase(nameEntity, priceEntity);

                // ...
            } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                // ...
            }
        }
    }

    private static void saveToDatabase(Name nameEntity, Price priceEntity)
    {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // Check if a record with the same name exists
            List existingNames = session.createQuery("FROM Name WHERE name = :name")
                    .setParameter("name", nameEntity.getName())
                    .getResultList();

            if (existingNames.isEmpty()) {
                // Save the entities to the database
                session.save(nameEntity);
                session.save(priceEntity);

                // Commit the transaction
                session.getTransaction().commit();
            } else {
                // Handle multiple existing records (choose one, update all, etc.)
                // For simplicity, let's print a message
                System.out.println("Record with name " + nameEntity.getName() + " already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//DATABASE qUERY TO DELETE ALL ITEMS
//        try (Session session = factory.openSession()) {
//                session.beginTransaction();
//
//                // Delete all records from the Price table
//                session.createQuery("DELETE FROM Price").executeUpdate();
//
//                // Delete all records from the Name table
//                session.createQuery("DELETE FROM Name").executeUpdate();
//
//                // Commit the transaction
//                session.getTransaction().commit();
//                } catch (Exception e) {
//                e.printStackTrace();
//                }
