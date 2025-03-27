package com.example.expressfood.dto.response;

import com.example.expressfood.dao.OrdersRepos;
import com.example.expressfood.entities.Cook;
import com.example.expressfood.entities.Orders;
import com.example.expressfood.entities.Role;
import com.example.expressfood.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CookResponse extends UserResponse implements Serializable {
    public CookResponse(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, Boolean isActivated) {
        super(id, firstName, lastName, birthDay, phoneNumber, address, avatarUrl, isActivated);
    }

    public static CookResponse fromEntity(Cook cook){
        CookResponse cookResponse = new CookResponse();
        BeanUtils.copyProperties(cook, cookResponse);
        return cookResponse;
    }
}
