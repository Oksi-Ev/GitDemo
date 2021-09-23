import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main(String[] args) throws IOException {

        // TODO Auto-generated method stub
        // validate if Add Place API is working as expected
        // Add place-> Update Place with NewAddress -> Get Place to validate if New address is present in response

        //given - all input details
        //when - submit the API -resource, http method
        //then - validate the response
        //content of the file to String -> content of file can convert into Byte -> Byte data to String

        RestAssured.baseURI= "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(new String (Files.readAllBytes(Paths.get("C:\\Users\\oksana.evstatieva\\Desktop\\addPlace.json")))).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response); //for parsing Json
        String placeId = js.getString("place_id");

        System.out.println(placeId);

        // Update place

        String newAddress = "Summer walk, Africa";

        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").
                when().put("maps/api/place/update/json")
                        .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place

        Response getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response();

      JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
      String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);
        //Cucumber Junit, Tesng






        //Add place -> Update place with New Address -> Get Place to validate if New address is present in response





    }


}
