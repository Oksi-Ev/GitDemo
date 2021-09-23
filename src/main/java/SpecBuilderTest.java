import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace p = new AddPlace();

        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://google.com");

        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
        .setContentType(ContentType.JSON).build();


        ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification response = given().spec(req).body(p);

        Response response1 = response.when().post("/maps/api/place/add/json")
        .then().spec(resspec).extract().response();

        String responseString = response1.asString();
        System.out.println(responseString);

























    }

}
