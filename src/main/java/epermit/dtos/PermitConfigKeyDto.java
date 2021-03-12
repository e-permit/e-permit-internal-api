package epermit.dtos;

import java.util.Map;
import com.google.gson.Gson;
import lombok.Getter;

@Getter
public class PermitConfigKeyDto {
    private Map<String, String> props;

    public void setProps(String jwk){
        Gson gson = new Gson();
        props = gson.fromJson(jwk, Map.class);
    }
}
