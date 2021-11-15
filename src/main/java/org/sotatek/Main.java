package org.sotatek;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public void writeToCsv(String filename, List<Product> list_products)
    {
        FileWriter myWriters = new FileWriter("D:\\Selenium\\helloworld\\Setel\\combine.csv");
        myWriters.write("NameWeabsite,NameProduct,Price,Link\n");
        for (Product product : list_products)
        {
            myWriters.write(String.format("{},{},{},{}\n", product.getnameWebsite(), Helper.encodeString(product.getName()), product.getPrice(), Helper.encodeString(product.getLink()));
        }
        myWriters.close();
    }
    public  void parsePrice(String price)
    {
        String priceValue = price.split(" to ")[0].subString(1);
        return Float.parseFloat(priceValue);
    }
    public void combine() throws IOException, ClassNotFoundException {
        List<Product> products = new ArrayList<Product>();

        try (FileReader fin = new FileReader("listproductsEbay.csv"))
        {
            int data = fin.read();
            StringBuilder line = new StringBuilder();
            while (data != -1)
            {
                if (((char)data == '\n') || ((char)data == '\r'))
                {
                    data = fin.read();
                    data = data.split(",");
                    products.append((char)data);
                }
            fin.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
        }

        try (FileReader fin = new FileReader("listproductsAmazon.csv"))
        {
            int data = fin.read();
            StringBuilder line = new StringBuilder();
            while (data != -1)
            {
                if (((char)data == '\n') || ((char)data == '\r'))
                {
                    data = fin.read();
                    data = data.split(",");
                    products.append((char)data);
                }
                fin.close();
            } catch (IOException e) {
            e.printStackTrace();
        }
        }

        for (int i=0, i< products.size().length()-1, i++){
            for (int j=i+1; j< products.size().length(), j++){
                if (parsePrice(products[i][2]) > parsePrice(products[j][2])){
                    tmp = products[i];
                    products[i] = products[j];
                    products[j] = tmp;
                }
            }
        }

        writeToCsv("sortproducts.csv", products);
    }

    public static void main (String[] args){
        System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        combine();
        driver.quit();

    }
}


