package pe.edu.upc.center.platform.user.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.center.platform.user.domain.model.commands.CreateTutorCommand;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.Address;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.Document;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.Email;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.Phone;

@Entity
@Table(name = "tutors")
public class Tutor extends AuditableAbstractAggregateRoot<Tutor> {

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="email",column = @Column(name = "email", length = 50, nullable = false))
    })
    private Email mail;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="document",column = @Column(name = "document", length = 8, nullable = false))
    })
    private Document document;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="phone",column = @Column(name = "phone", length = 9, nullable = false))
    })
    private Phone phone;

    @Getter
    @Column(name = "profile_img", length = 255)
    private String profileImg;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street", length = 100, nullable = false)),
            @AttributeOverride(name = "district", column = @Column(name = "address_district", length = 50, nullable = false))
    })
    private Address address;


    @Getter
    @NotNull
    @Column(name = "role", length = 20, nullable = false)
    private String role ="Tutor";

    @Getter
    @Setter
    @Column(name = "profile_id")
    private Long profileId;


    public Tutor(String fullName, String email, String doc, String password, String number, String street,String district, String role) {
        this.fullName = fullName;
        this.mail = new Email(email);
        this.document = new Document(doc);
        this.password = password;
        this.phone = new Phone(number);
        this.address = new Address(street, district);
        this.role = role;
    }

    public Tutor() {
    }

    public void updateEmail(String email) {
        this.mail = new Email(email);
    }

    public String getEmail() {
        return  mail.email();
    }

    public void updateDocument(String doc) {
        this.document = new Document(doc);
    }

    public String getDocument() {
        return  document.doc();
    }

    public void updatePhone(String number) {
        this.phone = new Phone(number);
    }

    public String getPhone() {
        return  phone.number();
    }

    public void updateAddress(String street, String district) {
        this.address = new Address(street,district);
    }

    public  String  getAddressDistrict() {
        return address.district();
    }

    public  String  getAddressStreet() {
        return address.street();
    }

    public Tutor(CreateTutorCommand command) {
        this.fullName = command.fullName();
        this.mail = new Email(command.email());
        this.document =  new Document(command.doc());
        this.password = command.password();
        this.phone= new Phone(command.number());
        this.address = new Address(command.street(),command.district());
        this.role = command.role();
    }

    public Tutor updateInformation(String fullName, String email, String doc, String password, String number, String street,String district, String role){
        this.fullName = fullName;
        this.mail = new Email(email);
        this.document = new Document(doc);
        this.password = password;
        this.phone = new Phone(number);
        this.address = new Address(street,district);
        this.role = role;
        return this;
    }
}
