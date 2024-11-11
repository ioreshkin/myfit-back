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
public class ProgramUser extends BaseEntity {
    private Integer lastOrderNumber;
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
