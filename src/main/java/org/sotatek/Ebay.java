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

public class Ebay {
    public static void writeToCsv(String filename, List<Product> list_products, String nameWebsite) throws IOException
    {
        FileWriter myWriter = new FileWriter("D:\\Selenium\\helloworld\\Setel\\Ebay.csv");
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
        WebElement buttonNext = driver.findElement(By.xpath("//a[@aria-label='Go to next search page']"));
        String results = driver.findElement(By.xpath("//h1[@class='srp-controls__count-heading']/span[1]")).getText();
        String[] result = results.split("\\s");
        String result1 = result[0];
        String numberResult = result1.replace(',', ' ');
        String numberResult1 = numberResult.replaceAll(" ", "");
        int pages= (int) parseInt(numberResult1)/50+1;

        for (int i=0; i<pages; i++)
        {
            List<WebElement> products = driver.findElements(By.xpath("//*[@id='srp-river-results']/ul/li"));
            for (WebElement product : products)
            {
                String name = product.findElement(By.className("s-item__title")).getText();

                String price = product.findElement(By.className("s-item__price")).getText();

                String link = product.findElement(By.className("s-item__link")).getAttribute("href");

                listProducts.add(new Product(name, price, link));
            }
            System.out.print(listProducts);
            buttonNext = driver.findElement(By.xpath("//a[@aria-label='Go to next search page']"));
            buttonNext.click();
            try {
                writeToCsv("listproductsEbay.csv", listProducts, "Ebay.com");
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
        driver.navigate().to("https://www.ebay.com/");
        driver.findElement(By.xpath("//input[@id='gh-ac']")).sendKeys("iPhone 11");
        driver.findElement(By.xpath("//input[@id='gh-btn']")).click();
        getProduct(driver);
        driver.quit();
    }
}

