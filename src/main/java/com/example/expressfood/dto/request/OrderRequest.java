package com.example.expressfood.dto.request;

import com.example.expressfood.dto.response.*;
import com.example.expressfood.entities.*;
import lombok.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class OrderRequest implements Serializable {
    private String address;
    private String description;

}
