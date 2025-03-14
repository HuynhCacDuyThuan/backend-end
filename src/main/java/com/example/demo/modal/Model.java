package com.example.demo.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "model")
@Getter
@Setter
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name = "active_flag", nullable = false)
	private boolean activeFlag = true;

	@Column(name = "delete_flag", nullable = false)
	private boolean deleteFlag = false;

	@Column(name = "created_by", nullable = false, updatable = false)
	private String createdBy = "admin";

	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate = LocalDateTime.now();

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

}
