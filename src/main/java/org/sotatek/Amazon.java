package org.sotatek;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Amazon {
    public static void writeToCsv(String filename, List<Product> list_products, String nameWebsite) throws IOException
    {
        FileWriter myWriter = new FileWriter("D:\\Selenium\\helloworld\\Setel\\Amazon.csv");
        myWriter.write("NameWeabsite,NameProduct,Price,Link\n");
        for (Product product : list_products)
        {
            myWriter.write(String.format("{},{},{},{}\n", nameWebsite, Helper.encodeString(product.getName()), product.getPrice(), Helper.encodeString(product.getLink())));
        }
        myWriter.close();
    }

    public static void getProduct(WebDriver driver)
    {
        List<Product> listProducts = new ArrayList<Product>();
        WebElement buttonNext = driver.findElement(By.className("a-last"));
        boolean status = buttonNext.isEnabled();
        while (status == true)
        {
            List<WebElement> products = driver.findElements(By.className("s-main-slot"));
            for (WebElement product : products)
            {
                String name = product.findElement(By.className("a-size-base-plus")).getText();

                String price = product.findElement(By.className("a-price-whole")).getText();

                String link = product.findElement(By.className("a-link-normal")).getAttribute("href");

                listProducts.add(new Product(name, price, link));
            }
            System.out.print(listProducts);
            buttonNext = driver.findElement(By.xpath("//a[@aria-label='Go to next search page']"));
            buttonNext.click();
            try {
                writeToCsv("listproductsAmazon.csv", listProducts, "Amazon.com");
            } catch (IOException e) {
                e.printStackTrace();
            }
            listProducts.clear();
        }
    }
    public static void main (String[] args){
        System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iPhone 11");
        driver.findElement(By.id("nav-search-submit-button")).click();
        getProduct(driver);
        driver.quit();
    }
}

