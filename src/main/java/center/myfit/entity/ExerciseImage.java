package center.myfit.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность ExerciseImage. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("exercise_image")
public class ExerciseImage extends Image {
  @OneToOne
  @JoinColumn(name = "exercise_id", referencedColumnName = "id")
  private Exercise exercise;
}
