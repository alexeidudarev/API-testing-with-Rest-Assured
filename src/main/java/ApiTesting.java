import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
public class ApiTesting {
    public static String url = "http://localhost:3000/posts";
    //public static String json = "[{\"id\": 1,\"title\": \"json-server\",\"author\": \"typicode\"}]";
    public static void main(String [] args){
        //System.out.println("response code is :" + getResponseCode(url));
        //assureThatResponseCodeIs200(url);
        getResponseLog(url);
        //getResponseTime(url);
        //System.out.println("The result for key 'title' is : "
        //        +getResponseValue("title",url));
        //sendPostRequest(url);

    }
    //this method run for all info of response
    public static void getResponseLog(String url){
        given().when().get(url).then().log().all();
    }
    //this method run to get response code
    public static int getResponseCode(String url){
        int status_code = given().when().get(url).getStatusCode();
        return status_code;
    }
    //this method run validate that response code is 200 , acts as real test
    public static void assureThatResponseCodeIs200(String url){
        given().when().get(url).then().assertThat().statusCode(201);
    }
    //this method run to get response time in milliseconds
    public static void getResponseTime(String url){
        System.out.println("The time taken to fetch the response "+get(url)
                .timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
    }
    //this method run to get specific value in JSON
    public static String getResponseValue(String JsonKey,String url){
        ArrayList<String> results = given().when().get(url).then().extract()
                .path(JsonKey) ;
        for (String s: results) {
            if (!results.contains(null)&&!s.equalsIgnoreCase(" ")){
                return s;
            }
        }
        return "No result at all,please check JSON key";
    }
    //method to make post request
    public static void sendPostRequest(String url){
        RequestSpecification request = RestAssured.given();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id","3")
                .put("title","test")
                .put("author","qa");
        request.header("Content-Type", "application/json");
        request.body(jsonObj.toString());
        request.post(url).then().assertThat().statusCode(201);

    }
    //method to make put request to existing data
    public static void sendPutRequest(String url,int id){
        RequestSpecification request = RestAssured.given();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id",id)
                .put("title","test")
                .put("author","qa")
                .put("extra","data-updated-1");
        request.header("Content-Type", "application/json");
        request.body(jsonObj.toString());
        request.put(url+id).then().assertThat().statusCode(200);
        //int statusCode = request.post(url).getStatusCode();
        //System.out.println("The status code recieved: " + statusCode);

    }
    //method to make delete request to existing data by id
    public static void sendDeleteRequest(String url,String id){
        RequestSpecification request = RestAssured.given();
        request.put(url+id).then().assertThat().statusCode(200);


    }
}
