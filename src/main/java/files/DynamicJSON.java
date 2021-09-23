package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;




public class DynamicJSON {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle)
    {

        RestAssured.baseURI="http://216.10.245.166";
        Response resp = given().header("Content-Type", "application/json")
        .body(payload.AddBook(isbn,aisle))
        .when()
        .post("/Library/Addbook.php")
        .then().assertThat().statusCode(200)
        .extract().response();

        JsonPath js = ReUsableMethods.rawToJson(resp);
        String id = js.get("ID");
        System.out.println(id);

        //delete book


    }

    @DataProvider(name = "BooksData")
    public Object[][] getData()
    {
        //array=collection of elements
        //multidimensional array = collection of arrays
        return new Object[][] {{"qwerqw", "1221"}, {"tyuiop", "8778"}, {"qaswed", "6547"} };
    }



}
