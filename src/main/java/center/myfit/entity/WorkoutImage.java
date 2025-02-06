package center.myfit.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность WorkoutImage. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("workout_image")
public class WorkoutImage extends Image{
  @OneToOne
  @JoinColumn(name = "workout_id", referencedColumnName = "id")
  private Workout workout;
}

