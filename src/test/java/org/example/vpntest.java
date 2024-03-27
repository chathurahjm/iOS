package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Test
public class vpntest {

    protected WebDriver driver;



    @Test
    public void testActions2() throws InterruptedException {
  long startTime = System.currentTimeMillis();

         while (System.currentTimeMillis() - startTime < TimeUnit.HOURS.toMillis(5)) {

       
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
             chromeOptions.addArguments("--disable-extensions");
             chromeOptions.addArguments("--disable-gpu");
             chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

        try {


        //chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);

           driver.get("https://www.youtube.com/@day2day/playlists");

            Thread.sleep(8000); // Sleep
            takeScreenHhot();

            try {
                WebElement ck = driver.findElement(By.xpath("//input[@type='checkbox']"));
                ck.click();
                takeScreenHhot();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                takeScreenHhot();
            }

             Thread.sleep(8000); // Sleep
            WebElement element = driver.findElement(By.xpath("(//*[@class='yt-simple-endpoint style-scope ytd-playlist-thumbnail'])[4]"));

            // Simulate pressing the space button on the element
            element.click();
            Thread.sleep(900000); // Sleep for 1 second
             //Thread.sleep(3000); // Sleep for 1 second
            Thread.sleep(900000); // Sleep for 1 second

            takeScreenHhot();
            driver.quit();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
            takeScreenHhot();
        
    }
       
    }


    public void testscreen() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(10)) {


            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("windows"))
            {
                WebDriverManager.chromedriver().setup();

                //ChromeOptions chromeOptions = new ChromeOptions();
            }
            else {


                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  OS");


            }
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);

            driver.get("https://whatismyipaddress.com/");
            Thread.sleep(3000);
            takeScreenHhot();
            driver.quit();

        }
    }


    public void takeScreenHhot() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        LocalDateTime currentTime = LocalDateTime.now();

        // Define a formatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss");

        // Format the current time as a string
        String currentTimeAsString = currentTime.format(formatter);

        // Define the path to save the screenshot
        Path destination = Paths.get(System.getProperty("user.dir") + "/screenshots/" + currentTimeAsString + ".png");

        try {
            // Copy screenshot to the destination
            Files.copy(source.toPath(), destination);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX  Screenshot taken Email");
            System.out.println(System.getProperty("user.dir"));
            System.out.println(destination);
            sendEmail(destination.toString());
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }



    public void sendEmail(String filename)
    {
// Recipient's email ID needs to be mentioned.
        String to = "chathurahjm@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "brakesautomation002@gmail.com";

        final String username = "brakesautomation002@gmail.com";//change accordingly
        final String password = "vtayrvuxrrdsjsbg";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("This is message body");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();

            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
