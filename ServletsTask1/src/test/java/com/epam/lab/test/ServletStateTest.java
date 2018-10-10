package com.epam.lab.test;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.XmlPath.*;

@Ignore
public class ServletStateTest {

    @Test
    public void doGet() {
        when().get("http://localhost:8080/ServletsTask1/change").then().statusCode(200);
    }

    @Test
    public void doPost() {
        String newState = "another new state";
        String response = given().param("newState",newState).post("http://localhost:8080/ServletsTask1/change")
                .getBody().asString();
        List<String> states = from(response).getList("states.state.name");
        assertTrue(states.contains(newState));

    }

    @Test
    public void doPut() {
        String initialState = "state for change";
        String changedState = "changedState";
        String response = given().param("newState",initialState).post("http://localhost:8080/ServletsTask1/change")
                .getBody().asString();
        List<String> states = from(response).getList("states.state.name");
        int index = states.indexOf(initialState);
        assertNotEquals(index,-1);
        response = given().formParam("id",index).formParam("newState", changedState).put("http://localhost:8080/ServletsTask1/change")
                .getBody().asString();
        states = from(response).getList("states.state.name");
        assertEquals(changedState,states.get(index));
    }

    @Test
    public void doDelete() {
        String initialState = "state for delete";
        String response = given().param("newState",initialState).post("http://localhost:8080/ServletsTask1/change")
                .getBody().asString();
        List<String> states = from(response).getList("states.state.name");
        int index = states.indexOf(initialState);
        assertNotEquals(index,-1);
        response = given().formParam("id",index).delete("http://localhost:8080/ServletsTask1/change")
                .getBody().asString();
        states = from(response).getList("states.state.name");
        index = states.indexOf(initialState);
        assertEquals(index,-1);
    }
}