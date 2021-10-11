package com.trino.mqttdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Constant {

    public static final String baseTopic= "CC/topic/";



    public static String getRandomPayLoad(String event) throws IOException, JSONException {

        String payload="";
        File resource = new ClassPathResource("data.json").getFile();
        String text = new String(Files.readAllBytes(resource.toPath()));
        JSONObject jsonObject = new JSONObject(text);
        JSONArray jsonArray= null;

        switch (event){
            case  "safety":
                jsonArray=  jsonObject.getJSONArray("safety");;
                break;
            case  "driverWellBeing":
                jsonArray=  jsonObject.getJSONArray("driverWellBeing");
                break;
            case  "telematics":
                jsonArray=  jsonObject.getJSONArray("telematics");
                break;
        }

        Random random= new Random();
        int objIndex =random.nextInt(5);

        JSONObject obj = jsonArray.getJSONObject(objIndex);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        payload=timeStamp+" -> "+obj.toString();

        return payload;
    }
}
