package epermit.dtos;

import lombok.Data;

@Data
public class ApiClientInput {
    private String clientId;

    private String clientSecret;
}
