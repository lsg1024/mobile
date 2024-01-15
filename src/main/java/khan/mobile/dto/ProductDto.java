package khan.mobile.dto;

import khan.mobile.entity.Factories;
import khan.mobile.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String color;
    private Float size;
    private Float weight;
    private String other;
    private String image;
    private Users user;
    private Factories factory;
}
