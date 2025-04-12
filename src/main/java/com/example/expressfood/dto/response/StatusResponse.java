package com.example.expressfood.dto.response;

import com.example.expressfood.entities.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@ToString
@NoArgsConstructor @Data
public class StatusResponse implements Serializable {
    private Long statusId;
    private String label;

    public static StatusResponse fromEntity(Status status) {
        StatusResponse statusResponse = new StatusResponse();
        BeanUtils.copyProperties(status, statusResponse);
        return statusResponse;
    }
}
