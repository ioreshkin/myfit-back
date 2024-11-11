package center.myfit.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Workout extends BaseEntity {
    private String title;
    private String description;
    private Integer difficulty;
}
