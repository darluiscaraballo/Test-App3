package com.demo.google.logic;

import com.demo.google.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GoogleSearchLogic extends Google{

    By GOOGLESEARCH = By.name("q");
    By GOOGLESEARCHBTN = By.name("btnK");
    By GOOGLERESULT = By.tagName("a");
    By TITLE = By.name("h3");

    public void displayPage(){
        try{
            WebDriverManager.navigateTo("https://www.google.com/");
            staticWait(2);
            System.out.println("*** WebDriver Opened URL ***");
        }catch (Exception e) {
            System.out.println("*** Error Opening URL: "+e+" ***");
        }

    }
    public void inputGoogleSearch(String search){
        try{
            WebDriverManager.getDriver().findElement(GOOGLESEARCH).click();
            staticWait(2);
            System.out.println("*** Click on Google Search ***");
            WebDriverManager.getDriver().findElement(GOOGLESEARCH).sendKeys(search);
            staticWait(2);
            System.out.println("*** Send Keys to Google Search ***");
            WebDriverManager.getDriver().findElement(GOOGLESEARCHBTN).click();
            staticWait(2);
            System.out.println("*** Click on Google Search btn ***");
        } catch (Exception e) {
            System.out.println("*** Error in GoogleSearch Input: "+e+" ***");
        }
    }
    public void extractResultAssTXT() {
        try {
            System.out.println("*** Before Extract Result ***");
            List<WebElement> allLinks = WebDriverManager.getDriver().findElements(GOOGLERESULT);
            allLinks.removeIf(x->x.getAttribute("href")==null||x.getAttribute("href").contains("google")||x.getAttribute("href").contains("guru99"));
            Set<WebElement> uniqueLinksSet = allLinks
                    .stream() // get stream for original list
                    .collect(Collectors.toCollection(//distinct elements stored into new SET
                            () -> new TreeSet<>(Comparator.comparing(x->x.getAttribute("href"))))
                    );
            String path = System.getProperty("user.dir");
            String directoryPath = path+"/src/test/resources/resultTest";
            File directoryFile = new File(directoryPath);

            if(!directoryFile.exists())
                directoryFile.mkdir();

            File myObj = new File(directoryPath+"/result.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(directoryPath+"/result.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(myWriter);

            int i= 0;
            for ( WebElement item : uniqueLinksSet)
            {
                i++;
                if(item != null )
                {

                    bufferedWriter.write("Item#"+i+"-" + item.getText() +" - "+ item.getAttribute("href"));
                    bufferedWriter.newLine();
                    System.out.println("Item#"+i+"-" + item.getText()+" - "+ item.getAttribute("href"));
                }
                if(i==10)
                    break;
            }
            bufferedWriter.close();
            myWriter.close();
        }
        catch (Exception e) {
            System.out.println("*** Error with extractResultAssTXT(): "+e+" ***");
        }
    }
    public void verifyResultGenerated( String Text){
        BufferedReader objReader = null;
        String expectedText = Text;
        try {
            String strCurrentLine;
            String path = System.getProperty("user.dir");
            String directoryPath = path+"/src/test/resources/resultTest";
            objReader = new BufferedReader(new FileReader(directoryPath+"/result.txt"));
            while ((strCurrentLine = objReader.readLine()) != null) {
                Assert.assertFalse(strCurrentLine.contains(expectedText));
                System.out.println("I am Reading this:"+strCurrentLine);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (objReader!= null)     objReader.close();
            } catch (IOException e) {
                System.out.println("*** Error with verifyResultGenerated(): "+e+" ***");
            }
        }
    }
}
