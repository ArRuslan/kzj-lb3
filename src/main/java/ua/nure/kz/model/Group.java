package ua.nure.kz.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Getter
@Table(name = "groups")
public class Group {
	@Id
	@GeneratedValue
	private long id;

	@Setter
	@Column(unique = true, nullable = false)
	private String name;
}
