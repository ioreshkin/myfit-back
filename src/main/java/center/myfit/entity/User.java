package center.myfit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
  private String firstName;
  private String lastName;
  @Transient private String role;
  private String email;
  private String keycloakId;
  private Integer invite;

  @OneToMany(mappedBy = "owner")
  private List<Exercise> exercises = new ArrayList<>();
}
