package com.example.expressfood.dto.response;

import com.example.expressfood.entities.Orders;
import com.example.expressfood.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

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
