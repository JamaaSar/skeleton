package com.nnk.springboot.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class CurrentUserDTO {
    String username;
    Boolean isAdmin;


}
