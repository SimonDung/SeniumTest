package org.example.data;

import org.example.data.DataModel.UserCredentials;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.List;

public class JsonDataProvider {
    @DataProvider(name = "loginData", parallel = true)
    public Object[][] loginData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<UserCredentials> userCredentials = objectMapper.readValue(new File("src/main/resources/files/loginData.json"),
                    new TypeReference<>() {
                    });

            Object[][] data = new Object[userCredentials.size()][3];

            for (int i = 0; i < userCredentials.size(); i++) {
                data[i][0] = userCredentials.get(i).username();
                data[i][1] = userCredentials.get(i).password();
                data[i][2] = userCredentials.get(i).loginStatus();
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
