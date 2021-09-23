package files;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;



public class oAuthTest {

    public static void main (String[] args) throws InterruptedException {
        //TODO Auto-generated method stub

        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

//        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
//        //System.setProperty("webdriver.gecko.driver", "C://geckodriver.exe");
//        WebDriver driver = new ChromeDriver();
//
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("oksana.melnik1993@gmail.com");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("ofatin47");
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//        Thread.sleep(4000);
        //String url = driver.getCurrentUrl();

        String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWjsS8afob0R1RymbpyNGgLGCX9sOFGPGWEBi07tfiDdJ-L9ppvj9E7WLKTCtODE-g&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&hd=mentormate.com&prompt=none";


        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);

        // tagname[attribute='value']

        String accessTokenResponse = given().urlEncodingEnabled(false)
        .queryParam("code", code)
        .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
        .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
        .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
        .queryParam("grant_type","authorization_code")
        .when().log().all()
        .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");

        //Step 3

        GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
        .when()
        .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = gc.getCourses().getApi();

        for (int i=0; i<apiCourses.size(); i++) {
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        //Get All courseTitles of WebAutomation

        ArrayList<String> actualList = new ArrayList<String>();
        List <pojo.WebAutomation> webAutomations = gc.getCourses().getWebAutomation();


        for (int i=0; i<webAutomations.size(); i++) {
            actualList.add(webAutomations.get(i).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(actualList.equals(expectedList));


        //System.out.println(response);


    }

}
