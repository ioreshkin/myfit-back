package center.myfit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exercise extends BaseEntity {
  private String title;
  private String description;
  private String pictureUrl;
  private String videoUrl;

  @ManyToOne
  @JoinColumn(name = "owner")
  private User owner;
}
