package center.myfit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность UserWorkoutExercise. */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserWorkoutExercise extends BaseEntity {
  private Integer repeats;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "workout_exercise_id")
  private WorkoutExercise workoutExercise;
}
