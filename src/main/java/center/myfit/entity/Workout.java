package center.myfit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность Workout. */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Workout extends BaseEntity {
  private String title;
  private String description;
  private Integer difficulty;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;
}
