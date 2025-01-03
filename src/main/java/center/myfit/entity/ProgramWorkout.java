package center.myfit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность ProgramWorkout. */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProgramWorkout extends BaseEntity {
  private Integer orderNumber;

  @ManyToOne
  @JoinColumn(name = "program_id")
  private Program program;

  @ManyToOne
  @JoinColumn(name = "workout_id")
  private Workout workout;
}
