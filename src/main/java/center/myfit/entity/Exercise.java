package center.myfit.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность Exercise. */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exercise extends BaseEntity {
  private String title;
  private String description;
  private String videoUrl;

  @ManyToOne
  @JoinColumn(name = "owner", referencedColumnName = "id")
  private User owner;

  @OneToOne(mappedBy = "exercise", cascade = CascadeType.ALL)
  private ExerciseImage image;
}
