package center.myfit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jdk.jfr.Description;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Сущность WorkoutExercise. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WorkoutExercise extends BaseEntity {

  @Description("Количество повторений упражнения")
  private Integer repeats;

  @Description("Рабочий вес")
  private Float weight;

  @Description("Номер подхода (сета)")
  private Integer orders;

  @ManyToOne
  @JoinColumn(name = "workout_id")
  private Workout workout;

  @ManyToOne
  @JoinColumn(name = "exercise_id")
  private Exercise exercise;
}