package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.TodoEntity;
import com.example.demo.model.TodoEntity.TodoEntityBuilder;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	private String id;
	private String userId;
	private String title;
	private boolean done;
	private Long tno;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
		this.tno = entity.getTno();
		this.dueDate = entity.getDueDate();
	}
	
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
			.id(dto.getId())
			.userId(dto.getUserId())
			.title(dto.getTitle())
			.done(dto.isDone())
			.tno(dto.getTno())
			.dueDate(dto.getDueDate())
			.build();
	}
}
