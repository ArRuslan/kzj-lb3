package ua.nure.kz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Getter
@Table(name = "groups")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Setter
	@Column(unique = true, nullable = false)
	private String name;

	/*@ManyToMany(mappedBy = "groups")
	private Set<User> users;*/
}
