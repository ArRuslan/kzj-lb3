package ua.nure.kz.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Getter
@Table(name = "user_groups")
public class UserGroup {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
