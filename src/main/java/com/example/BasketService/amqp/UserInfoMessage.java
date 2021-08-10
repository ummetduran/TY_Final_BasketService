package com.example.BasketService.amqp;

import com.example.BasketService.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoMessage implements Serializable {

    List<Long> userIdList;

}
