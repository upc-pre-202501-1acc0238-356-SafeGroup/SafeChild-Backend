package pe.edu.upc.center.platform.user.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(String street, String district) {

    public Address{
        if (street == null || street.trim().isEmpty()){
            throw new IllegalArgumentException("street cannot be null or empty");
        }
        if(district == null || district.trim().isEmpty()){
            throw new IllegalArgumentException("district cannot be null or empty");
        }
    }
}
