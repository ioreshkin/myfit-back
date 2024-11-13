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
public class CoachUser extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;
}
