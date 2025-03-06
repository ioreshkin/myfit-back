package center.myfit.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @OneToOne(mappedBy = "workout", cascade = CascadeType.ALL)
  private WorkoutImage image;
}
