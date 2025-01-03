package center.myfit.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность Program. */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Program extends BaseEntity {
  private String title;
  private String description;
}
