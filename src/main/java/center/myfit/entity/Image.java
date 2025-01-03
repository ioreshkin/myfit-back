package center.myfit.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Сущность Image. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorColumn(name = "image_type")
@DiscriminatorValue("image")
public abstract class Image extends BaseEntity {
  private String original;
  private String desktop;
  private String mobile;
}
